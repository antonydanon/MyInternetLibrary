<%--
  Created by IntelliJ IDEA.
  User: anton
  Date: 02.01.2022
  Time: 21:45
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
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
    <form method="post" action="/Servlet">
        <input type="text" name="passportID"/>
        <input type="text" name="returnDate"/>
        <input type="text" name="priceForReturningBooks"/>
        <input type="text" name="rating"/>
        <input type="submit" name="returnBooks" value="Return Books">
    </form>
</body>
</html>
