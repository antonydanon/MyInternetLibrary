<%--
  Created by IntelliJ IDEA.
  User: anton
  Date: 01.01.2022
  Time: 17:44
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Book Distributions</title>
    <link rel = "stylesheet" href = "css/bookDistributions.css">
    <link href="https://fonts.googleapis.com/css2?family=Raleway:wght@300&display=swap" rel="stylesheet">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
    <script type="text/javascript" src = "js/bookDistributions.js"></script>
</head>
<body>
    <div id='header'></div>
    <h1>Book Distributions</h1>
    <form method="get" action="/Servlet">
        <label>Passport-id of reader</label>
        <input type="text" name="passportID"  value="${passportId}"/>
        <label>Books for reader</label>
        <c:forEach items="${titlesOfBooks}" var="title" >
            <input type="text" name="book" value="${title}"/>
        </c:forEach>
        <label>Return Date</label>
        <input type="text"  value="${returnDateOfOrder}">
        <label>Price</label>
        <input type="text"  value="${priceOfOrder}">
        <input type="submit" name="requestOnOrder" value="Sent request">
        <input type="hidden" name="titles" value="${titlesOfBooks}"/>
    </form>
</body>
</html>
