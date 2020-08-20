/*
    Funzioni javascript per aggiungere un campo di testo per una domanda.
    Un problema e che per distinguere i diversi campi dello stesso tipo bisogna che questi hanno il paraametro name diverso.
    Per questo ho fatto 2 funzioni
    addText che analiza la pagina per vedere se sono piu di 20 campi di input, se si return null
    se non ci sono 20 campi allora controlla se esiste il campo text1 che sara il primo, se non esiste lo aggiunge
    se esiste allora prende l'id del campo text gia esistente crea la stringa text-n (n il numero prossimo del campo che non esiste)
    (es. se text-1 esiste allora crea la stringa text-2)
    passa questa stringa alla funzione addTextQuestion che crea il campo di input

    per addesso ho fatto solo l'inserimento del campo per la risposta
     
*/

function addTextQuestion(n, s, l){
    var tipo = ['Text', 'Textarea', 'Number', 'Date'];
    var selectList = document.createElement('select');
    selectList.id = s;
    selectList.name = 'tipo';
    var field = document.getElementById('formDomande');
    var container = document.createElement('div');
    container.name = 'domandaDiv';
    container.id = 'domandaDiv';
    container.className = 'domandaDiv';
    var testo = document.createElement('h4');
    testo.innerHTML = 'Inserisci qui la domanda:';
    var input = document.createElement('input');
    input.type = 'text';
    input.name = 'domanda';
    input.id = n;
    input.className = 'textField';
    var nota = document.createElement('input');
    nota.id = l;
    nota.name = 'nota';
    nota.type = 'text'; 
    container.appendChild(testo);
    container.appendChild(selectList);
    for (var i = 0; i < tipo.length; i++) {
        var option = document.createElement("option");
        option.value = tipo[i];
        option.text = tipo[i];
        option.id = tipo[i];
        selectList.appendChild(option);
    }
    container.appendChild(input);
    container.appendChild(nota);
    field.appendChild(container);
    field.appendChild(document.createElement('br'));
}

function addFieldQuestion() {
    var campo = 'domanda-';
    var selectNumber = 'select-'
    var not = 'nota-';
    var id = campo.concat("1");
    var idSelect = selectNumber.concat('1');
    var idNota = not.concat('1');
    var ele = document.getElementById(id);
    var sel = document.getElementById(idSelect);
    var nota = document.getElementById(idNota);
    if (!ele) {
        addTextQuestion(id, idSelect, idNota);
    } else {
        while (ele){
            var ss = ele.id;
            var sss = sel.id;
            var ssss = nota.id;
            var str1 = ss.substring(ss.indexOf("-") + 1);
            var str2 = sss.substring(sss.indexOf("-") + 1);
            var str3 = ssss.substring(ssss.indexOf("-") + 1);
            str1 *= 1;
            str1 += 1;
            str2 *= 1;
            str2 += 1;
            str3 *= 1;
            str3 += 1;
            id = campo.concat(str1);
            idSelect = selectNumber.concat(str2);
            idNota = not.concat(str3);
            ele = document.getElementById(id);
            sel = document.getElementById(idSelect);
            nota = document.getElementById(idNota);
        }
        addTextQuestion(id, idSelect, idNota);
    }
  }
