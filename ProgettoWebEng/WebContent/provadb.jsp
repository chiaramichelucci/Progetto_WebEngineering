<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
     
<sql:query var="utenti" dataSource="jdbc/pollweb">
    select nome, cognome, email, password from utente;
</sql:query>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>

 <table border="1" cellpadding="5">
            <caption><h2>List of utenti</h2></caption>
            <tr>
                <th>Name</th>
                <th>Cognome</th>
                <th>Email</th>
                <th>Password</th>
            </tr>
            <c:forEach var="user" items="${utenti.rows}">
                <tr>
                    <td><c:out value="${user.nome}" /></td>
                    <td><c:out value="${user.cognome}" /></td>
                    <td><c:out value="${user.email}" /></td>
                    <td><c:out value="${user.password}" /></td>
                </tr>
            </c:forEach>
        </table>

</body>
</html>