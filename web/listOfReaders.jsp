<%--
  Created by IntelliJ IDEA.
  User: anton
  Date: 19.12.2021
  Time: 12:15
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>List Of Readers</title>
    <link rel = "stylesheet" href = "css/listOfReaders.css">
    <link href="https://fonts.googleapis.com/css2?family=Raleway:wght@300&display=swap" rel="stylesheet">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
    <script type="text/javascript" src = "js/listOfReaders.js"></script>
</head>
<body>
    <div id='header'></div>
    <h1>list of readers</h1>
    <table>
        <tr>
            <th>Surname</th>
            <th>Name</th>
            <th>Birthday</th>
            <th>Address</th>
            <th>Email</th>
        </tr>

            <c:forEach items="${listOfReaders}" var="reader" >
                <tr>
                    <td>${reader.surname}</td>
                    <td>${reader.name}</td>
                    <td>${reader.birthday}</td>
                    <td>${reader.address}</td>
                    <td>${reader.email}</td>
                </tr>
            </c:forEach>

    </table>
</body>
</html>
