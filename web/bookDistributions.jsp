<%--
  Created by IntelliJ IDEA.
  User: anton
  Date: 01.01.2022
  Time: 17:44
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
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
        <input type="text" name="passportID"/>
        <label>Books for reader</label>
        <input type="text" name="firstBook"/>
        <input type="text" name="secondBook"/>
        <input type="text" name="thirdBook"/>
        <input type="text" name="fourthBook"/>
        <input type="text" name="fifthBook"/>
        <input type="submit" name="request" value="Get price and date"/>
        <label>${dateOfOrder}</label>
        <label>${priceOfOrder}</label>
        <input type="submit" name="request" value="Sent request">
    </form>
</body>
</html>
