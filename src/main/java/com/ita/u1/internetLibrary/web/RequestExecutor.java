package com.ita.u1.internetLibrary.web;

import com.ita.u1.internetLibrary.Constants;
import com.ita.u1.internetLibrary.model.*;
import com.ita.u1.internetLibrary.model.additional.BooleanHolder;
import com.ita.u1.internetLibrary.service.*;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class RequestExecutor {

    static List<Book> listWithBooks = new ArrayList<>();
    static List<Reader> listWithReaders = new ArrayList<>();
    protected static void openBookRegistration(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("dateOfRegistration", LocalDate.now());
        RequestDispatcher dispatcher = request.getRequestDispatcher(Constants.bookRegistration);
        dispatcher.forward(request, response);
    }

    protected static void openReaderRegistration(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        RequestDispatcher dispatcher = request.getRequestDispatcher(Constants.readerRegistration);
        dispatcher.forward(request, response);
    }

    protected static void addNewReader(HttpServletRequest request, HttpServletResponse response) throws IOException{
        Reader reader = RecipientOfParameters.getReader(request);
        ReaderManagement.addNewReaderInDB(reader);
        response.sendRedirect(Constants.readerRegistration);
    }

    protected static void registerOrdersOfReaders(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<String> titlesOfBooks = RecipientOfParameters.getTitlesOfBooks(request);
        if(OrderManagement.emailAndTitlesOfBooksIsValid(request.getParameter("email"), titlesOfBooks)) {
            OrderManagement.registerOrderOfReader(request.getParameter("email"), titlesOfBooks);
        }
        getListOfReadersWithoutDebts(request, response);
    }

    protected static void getListOfReadersWithoutDebts(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Reader> listOfReadersWithoutDebts = ReaderManagement.loadListOfReadersWithoutDebtsFromDB();
        request.setAttribute("listOfReadersWithoutDebts", listOfReadersWithoutDebts);
        RequestDispatcher dispatcher = request.getRequestDispatcher(Constants.listOfReadersWithoutDebts);
        dispatcher.forward(request, response);
    }

    protected static void getListOfReadersWithDebts(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Reader> listOfReadersWithDebts = ReaderManagement.loadListOfReadersWithDebtsFromDB();
        request.setAttribute("listOfReadersWithDebts", listOfReadersWithDebts);
        RequestDispatcher dispatcher = request.getRequestDispatcher(Constants.listOfReadersWithDebts);
        dispatcher.forward(request, response);
    }

    protected static void getListOfAvailableBooks(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Map<String, String[]> params = request.getParameterMap();
        if(params.containsKey("choice")){
            int readerId = Integer.parseInt(params.get("choice")[0]);
            List<Book> listOfAvailableBooks = BookManagement.getListOfAvailableBooksFromDB();
            request.setAttribute("listOfAvailableBooks", listOfAvailableBooks);
            request.setAttribute("readerId", readerId);
            RequestDispatcher dispatcher = request.getRequestDispatcher(Constants.listOfAvailableBooks);
            dispatcher.forward(request, response);
        }else {
            getListOfReadersWithDebts(request, response);
        }
    }

    protected static void openWindowOfOrderRegistration(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Map<String, String[]> params = request.getParameterMap();
        if(params.containsKey("choiceOfBook")) {
            int readerId = Integer.parseInt(request.getParameter("readerId"));
            List<Integer> listOfBooksId = RecipientOfParameters.getAllBooksId(request);
            List<String> titlesOfBooks = OrderManagement.getTitlesOfBooks(listOfBooksId);
            String email = OrderManagement.getEmail(readerId);
            LocalDate bookReturnDate = OrderManagement.getReturnDate();
            int priceOfOrder = OrderManagement.getOrderPrice(titlesOfBooks);
            request.setAttribute("returnDateOfOrder", bookReturnDate);
            request.setAttribute("priceOfOrder", priceOfOrder);
            request.setAttribute("email", email);
            request.setAttribute("titlesOfBooks", titlesOfBooks);
            RequestDispatcher dispatcher = request.getRequestDispatcher(Constants.bookDistributions);
            dispatcher.forward(request, response);
        }else{
            getListOfReadersWithoutDebts(request, response);
        }
    }

    protected static void openWindowOfBookReturning(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if(!request.getParameter("listOfReadersWithDebts").equals("[]")) {
            Map<String, String[]> params = request.getParameterMap();
            int readerId = Integer.parseInt(params.get("choice")[0]);
            String email = OrderManagement.getEmail(readerId);
            List<String> titlesOfBooks = BookReturningManagement.getTitlesOfBook(readerId);
            int price =  BookReturningManagement.getFinalPrice(readerId);
            LocalDate returnDate = LocalDate.now();
            request.setAttribute("email", email);
            request.setAttribute("titlesOfBooks", titlesOfBooks);
            request.setAttribute("price", price);
            request.setAttribute("returnDate", returnDate);
            RequestDispatcher dispatcher = request.getRequestDispatcher(Constants.returnOfBooks);
            dispatcher.forward(request, response);
        }else{
            getListOfReadersWithDebts(request, response);
        }
    }

    protected static void makeReturnOfBooks(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Map<String, String[]> params = request.getParameterMap();
        List<BookReturning> booksForReturn = new ArrayList<>();
        List<String> books = new ArrayList<>();
        Collections.addAll(books, params.get("book"));
        List<String> ratings = new ArrayList<>();
        Collections.addAll(ratings, params.get("rating"));
        List<String> newPrices = new ArrayList<>();
        Collections.addAll(newPrices, params.get("newPrice"));
        int penalty = 0;
        if(!request.getParameter("penalty").equals(""))
            penalty = Integer.parseInt(request.getParameter("penalty"));
        for(int i = 0; i < books.size(); i++){
            if(ratings.get(i).equals(""))
                booksForReturn.add(new BookReturning(books.get(i), -1));
            else
                booksForReturn.add(new BookReturning(books.get(i), Integer.parseInt(ratings.get(i))));
            if(newPrices.get(i).equals(""))
                booksForReturn.get(i).setNewPrice(-1);
            else
                booksForReturn.get(i).setNewPrice(Integer.parseInt(newPrices.get(i)));
        }
        if(BookReturningManagement.paramsIsNotValid(request.getParameter("email"), Integer.parseInt(request.getParameter("priceForReturningBooks")), booksForReturn, penalty)){
            getListOfReadersWithDebts(request, response);
        } else {
            BookReturningManagement.returningOfBooks(request.getParameter("email"), Integer.parseInt(request.getParameter("priceForReturningBooks")), booksForReturn, penalty);
            getListOfReadersWithDebts(request, response);
        }
    }

    protected static void makeRegistrationOfBook(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Map<String, String[]> params = request.getParameterMap();
        if(BookRegistrationManagement.paramsIsValid(params)) {
            BookRegistration book = BookRegistrationManagement.getBook(params);
            List<byte[]> photosOfBook = RecipientOfParameters.getAllPhotoOfBook(request);
            List<Author> authorsForBook = RecipientOfParameters.getAllAuthorsForBook(request);
            List<Genre> genres = RecipientOfParameters.getGenresForBook(request);
            BookRegistrationManagement.registrationOfBook(book, photosOfBook, authorsForBook, genres);
        }
        openBookRegistration(request, response);
    }

    protected static void loadListOfReaders(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        Map<String, String[]> params = request.getParameterMap();
        BooleanHolder isAscForSurname = new BooleanHolder(true);
        BooleanHolder isAscForName = new BooleanHolder(true);
        BooleanHolder isAscForBirthday = new BooleanHolder(true);
        BooleanHolder isAscForAddress = new BooleanHolder(true);
        BooleanHolder isAscForEmail = new BooleanHolder(true);
        List<Reader> listOfReaders;
        if(params.containsKey("btnGetListReaders")) {
            listOfReaders = ReaderManagement.loadListOfReadersFromDB();
        } else {
            listOfReaders = listWithReaders;
            isAscForSurname.value = Boolean.parseBoolean(request.getParameter("isAscForSurname"));
            isAscForName.value = Boolean.parseBoolean(request.getParameter("isAscForName"));
            isAscForBirthday.value = Boolean.parseBoolean(request.getParameter("isAscForBirthday"));
            isAscForAddress.value = Boolean.parseBoolean(request.getParameter("isAscForAddress"));
            isAscForEmail.value = Boolean.parseBoolean(request.getParameter("isAscForEmail"));
        }
        if(params.containsKey("btnSortReaders"))
            listOfReaders = ReaderManagement.sortListOfReaders(listOfReaders, request.getParameter("btnSortReaders"),
                    isAscForSurname, isAscForName, isAscForBirthday, isAscForAddress, isAscForEmail);

        int currentPage = ReaderManagement.getCurrentPage(params, request.getParameter("btnGetNextPageReaders"), request.getParameter("currentPageReaders"));
        int countOfPages = ReaderManagement.getCountOfPages(listOfReaders);
        if(currentPage > countOfPages)
            currentPage = countOfPages;
        if(currentPage < 1)
            currentPage = 1;
        List<Reader> listOfReadersForCurrentPage = ReaderManagement.getListOfReadersForCurrentPage(listOfReaders, currentPage);
        listWithReaders = listOfReaders;
        request.setAttribute("listOfReadersForCurrentPage", listOfReadersForCurrentPage);
        request.setAttribute("currentPage", currentPage);
        request.setAttribute("isAscForSurname", isAscForSurname.value);
        request.setAttribute("isAscForName", isAscForName.value);
        request.setAttribute("isAscForBirthday", isAscForBirthday.value);
        request.setAttribute("isAscForAddress", isAscForAddress.value);
        request.setAttribute("isAscForEmail", isAscForEmail.value);
        RequestDispatcher dispatcher = request.getRequestDispatcher(Constants.listOfReaders);
        dispatcher.forward(request, response);
    }

    protected static void loadListOfBooks(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Map<String, String[]> params = request.getParameterMap();
        BooleanHolder isAscForTitle = new BooleanHolder(true);
        BooleanHolder isAscForGenre = new BooleanHolder(true);
        BooleanHolder isAscForYear = new BooleanHolder(true);
        BooleanHolder isAscForInstances = new BooleanHolder(true);
        BooleanHolder isAscForAvailable = new BooleanHolder(true);
        List<Book> listOfBooks;
        if(params.containsKey("btnGetMainPage")) {
            listOfBooks = BookManagement.loadListOfBooksFromDB();
        } else {
            listOfBooks = listWithBooks;
            isAscForTitle.value = Boolean.parseBoolean(request.getParameter("isAscForTitle"));
            isAscForGenre.value = Boolean.parseBoolean(request.getParameter("isAscForGenre"));
            isAscForYear.value = Boolean.parseBoolean(request.getParameter("isAscForYear"));
            isAscForInstances.value = Boolean.parseBoolean(request.getParameter("isAscForInstances"));
            isAscForAvailable.value = Boolean.parseBoolean(request.getParameter("isAscForAvailable"));
        }
        if(params.containsKey("btnSort"))
            listOfBooks = BookManagement.sortListOfBooks(listOfBooks, request.getParameter("btnSort"),
                    isAscForTitle, isAscForGenre, isAscForYear, isAscForInstances, isAscForAvailable);

        int currentPage = BookManagement.getCurrentPage(params, request.getParameter("btnGetNextPage"), request.getParameter("currentPage"));
        int countOfPages = BookManagement.getCountOfPages(listOfBooks);
        if(currentPage > countOfPages)
            currentPage = countOfPages;
        if(currentPage < 1)
            currentPage = 1;
        List<Book> listOfBooksForCurrentPage = BookManagement.getListOfBooksForCurrentPage(listOfBooks, currentPage);
        listWithBooks = listOfBooks;
        request.setAttribute("listOfBooksForCurrentPage", listOfBooksForCurrentPage);
        request.setAttribute("currentPage", currentPage);
        request.setAttribute("isAscForTitle", isAscForTitle.value);
        request.setAttribute("isAscForGenre", isAscForGenre.value);
        request.setAttribute("isAscForYear", isAscForYear.value);
        request.setAttribute("isAscForInstances", isAscForInstances.value);
        request.setAttribute("isAscForAvailable", isAscForAvailable.value);
        RequestDispatcher dispatcher = request.getRequestDispatcher(Constants.mainPage);
        dispatcher.forward(request, response);
    }
}
