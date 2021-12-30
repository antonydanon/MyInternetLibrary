<%--
  Created by IntelliJ IDEA.
  User: anton
  Date: 15.12.2021
  Time: 22:21
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link rel = "stylesheet" href = "css/header.css">
    <link href="https://fonts.googleapis.com/css2?family=Raleway:wght@300&display=swap" rel="stylesheet">
    <script type="text/javascript" src = "js/header.js"></script>
</head>
<body>
<header>
    <div class = "logo">
        <a href = "mainPage.jsp"><img class = "graphicLogo" src = "images\logotype.png" alt = "Logo"></a>
    </div>
    <div id = "sidebar">
        <div class = "toggle-btn" onclick = "openMenu()">
            <span></span>
            <span></span>
            <span></span>
        </div>
        <ul>
            <li>Menu</li>
            <li><a href = "mainPage.jsp">Main Page</a></li>
            <li><a href = "readerRegistration.jsp">Reader Registration</a></li>
            <li><a href = "mainPage.jsp">Return Of Books</a></li>
            <li><a href = "bookRegistration.jsp">Book Registration</a></li>
            <li><a href = "mainPage.jsp">Issuance of Books</a></li>
            <li>
            <form id = "formReaderList" method="get" action="/Servlet">
                <input id = "btnGetReadersList" type = "submit" name = "btnGetListReaders" value="List Of Readers"/>
            </form>
            </li>
        </ul>
    </div>
</header>
</body>
</html>
