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
    <div class = "input-forms">
    <form method="post" action="/Servlet" enctype="multipart/form-data">
        <div class="group">
            <label>Email of reader</label>
            <input type="text" class = "input" name="email" value="${email}" readonly/>
        </div>
        <div class="group">
            <label>Return date</label>
            <input type="date" class = "input" name="returnDate" value="${returnDate}" readonly/>
        </div>
        <div class="group">
            <label>Price</label>
            <input type="text" class = "input" name="priceForReturningBooks" value="${price}" readonly/>
        </div>
        <div>
            <label>Return books</label>
            <c:forEach items="${titlesOfBooks}" var="title">
            <div class="group">
                <label>Title</label>
                <input type="text" class = "input"  name="book" value="${title}" readonly/>
            </div>
            <div class="group">
                <label>Rating</label>
                <input type="number" class = "input"  name="rating" pattern="[0-9]{0,2}" min="0" max="10" title="An integer from 0 to 10!"/>
            </div>
            <div class="group">
                <label>Photos with violations</label>
                <input type="file" id="pht1"  name = "photoOfViolations">
                <input type="file" id="pht2" name = "photoOfViolations">
            </div>
            <div class="group">
                <label>New price of book</label>
                <input type="number" class = "input"  name="newPrice" pattern="[0-9]+" min="0" max="999999" title="An integer from 0 to 999999!"/>
            </div>
            </c:forEach>
            <div class="group">
                <label>Penalty</label>
                <input type="number" class = "input"  name="penalty" pattern="[0-9]+" min="0" max="999999" title="An integer from 0 to 999999!"/>
            </div>
            <input type="submit" id="btn" class = "input" name="returnBooks" value="Return Books">
        </div>
    </form>
    </div>
</body>
</html>
