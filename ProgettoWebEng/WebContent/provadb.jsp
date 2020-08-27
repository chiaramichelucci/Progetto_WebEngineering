<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
     
<sql:query var="sondaggi" dataSource="jdbc/pollweb">
    select id, titolo, disponibile, modalita from sondaggio;
</sql:query>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>

 <table border="1" cellpadding="5">
            <caption><h2>List of sondaggi</h2></caption>
            <tr>
                <th>id</th>
                <th>titolo</th>
                <th>modalita</th>
                <th>disponibile</th>
            </tr>
            <c:forEach var="sondaggio" items="${sondaggi.rows}">
                <tr>
                    <td><c:out value="${sondaggio.id}" /></td>
                    <td><c:out value="${sondaggio.titolo}" /></td>
                    <td><c:out value="${sondaggio.disponibile}" /></td>
                    <td><c:out value="${sondaggio.modalita}" /></td>
                </tr>
            </c:forEach>
        </table>

</body>
</html>