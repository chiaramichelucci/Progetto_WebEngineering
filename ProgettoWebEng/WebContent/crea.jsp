<!DOCTYPE html>
<html lang="en">
  <head>
    <title>PollWEB</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" type="text/css" href="css/pagineNormale.css">
    <link rel="stylesheet" type="text/css" href="css/contentPart.css">
    <script type="text/javascript" src="js/addFieldQuestion.js" charset="UTF-8"></script>
    <script type="text/javascript" src="js/addChoiceQuestion.js" charset="UTF-8"></script>
    <script type="text/javascript" src="js/funzionalita.js" charset="UTF-8"></script>
  </head>
  <body>
    <div class="sidenav">
      <img src="img/Logo2.png" />
      <a href="#">Home</a>
      <a href="listautenti.html">Aggiungi responsabile</a>
      <a href="crea.html">Crea Sondaggio</a>
      <a href="partecipa.html">Partecipa ad un Sondaggio</a>
      <a href="disponibili.html">Sondaggi disponibili</a>
      <a href="miei.html">I miei Sondaggi</a>
      <a href="login.html">LogOut</a>
    </div>

    <div class="content" id="content">
      <font face="Verdana" size="3" color="#11a69c">
        <p align=”center” >
          <div class="formAdd">
            <form name="domande" value="domande" action="CreazioneSondaggio" method="POST" id="formDomande">
              <div class="buttonAdd">
                <input type="button" name="addText" value="Add Text Question" id="addText" onclick="addFieldQuestion()" class="addButtons" />
                <input type="button" name="addChoice" value="Add Checkbox Question" id="addChoice" onclick="addMultiQuestion()" class="addButtons" /><br />
                <!--<input type="submit" id="submitButton" class="submitButton" name="submit" value="Crea Sondaggio" />-->
              </div>
              <div class="sondaggioInfo">
                <h3>Inserisci qui le informazioni del sondaggio</h3>
                <input type="text" id="titoloS" name="titoloS" class ="textField" placeholder="Titolo" /><br />
                <input type="radio" id="modalitaS" name="modalitaS" value="Privato" />
                <label for="modalitaS">Privato</label>
                <input type="radio" id="modalitaS" name="modalitaS" value="Aperto" />
                <label for="modalitaS">Aperto</label>
              </div>
              <input type="submit" id="submitButton" class="submitButton" name="submit" value="Crea Sondaggio" />
            </form>
          </div>
       </p>
    </font>
    </div> 
  </body>
  <input type="button" name="button" value="Torna ad inizio pagina" id="button" onclick="tornaSu()" class="button" />
</html>