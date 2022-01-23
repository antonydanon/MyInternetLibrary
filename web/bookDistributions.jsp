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
    <div class = "input-forms">
    <form method="get" action="/Servlet">
        <div class="group">
            <label>Email of reader</label>
            <input type="text"  class = "input" name="email"  value="${email}" readonly/>
        </div>
        <div class="group">
            <label>Books for reader</label>
            <c:forEach items="${titlesOfBooks}" var="title" >
                <div id="book">
                <input type="text"  class = "input" name="book" value="${title}" readonly/>
                </div>
            </c:forEach>
        </div>
        <div class="group">
            <label>Return Date</label>
            <input type="text"  class = "input" value="${returnDateOfOrder}" readonly>
        </div>
        <div class="group">
            <label>Price</label>
            <input type="text"  class = "input" value="${priceOfOrder}" readonly>
            <input type="submit"  class = "input" id="btn" name="requestOnOrder" value="Make distribution">
            <input type="hidden" name="titles" value="${titlesOfBooks}"/>
        </div>
    </form>
    </div>
</body>
</html>
