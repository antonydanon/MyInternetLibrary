<%--
  Created by IntelliJ IDEA.
  User: anton
  Date: 15.12.2021
  Time: 22:21
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Main Page</title>
    <link rel = "stylesheet" href = "css/mainPage.css">
    <link href="https://fonts.googleapis.com/css2?family=Raleway:wght@300&display=swap" rel="stylesheet">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
    <script type="text/javascript" src = "js/mainPage.js"></script>
</head>
<body>
    <div id='header'></div>
    <h1>list of books</h1>

    <form>
    <table>
        <tr>
            <th>Russian title of the book</th>
            <th>Genre</th>
            <th>Year of publication</th>
            <th>Count of instances</th>
            <th>Available</th>
        </tr>

        <c:forEach items="${listOfBooksForCurrentPage}" var="book" >
            <tr>
                <td>${book.russianNameOfBook}</td>
                <td>${book.genres.get(0)}</td>
                <td>${book.yearOfPublication}</td>
                <td>${book.countOfInstances}</td>
                <td>${book.countOfInstancesAvailable}</td>
            </tr>
        </c:forEach>
    </table>
        <input type="submit" name="btnGetNextPage" id="btnGetNextPage1" value="  <  "/>
        <input type="submit" name="btnGetNextPage" id="btnGetNextPage2" value="  >  "/>
        <input type="hidden" name="currentPage" value="${currentPage}">
    </form>

</body>
</html>
