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
    <div class = "input-forms">
    <form action="/Servlet" method="post" enctype="multipart/form-data">
        <div class="group">
            <label>Russian title</label>
            <input type="text" class = "input" name="russianTitle" pattern="[А-Яа-я0-9s]{1,50}" title="Only integers and Russian letters, no more than 50!" required/>
        </div>
        <div class="group">
            <label>Original title</label>
            <input type="text" class = "input" name="originalTitle" pattern="([A-Za-z0-9s]{1,50})" title="Only integers and letters, no more than 50!" required/>
        </div>
        <div class="group">
            <label>Genres</label>
            <div>
                <label>Fantasy</label>
            <input type="checkbox" name="genreFantasy" />
                <label>Classic</label>
            <input type="checkbox" name="genreClassic" />
                <label>Adventure</label>
            <input type="checkbox" name="genreAdventure" />
                <label>Drama</label>
            <input type="checkbox" name="genreDrama" />
            </div>
            <div>
                <label>Horror</label>
            <input type="checkbox" name="genreHorror" />
                <label>Romance</label>
            <input type="checkbox" name="genreRomance" />
                <label>Science</label>
            <input type="checkbox" name="genreScience" />
            </div>
        </div>
        <div class="group">
            <label>Price</label>
            <input type="number" class = "input" name="price" pattern="[0-9]+" min="0" max="9999999" title="An integer from 0 to 9999999!" required/>
        </div>
        <div class="group">
            <label>Count of instances</label>
            <input type="number" class = "input" name="countOfInstances" pattern="[0-9]+" min="1" max="9999999" title="An integer from 1 to 9999999!" required/>
        </div>
        <div class="group">
            <label>Authors and their photos</label>
            <div>
            <input type="text" class = "input"  name="author1" pattern="[A-Za-z ]{1,70}" title="Only letters and spaces!" required/>
            <input type="file" name="photoOfAuthor1" />
            </div>
            <div>
            <input type="text" class = "input"  name="author2" pattern="([A-Za-z ]{1,70})|" title="Only letters and spaces!"/>
            <input type="file" name="photoOfAuthor2" />
            </div>
            <div>
            <input type="text" class = "input" name="author3"  pattern="([A-Za-z ]{1,70})|" title="Only letters and spaces!"/>
            <input type="file" name="photoOfAuthor3" />
            </div>
            <div>
            <input type="text" class = "input" name="author4"  pattern="([A-Za-z ]{1,70})|" title="Only letters and spaces!"/>
            <input type="file" name="photoOfAuthor4" />
            </div>
            <div>
            <input type="text" class = "input" name="author5"  pattern="([A-Za-z ]{1,70})|" title="Only letters and spaces!"/>
            <input type="file" name="photoOfAuthor5" />
            </div>
        </div>
        <div class="group">
            <label>Photos of books</label>
            <div>
            <input type="file" name="photoOfBook1" required/>
            <input type="file" name="photoOfBook2" />
            <input type="file" name="photoOfBook3" />
            <input type="file" name="photoOfBook4" />
            <input type="file" name="photoOfBook5" />
            </div>
        </div>
        <div class="group">
            <label>Price per day</label>
            <input type="number" class = "input" name="pricePerDay"  pattern="[0-9]+" min="0" max="9999999" title="An integer from 1 to 9999999!" required/>
        </div>
        <div class="group">
            <label>Year of publication</label>
            <input type="number" class = "input" name="yearOfPublishing" pattern="[0-9]+" min="0" max="9999999" title="An integer from 1 to 9999999!"/>
        </div>
        <div class="group">
            <label>Date of registration</label>
            <input type="date" class = "input" name="dateOfRegistration" value="${dateOfRegistration}" readonly/>
        </div>
        <div class="group">
            <label>Count of pages</label>
            <input type="number" class = "input" name="countOfPages" pattern="[0-9]+" min="3" max="9999999" title="An integer from 3 to 9999999!"/>
            <input type="submit" id="btn" class = "input" name="makeRegistrationOfBook" value="Registrate book"/>
        </div>
    </form>
    </div>
</body>
</html>
