<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<sql:query var="sondaggi,utente" dataSource="jdbc/pollweb">
    select id, titolo, disponibile, modalita from sondaggio where utente=id_utente;
</sql:query>

<!DOCTYPE html>
<html lang="en">
  <head>
    <title>PollWEB</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" type="text/css" href="css/pagineNormale.css">
    <script type="text/javascript" src="js/controlloCampi.js"></script>
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

    <div class="content">
      <font face="Verdana" size="3" color="#11a69c">
        <p align=”center” >
          I miei Sondaggi 
       </p>
    </font>
    <table border=1>
        <tr>
            <td>Id</td>
            <td>Titolo</td>
            <td>Disponobile</td>
            <td>Modalità</td>
            <td>Altro.</td>
        </tr>
        <tr>
            <c:forEach var="sondaggio" items="${sondaggi.rows}">
                <tr>
                    <td><c:out value="${sondaggio.id}" /></td>
                    <td><c:out value="${sondaggio.titolo}" /></td>
                    <td><c:out value="${sondaggio.disponibile}" /></td>
                    <td><c:out value="${sondaggio.modalita}" /></td>
            <td><input type="button" value="Risposte"></button></td>
            <td><input type="button" value="Modifica"></button></td>
            <td><input type="button" id="pulsante" value="Chiudi" onclick="chiudi()"></td>
        </tr>
            </c:forEach>
    </table>
    </div>
  </body>
</html>