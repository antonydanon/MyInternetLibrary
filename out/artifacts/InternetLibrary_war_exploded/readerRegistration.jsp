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
    <title>Reader Registration</title>
    <link rel = "stylesheet" href = "css/readerRegistration.css">
    <link href="https://fonts.googleapis.com/css2?family=Raleway:wght@300&display=swap" rel="stylesheet">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
    <script type="text/javascript" src = "js/readerRegistration.js"></script>
</head>
<body>
<div id='header'></div>
<h1>registering  a  new  reader</h1>
<div class = "input-forms">
    <form method = "post" action="/Servlet">
        <div>
            <div class = "group">
                <label>Surname</label>
                <input class = "input" name = "surname" type = "text" required/>
            </div>
            <div class = "group">
                <label>Name</label>
                <input class = "input" name = "name" type = "text" required/>
            </div>
            <div class = "group">
                <label>Patronymic</label>
                <input class = "input" name = "patronymic" type = "text"/>
            </div>
        </div>
        <div>
            <div class = "group">
                <label>Birthday</label>
                <input class = "input" name = "birthday" type = "date" required/>
            </div>
            <div class = "group">
                <label>Email</label>
                <input class = "input" name = "email" type = "text"  required/>
            </div>
            <div class = "group">
                <label>Passport-id</label>
                <input class = "input" name = "passport-id" type = "text"/>
            </div>
        </div>
        <div>
            <div class = "group">
                <label>Address</label>
                <input class = "input" name = "address" type = "text"/>
                <input class = "input" id = "btn" type = "submit" value="Confirm"/>
            </div>
        </div>

    </form>
</div>
</body>
</html>

