<%--
  Created by IntelliJ IDEA.
  User: anton
  Date: 30.12.2021
  Time: 17:15
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Book Registration</title>
    <link rel = "stylesheet" href = "css/bookRegistration.css">
    <link href="https://fonts.googleapis.com/css2?family=Raleway:wght@300&display=swap" rel="stylesheet">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
    <script type="text/javascript" src = "js/bookRegistration.js"></script>
</head>
<body>
    <div id='header'></div>
    <h1>Book Registration</h1>
    <form action="/Servlet" method="post" enctype="multipart/form-data">
        <input type="text" name="description" />
        <input type="file" name="file" />
        <input type="submit" name="btnSubmitFile"/>
    </form>
    <img id="ItemPreview" src="">
</body>
</html>
