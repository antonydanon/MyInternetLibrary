<%--
  Created by IntelliJ IDEA.
  User: anton
  Date: 02.01.2022
  Time: 21:45
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Return Of Books</title>
    <link rel = "stylesheet" href = "css/returnOfBooks.css">
    <link href="https://fonts.googleapis.com/css2?family=Raleway:wght@300&display=swap" rel="stylesheet">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
    <script type="text/javascript" src = "js/returnOfBooks.js"></script>
</head>
<body>
    <div id='header'></div>
    <h1>Return Of Books</h1>
    <form method="post" action="/Servlet" enctype="multipart/form-data">
        <label>Passport-id of reader</label>
        <input type="text" name="email" value="${email}"/>
        <label>Return date</label>
        <input type="date" name="returnDate" value="${returnDate}"/>
        <label>Price</label>
        <input type="text" name="priceForReturningBooks" value="${price}"/>
        <label>Return books</label>
        <c:forEach items="${titlesOfBooks}" var="title">
            <label>Title</label>
            <input type="text" name="book" value="${title}"/>
            <label>Rating</label>
            <input type="number" name="rating"/>
            <label>Photos with violations</label>
            <input type="file" name = "photoOfViolations">
            <input type="file" name = "photoOfViolations">
        </c:forEach>
        <input type="submit" name="returnBooks" value="Return Books">
    </form>
</body>
</html>
