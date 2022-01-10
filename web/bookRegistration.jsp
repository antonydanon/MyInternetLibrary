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
        <label>Russian title</label>
        <input type="text" name="russianTitle" />
        <label>Original title</label>
        <input type="text" name="originalTitle" />
        <label>Genres</label>
        <input type="checkbox" name="genreFantasy" />
        <input type="checkbox" name="genreClassic" />
        <input type="checkbox" name="genreAdventure" />
        <input type="checkbox" name="genreDrama" />
        <input type="checkbox" name="genreHorror" />
        <input type="checkbox" name="genreRomance" />
        <input type="checkbox" name="genreScience" />
        <label>Price</label>
        <input type="number" name="price" />
        <label>Count of instances</label>
        <input type="number" name="countOfInstances" />
        <label>Authors</label>
        <input type="text" name="author1" />
        <input type="file" name="photoOfAuthor1" />
        <input type="text" name="author2" />
        <input type="file" name="photoOfAuthor2" />
        <input type="text" name="author3" />
        <input type="file" name="photoOfAuthor3" />
        <input type="text" name="author4" />
        <input type="file" name="photoOfAuthor4" />
        <input type="text" name="author5" />
        <input type="file" name="photoOfAuthor5" />
        <label>Photos of books</label>
        <input type="file" name="photoOfBook1" />
        <input type="file" name="photoOfBook2" />
        <input type="file" name="photoOfBook3" />
        <input type="file" name="photoOfBook4" />
        <input type="file" name="photoOfBook5" />
        <label>Price per day</label>
        <input type="number" name="pricePerDay" />
        <label>Year of publication</label>
        <input type="number" name="yearOfPublishing" />
        <label>Date of registration</label>
        <input type="date" name="dateOfRegistration" value="${dateOfRegistration}"/>
        <label>Count of pages</label>
        <input type="number" name="countOfPages" />
        <input type="submit" name="makeRegistrationOfBook" value="Registrate book"/>
    </form>
    <img id="ItemPreview" src="">
</body>
</html>
