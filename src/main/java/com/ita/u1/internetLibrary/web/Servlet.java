package com.ita.u1.internetLibrary.web;

import javax.servlet.*;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.*;
import java.io.IOException;
import java.time.LocalDate;
import java.util.*;

import com.ita.u1.internetLibrary.service.*;
import com.ita.u1.internetLibrary.model.Book;
import com.ita.u1.internetLibrary.model.Reader;

@MultipartConfig
public class Servlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Map<String, String[]> params = request.getParameterMap();
        if(params.containsKey("btnGetListReaders")) {
            loadListOfReaders(request, response);
        }
        if(params.containsKey("btnGetMainPage")) {
            loadListOfBooks(request, response);
        }
        if(params.containsKey("request") && params.get("request")[0].equals("Get price and date")){
            getPriceAndDateForOrder(request, response);
        }
        if(params.containsKey("request") && params.get("request")[0].equals("Sent request")) {
            registerOrdersOfReaders(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Map<String, String[]> params = request.getParameterMap();
        if(params.containsKey("name")) {
            addNewReader(request, response);
        }
        if(params.containsKey("makeRegistrationOfBook")) {
            makeRegistrationOfBook(request, response);
        }
        if(params.containsKey("returnBooks")){
            makeReturnOfBooks(request, response);
        }
    }

    protected void addNewReader(HttpServletRequest request, HttpServletResponse response) throws IOException{
        Reader reader = new Reader(request.getParameter("surname"), request.getParameter("name"),
                request.getParameter("patronymic"), request.getParameter("passport-id"),
                request.getParameter("email"), request.getParameter("address"), LocalDate.parse(request.getParameter("birthday")));
        ReaderManagement.addNewReaderInDB(reader);
        response.sendRedirect("readerRegistration.jsp");
    }

    protected void loadListOfReaders(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        List<Reader> listOfReaders = ReaderManagement.loadListOfReadersFromDB();
        request.setAttribute("listOfReaders", listOfReaders);
        RequestDispatcher dispatcher = request.getRequestDispatcher("listOfReaders.jsp");
        dispatcher.forward(request, response);
    }

    protected void loadListOfBooks(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Book> listOfBooks = BookManagement.loadListOfBooksFromDB();
        request.setAttribute("listOfBooks", listOfBooks);
        RequestDispatcher dispatcher = request.getRequestDispatcher("mainPage.jsp");
        dispatcher.forward(request, response);
    }

    protected void getPriceAndDateForOrder(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        LocalDate bookReturnDate = OrderManagement.getReturnDate();
        List<String> titlesOfBooks = new ArrayList<>();
        titlesOfBooks.add(request.getParameter("firstBook"));
        titlesOfBooks.add(request.getParameter("secondBook"));
        titlesOfBooks.add(request.getParameter("thirdBook"));
        titlesOfBooks.add(request.getParameter("fourthBook"));
        titlesOfBooks.add(request.getParameter("fifthBook"));
        int priceOfOrder = OrderManagement.getOrderPrice(titlesOfBooks);
        request.setAttribute("dateOfOrder", bookReturnDate);
        request.setAttribute("priceOfOrder", priceOfOrder);
        RequestDispatcher dispatcher = request.getRequestDispatcher("bookDistributions.jsp");
        dispatcher.forward(request, response);
    }

    protected void registerOrdersOfReaders(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<String> titlesOfBooks = new ArrayList<>();
        titlesOfBooks.add(request.getParameter("firstBook"));
        titlesOfBooks.add(request.getParameter("secondBook"));
        titlesOfBooks.add(request.getParameter("thirdBook"));
        titlesOfBooks.add(request.getParameter("fourthBook"));
        titlesOfBooks.add(request.getParameter("fifthBook"));
        OrderManagement.registerOrderOfReader(request.getParameter("passportID"), titlesOfBooks);
        RequestDispatcher dispatcher = request.getRequestDispatcher("bookDistributions.jsp");
        dispatcher.forward(request, response);
    }

    protected void makeReturnOfBooks(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        BookReturningManagement.returningOfBooks(request.getParameter("passportID"));
        RequestDispatcher dispatcher = request.getRequestDispatcher("returnOfBooks.jsp");
        dispatcher.forward(request, response);
    }

    protected void makeRegistrationOfBook(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        BookRegistrationManagement.registrationOfBook(request);
        RequestDispatcher dispatcher = request.getRequestDispatcher("bookRegistration.jsp");
        dispatcher.forward(request, response);
    }
}
