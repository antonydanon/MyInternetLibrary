<%--
  Created by IntelliJ IDEA.
  User: anton
  Date: 07.01.2022
  Time: 18:09
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>List Of Available Books</title>
    <link rel = "stylesheet" href = "css/listOfAvailableBooks.css">
    <link href="https://fonts.googleapis.com/css2?family=Raleway:wght@300&display=swap" rel="stylesheet">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
    <script type="text/javascript" src = "js/listOfAvailableBooks.js"></script>
</head>
<body>
<div id='header'></div>
<h1>list of available books</h1>
<form name="form1" method="get" action="/Servlet">
<table>
    <tr>
        <th>Russian title of the book</th>
        <th>Genre</th>
        <th>Year of publication</th>
        <th>Count of instances</th>
        <th>Available</th>
        <th>Choice</th>
    </tr>

    <c:forEach items="${listOfAvailableBooks}" var="book" >
        <tr>
            <td>${book.russianNameOfBook}</td>
            <td>${book.genres.get(0)}</td>
            <td>${book.yearOfPublication}</td>
            <td>${book.countOfInstances}</td>
            <td>${book.countOfInstancesAvailable}</td>
            <td><input type="checkbox" name="choiceOfBook" value="${book.id}"></td>
        </tr>
    </c:forEach>
</table>
    <input type="submit" name="btnGetWindowOfOrderRegistration" id="btnGetWindowOfOrderRegistration" value="Choose books"/>
    <input type="hidden" name="readerId" value="${readerId}"/>
    <div>
        <span id="notvalid"></span>
    </div>
</form>
</body>
</html>
