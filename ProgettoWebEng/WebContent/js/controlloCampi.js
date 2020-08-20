function datireg() {
// Variabili associate ai campi del modulo
   var nome = document.reg.nome.value;
   var cognome = document.reg.cognome.value;
   var email = document.reg.email.value;
   var password = document.reg.password.value;
   var conferma= document.reg.conferma.value;

   if ((nome == "") || (nome == "undefined")) {
      alert("Devi inserire un nome");
      document.reg.nome.focus();
      return false;
   }

   if ((cognome == "") || (cognome == "undefined")) {
    alert("Devi inserire un cognome");
    document.reg.cognome.focus();
    return false;
 }
// Espressione regolare dell'email
   var email_valid = /^([a-zA-Z0-9_.-])+@(([a-zA-Z0-9-]{2,})+.)+([a-zA-Z0-9]{2,})+$/;
   if (!email_valid.test(email) || (email == "") || (email == "undefined")) 
   {
      alert("Devi inserire un indirizzo mail corretto");
      document.reg.email.focus();
      return false;
   }
  if (password.length < 6 || (password == "") || (password == "undefined") ) 
   {
    alert("Scegli una password, minimo 6 caratteri");
    document.reg.password.focus();
    return false;
   }
   //Effettua il controllo sul campo CONFERMA PASSWORD
   if ((conferma == "") || (conferma == "undefined")) {
      alert("Devi confermare la password");
      document.reg.conferma.focus();
      return false;
    }
    if (password != conferma) {
       alert("La conferma password");
       document.reg.conferma.value = "";
       document.reg.conferma.focus();
       return false;
    }
    else {
      document.reg.action = "partecipante.html"; 
      document.reg.submit();
   }
}

function chiudi(){
   alert ('Questo questionario sta per essere cancellato!');
}
/*
function chiudi(id)
{
  var sei_sicuro = confirm('sei sicuro di vole cancellare?');
  if (sei_sicuro)
  {
    location.href = '/cancella.asp?id=' + id;
  }else{
    alert('cancellazione NON eseguita');
  }
}
</script>
<a href="#" onclick="cancella(1);">cancella</a>
*/