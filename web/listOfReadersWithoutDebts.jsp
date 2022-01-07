<%--
  Created by IntelliJ IDEA.
  User: anton
  Date: 07.01.2022
  Time: 15:31
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>List Of Readers Without Debts</title>
    <link rel = "stylesheet" href = "css/listOfReadersWithoutDebts.css">
    <link href="https://fonts.googleapis.com/css2?family=Raleway:wght@300&display=swap" rel="stylesheet">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
    <script type="text/javascript" src = "js/listOfReadersWithoutDebts.js"></script>
</head>
<body>
    <div id='header'></div>
    <h1>list of readers without debts</h1>
    <form method="get" action="/Servlet">
    <table>
        <tr>
            <th>Surname</th>
            <th>Name</th>
            <th>Birthday</th>
            <th>Address</th>
            <th>Email</th>
            <th>Choice</th>
        </tr>

        <c:forEach items="${listOfReadersWithoutDebts}" var="reader" >
            <tr>
                <td>${reader.surname}</td>
                <td>${reader.name}</td>
                <td>${reader.birthday}</td>
                <td>${reader.address}</td>
                <td>${reader.email}</td>
                <td><input type="radio" name="choice" value="${reader.id}" id="${reader.id}" required></td>
            </tr>
        </c:forEach>
    </table>
        <a id="linkOnReaderRegistration" href = "readerRegistration.jsp">Follow the link if the reader is not registered</a>
        <input type="submit" name="btnGetListOfAvailableBooks" id="btnGetListOfAvailableBooks" value="Choose reader"/>
    </form>>
</body>
</html>
