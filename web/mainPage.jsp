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

    <table id="btnTable">
        <tr class="btnTr">
            <td class="btnTd">
                <form method="get" action="/Servlet">
                    <input id = "btnReaderRegistration" class="btnInput" type = "submit" name = "btnReaderRegistration" value="Reader Registration"/>
                </form>
            </td>
            <td class="btnTd">
                <form method="get" action="/Servlet">
                    <input id = "btnGetBookRegistration" class="btnInput" type = "submit" name = "btnGetBookRegistration" value="Book Registration"/>
                </form>
            </td>
        </tr>
        <tr class="btnTr">
            <td class="btnTd">
                <form method="get" action="/Servlet">
                    <input id = "btnGetReadersListWithDebts" class="btnInput" type = "submit" name = "btnGetReadersListWithDebts" value="Return Of Books"/>
                </form>
            </td>
            <td class="btnTd">
                <form method="get" action="/Servlet">
                    <input id = "btnGetReadersListWithoutDebts" class="btnInput" type = "submit" name = "btnGetListReadersWithoutDebts" value="Book Distributions"/>
                </form>
            </td>
        </tr>
    </table>

    <h1>list of books</h1>

    <table id="tableOfBooks">
        <form method="get" action="/Servlet">
        <tr class="bookTr">
            <th>
                <input type="submit" name="btnSort" class="sort" value="Russian title of the book"/>
                <input type="hidden" name="isAscForTitle" value="${isAscForTitle}">
            </th>
            <th>
                <input type="submit" name="btnSort" class="sort" value="Genre"/>
                <input type="hidden" name="isAscForGenre" value="${isAscForGenre}">
            </th>
            <th>
                <input type="submit" name="btnSort" class="sort" value="Year of publication"/>
                <input type="hidden" name="isAscForYear" value="${isAscForYear}">

            </th>
            <th>
                <input type="submit" name="btnSort" class="sort" value="Count of instances"/>
                <input type="hidden" name="isAscForInstances" value="${isAscForInstances}">

            </th>
            <th>
                <input type="submit" name="btnSort" class="sort" value="Available"/>
                <input type="hidden" name="isAscForAvailable" value="${isAscForAvailable}">
            </th>
        </tr>
        </form>

        <c:forEach items="${listOfBooksForCurrentPage}" var="book" >
            <tr class="bookTr">
                <td class="bookTd">${book.russianNameOfBook}</td>
                <td class="bookTd">${book.genres.get(0)}</td>
                <td class="bookTd">${book.yearOfPublication}</td>
                <td class="bookTd">${book.countOfInstances}</td>
                <td class="bookTd">${book.countOfInstancesAvailable}</td>
            </tr>
        </c:forEach>
    </table>
    <form method="get" action="/Servlet">
        <input type="submit" name="btnGetNextPage" id="btnGetNextPage1" value="  <  "/>
        <input type="submit" name="btnGetNextPage" id="btnGetNextPage2" value="  >  "/>
        <input type="hidden" name="currentPage" value="${currentPage}">
        <label>${currentPage}</label>
    </form>

</body>
</html>
