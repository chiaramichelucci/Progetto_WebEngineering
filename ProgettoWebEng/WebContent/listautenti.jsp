<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
     
<sql:query var="utenti" dataSource="jdbc/pollweb">
    select id, nome, cognome, email, tipo from utente;
</sql:query>

<!DOCTYPE html>
<html lang="en">
  <head>
    <title>PollWEB</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" type="text/css" href="css/pagineNormale.css">
  <script type="text/javascript" src="https://gc.kis.v2.scr.kaspersky-labs.com/FD126C42-EBFA-4E12-B309-BB3FDD723AC1/main.js?attr=Xygwd2Em5Sao_Y6-adJTDrTJPCIrAl39gdXJhQNQPK-x6u14Ei4nQ0evC-r7heFvGyA-6WsKUCh0UVgcHm1r76miAwVRgXQk8LLCcuquKMcUM8sNr7MsgGG-iqn4AZzQ_NSMQjmrmptdCjZlKmtvMrE09ZQ2h63ykDfwixI7Arq_Xi2IvRGyLuuVqIq6y1AACF3zeAg2VGHtZzfMut4B4Fezqj9ZusnS3QbPYIiixj3RjcbQx9ZULT3X4bBdUGI38eVe3KbYcg6CV8t7rEk9r62mSpJpxmUeo29RZucpkld04-zTfGa0GEBPpoVTqUy3RIJWnI9qhyNcrnxVE47lUA" charset="UTF-8"></script></head>
  <body>

    <div class="sidenav">
      <img src="img/Logo2.png" />
      <a href="amministratore.html">Home</a>
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
          Lista Utenti registrati al portale 
       </p>
    </font>
    <table border=1>
        <tr>
            <td>Id</td>
            <td>Nome</td>
            <td>Cognome</td>
            <td>Email</td>
            <td>Tipo</td>
        </tr>
        <c:forEach var="utente" items="${utenti.rows}">
                <tr>
                    <td><c:out value="${utente.id}" /></td>
                    <td><c:out value="${utente.nome}" /></td>
                    <td><c:out value="${utente.cognome}" /></td>
                    <td><c:out value="${utente.email}" /></td>
                    <td><c:out value="${utente.tipo}" /></td>
                    <td><input type="button" value="+Add"></button></td>
                </tr>
          </c:forEach>
    </table>
    </div>
  </body>
</html>