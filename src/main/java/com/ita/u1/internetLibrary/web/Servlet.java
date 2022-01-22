package com.ita.u1.internetLibrary.web;

import javax.servlet.*;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.*;

import com.ita.u1.internetLibrary.model.*;
import com.ita.u1.internetLibrary.service.*;

@MultipartConfig
public class Servlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Map<String, String[]> params = request.getParameterMap();
        if(params.containsKey("btnGetListReaders")) {
            loadListOfReaders(request, response);
        }
        if(params.containsKey("btnGetMainPage") || params.containsKey("btnGetNextPage")) {
            loadListOfBooks(request, response);
        }
        if(params.containsKey("requestOnOrder")) {
            registerOrdersOfReaders(request, response);
        }
        if(params.containsKey("btnGetListReadersWithoutDebts")){
            getListOfReadersWithoutDebts(request, response);
        }
        if(params.containsKey("btnGetListOfAvailableBooks")){
            getListOfAvailableBooks(request, response);
        }
        if(params.containsKey("btnGetWindowOfOrderRegistration")){
            openWindowOfOrderRegistration(request, response);
        }
        if(params.containsKey("btnGetWindowOfBookReturning")){
            openWindowOfBookReturning(request, response);
        }
        if(params.containsKey("btnGetReadersListWithDebts")){
            getListOfReadersWithDebts(request, response);
        }
        if(params.containsKey("btnGetBookRegistration")){
            openBookRegistration(request, response);
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

    protected void openBookRegistration(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        request.setAttribute("dateOfRegistration", LocalDate.now());
        RequestDispatcher dispatcher = request.getRequestDispatcher("bookRegistration.jsp");
        dispatcher.forward(request, response);
    }

    protected void addNewReader(HttpServletRequest request, HttpServletResponse response) throws IOException{
        Reader reader = new Reader(request.getParameter("surname"), request.getParameter("name"),
                request.getParameter("patronymic"), request.getParameter("passport-id"),
                request.getParameter("email"), request.getParameter("address"), LocalDate.parse(request.getParameter("birthday")), 0);
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
        Map<String, String[]> params = request.getParameterMap();
        List<Book> listOfBooks = BookManagement.loadListOfBooksFromDB();
        int currentPage = BookManagement.getCurrentPage(params, request.getParameter("btnGetNextPage"), request.getParameter("currentPage"));
        int countOfPages = BookManagement.getCountOfPages(listOfBooks);
        if(currentPage > countOfPages)
            currentPage = countOfPages;
        if(currentPage < 1)
            currentPage = 1;
        List<Book> listOfBooksForCurrentPage = BookManagement.getListOfBooksForCurrentPage(listOfBooks, currentPage);
        request.setAttribute("listOfBooksForCurrentPage", listOfBooksForCurrentPage);
        request.setAttribute("currentPage", currentPage);
        RequestDispatcher dispatcher = request.getRequestDispatcher("mainPage.jsp");
        dispatcher.forward(request, response);
    }

    protected void registerOrdersOfReaders(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<String> titlesOfBooks = getTitlesOfBooks(request);
        if(OrderManagement.emailAndTitlesOfBooksIsValid(request.getParameter("email"), titlesOfBooks)) {
            OrderManagement.registerOrderOfReader(request.getParameter("email"), titlesOfBooks);
        }
        getListOfReadersWithoutDebts(request, response);
    }

    protected void getListOfReadersWithoutDebts(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Reader> listOfReadersWithoutDebts = ReaderManagement.loadListOfReadersWithoutDebtsFromDB();
        request.setAttribute("listOfReadersWithoutDebts", listOfReadersWithoutDebts);
        RequestDispatcher dispatcher = request.getRequestDispatcher("listOfReadersWithoutDebts.jsp");
        dispatcher.forward(request, response);
    }

    protected void getListOfReadersWithDebts(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Reader> listOfReadersWithDebts = ReaderManagement.loadListOfReadersWithDebtsFromDB();
        request.setAttribute("listOfReadersWithDebts", listOfReadersWithDebts);
        RequestDispatcher dispatcher = request.getRequestDispatcher("listOfReadersWithDebts.jsp");
        dispatcher.forward(request, response);
    }

    protected void getListOfAvailableBooks(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Map<String, String[]> params = request.getParameterMap();
        if(params.containsKey("choice")){
            int readerId = Integer.parseInt(params.get("choice")[0]);
            List<Book> listOfAvailableBooks = BookManagement.getListOfAvailableBooksFromDB();
            request.setAttribute("listOfAvailableBooks", listOfAvailableBooks);
            request.setAttribute("readerId", readerId);
            RequestDispatcher dispatcher = request.getRequestDispatcher("listOfAvailableBooks.jsp");
            dispatcher.forward(request, response);
        }else {
            getListOfReadersWithDebts(request, response);
        }
    }

    protected void openWindowOfOrderRegistration(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Map<String, String[]> params = request.getParameterMap();
        if(params.containsKey("choiceOfBook")) {
            int readerId = Integer.parseInt(request.getParameter("readerId"));
            List<Integer> listOfBooksId = getAllBooksId(request);
            List<String> titlesOfBooks = OrderManagement.getTitlesOfBooks(listOfBooksId);
            String email = OrderManagement.getEmail(readerId);
            LocalDate bookReturnDate = OrderManagement.getReturnDate();
            int priceOfOrder = OrderManagement.getOrderPrice(titlesOfBooks);
            request.setAttribute("returnDateOfOrder", bookReturnDate);
            request.setAttribute("priceOfOrder", priceOfOrder);
            request.setAttribute("email", email);
            request.setAttribute("titlesOfBooks", titlesOfBooks);
            RequestDispatcher dispatcher = request.getRequestDispatcher("bookDistributions.jsp");
            dispatcher.forward(request, response);
        }else{
            getListOfReadersWithoutDebts(request, response);
        }
    }

    protected void openWindowOfBookReturning(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
            RequestDispatcher dispatcher = request.getRequestDispatcher("returnOfBooks.jsp");
            dispatcher.forward(request, response);
        }else{
            getListOfReadersWithDebts(request, response);
        }
    }

    protected void makeReturnOfBooks(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if(BookReturningManagement.paramsIsNotValid(request.getParameter("email"), Integer.parseInt(request.getParameter("priceForReturningBooks")))){
            getListOfReadersWithDebts(request, response);
        } else {
            BookReturningManagement.returningOfBooks(request.getParameter("email"), Integer.parseInt(request.getParameter("priceForReturningBooks")));
            getListOfReadersWithDebts(request, response);
        }
    }

    protected void makeRegistrationOfBook(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Map<String, String[]> params = request.getParameterMap();
        if(BookRegistrationManagement.paramsIsValid(params)) {
            BookRegistration book = BookRegistrationManagement.getBook(params);
            List<byte[]> photosOfBook = getAllPhotoOfBook(request);
            List<Author> authorsForBook = getAllAuthorsForBook(request);
            List<Genre> genres = getGenresForBook(request);
            BookRegistrationManagement.registrationOfBook(book, photosOfBook, authorsForBook, genres);
        }
        openBookRegistration(request, response);
    }

    private List<String> getTitlesOfBooks(HttpServletRequest request){
        Map<String, String[]> params = request.getParameterMap();
        List<String> titlesOfBooks = new ArrayList<>();
        Collections.addAll(titlesOfBooks,params.get("book"));
        return titlesOfBooks;
    }

    private static List<Genre> getGenresForBook(HttpServletRequest request){
        List<Genre> genresForBook = new ArrayList<>();
        Map<String, String[]> params = request.getParameterMap();
        if(params.containsKey("genreFantasy")){
            genresForBook.add(Genre.Fantasy);
        }
        if(params.containsKey("genreClassic")){
            genresForBook.add(Genre.Classic);
        }
        if(params.containsKey("genreAdventure")){
            genresForBook.add(Genre.Adventure);
        }
        if(params.containsKey("genreDrama")){
            genresForBook.add(Genre.Drama);
        }
        if(params.containsKey("genreHorror")){
            genresForBook.add(Genre.Horror);
        }
        if(params.containsKey("genreRomance")){
            genresForBook.add(Genre.Romance);
        }
        if(params.containsKey("genreScience")){
            genresForBook.add(Genre.Science);
        }
        return genresForBook;
    }

    private static List<byte[]> getAllPhotoOfBook(HttpServletRequest request) throws ServletException, IOException {
        List<byte[]> photos = new ArrayList<>();
        String nameOfParam = "photoOfBook";
        for(int numOfPhotos = 1; numOfPhotos <= 5; numOfPhotos++) {
            Part filePart = request.getPart(nameOfParam + numOfPhotos);
            if (filePart != null) {
                String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
                InputStream fileContent = filePart.getInputStream();
                byte[] photo = fileContent.readAllBytes();
                if (photo.length != 0) {
                    photos.add(photo);
                }
            }
        }
        return photos;
    }

    private static List<Author> getAllAuthorsForBook(HttpServletRequest request) throws ServletException, IOException {
        List<Author> authors = new ArrayList<>();
        String authorParam = "author";
        String photoParam = "photoOfAuthor";
        for (int num = 1; num <= 5; num++) {
            if (!request.getParameter(authorParam + num).equals("")) {
                Part filePart = request.getPart(photoParam + num);
                if (filePart != null) {
                    String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
                    InputStream fileContent = filePart.getInputStream();
                    byte[] photo = fileContent.readAllBytes();
                    if (photo.length != 0) {
                        authors.add(new Author(request.getParameter(authorParam + num), photo, 0));
                    } else {
                        authors.add(new Author(request.getParameter(authorParam + num), null, 0));
                    }
                }
            }
        }
        return authors;
    }

    private List<Integer> getAllBooksId(HttpServletRequest request){
        Map<String, String[]> params = request.getParameterMap();
        List<Integer> listOfBooksId = new ArrayList<>();
        for (var bookId : params.get("choiceOfBook")) {
            listOfBooksId.add(Integer.parseInt(bookId));
        }
        return listOfBooksId;
    }
}
