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
                <input id = "inpSurname" class = "input" name = "surname" type = "text" pattern="[A-Za-z]{1,30}" title="Only letters, no more than 30!" required/>
                <div id = "mistakeSurname"></div>
            </div>
            <div class = "group">
                <label>Name</label>
                <input id = "inpName" class = "input" name = "name" type = "text" pattern="[A-Za-z]{1,30}" title="Only letters, no more than 30!" required/>
                <div id = "mistakeName"></div>
            </div>
            <div class = "group">
                <label>Patronymic</label>
                <input id = "inpPatronymic" class = "input" name = "patronymic" pattern="([A-Za-z]{1,30})|" title="Only letters, no more than 30!" type = "text"/>
                <div id = "mistakePatronymic"></div>
            </div>
        </div>
        <div>
            <div class = "group">
                <label>Birthday</label>
                <input id = "inpBirthday" class = "input" name = "birthday" type = "date" required/>
                <div id = "mistakeBirthday"></div>
            </div>
            <div class = "group">
                <label>Email</label>
                <input id = "inpEmail" class = "input" name = "email" type = "text" pattern="[a-z0-9._%+-]+@[a-z0-9.-]+\.[a-z]{2,4}$" title="Enter the name of the correct unique mail!" required/>
                <div id = "mistakeEmail"></div>
            </div>
            <div class = "group">
                <label>Passport-id</label>
                <input id = "inpPassport-id" class = "input" name = "passport-id" pattern="[A-Z]{2}[0-9]{7}|" title="The first two characters are capital letters, the other seven are numbers!" type = "text"/>
                <div id = "mistakePassport-id"></div>
            </div>
        </div>
        <div>
            <div class = "group">
                <label>Address</label>
                <input id = "inpAddress" class = "input" name = "address" pattern="[A-Za-z0-9 ]{2,100}|" title="Enter the correct address of letters and numbers, no more than 100 and no less than 2 characters!" type = "text"/>
                <div id = "mistakeAddress"></div>
                <input class = "input" id = "btn" type = "submit" value="Confirm"/>
            </div>
        </div>

    </form>
</div>
</body>
</html>

