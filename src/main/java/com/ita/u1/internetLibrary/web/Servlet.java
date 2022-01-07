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
        if(params.containsKey("btnGetMainPage")) {
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
        List<Book> listOfBooks = BookManagement.loadListOfBooksFromDB();
        request.setAttribute("listOfBooks", listOfBooks);
        RequestDispatcher dispatcher = request.getRequestDispatcher("mainPage.jsp");
        dispatcher.forward(request, response);
    }

    protected void registerOrdersOfReaders(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<String> titlesOfBooks = getTitlesOfBooks(request);
        OrderManagement.registerOrderOfReader(request.getParameter("passportID"), titlesOfBooks);
        getListOfReadersWithoutDebts(request, response);
    }

    protected void getListOfReadersWithoutDebts(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Reader> listOfReadersWithoutDebts = ReaderManagement.loadListOfReadersWithoutDebtsFromDB();
        request.setAttribute("listOfReadersWithoutDebts", listOfReadersWithoutDebts);
        RequestDispatcher dispatcher = request.getRequestDispatcher("listOfReadersWithoutDebts.jsp");
        dispatcher.forward(request, response);
    }

    protected void getListOfAvailableBooks(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Map<String, String[]> params = request.getParameterMap();
        int readerId = Integer.parseInt(params.get("choice")[0]);
        List<Book> listOfAvailableBooks = BookManagement. getListOfAvailableBooksFromDB();
        request.setAttribute("listOfAvailableBooks", listOfAvailableBooks);
        request.setAttribute("readerId", readerId);
        RequestDispatcher dispatcher = request.getRequestDispatcher("listOfAvailableBooks.jsp");
        dispatcher.forward(request, response);
    }

    protected void openWindowOfOrderRegistration(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int readerId = Integer.parseInt(request.getParameter("readerId"));
        List<Integer> listOfBooksId = getAllBooksId(request);
        List<String> titlesOfBooks = OrderManagement.getTitlesOfBooks(listOfBooksId);
        String passportId = OrderManagement.getPassportId(readerId);
        LocalDate bookReturnDate = OrderManagement.getReturnDate();
        int priceOfOrder = OrderManagement.getOrderPrice(titlesOfBooks);
        request.setAttribute("returnDateOfOrder", bookReturnDate);
        request.setAttribute("priceOfOrder", priceOfOrder);
        request.setAttribute("passportId", passportId);
        request.setAttribute("titlesOfBooks", titlesOfBooks);
        RequestDispatcher dispatcher = request.getRequestDispatcher("bookDistributions.jsp");
        dispatcher.forward(request, response);
    }

    protected void makeReturnOfBooks(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        BookReturningManagement.returningOfBooks(request.getParameter("passportID"));
        RequestDispatcher dispatcher = request.getRequestDispatcher("returnOfBooks.jsp");
        dispatcher.forward(request, response);
    }

    protected void makeRegistrationOfBook(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        BookRegistration book = new BookRegistration(request.getParameter("russianTitle"),
                                                     request.getParameter("originalTitle"),
                                                     Integer.parseInt(request.getParameter("price")),
                                                     Integer.parseInt(request.getParameter("pricePerDay")),
                                                     Integer.parseInt(request.getParameter("yearOfPublishing")),
                                                     LocalDate.parse(request.getParameter("dateOfRegistration")),
                                                     Integer.parseInt(request.getParameter("countOfPages")),
                                                     Integer.parseInt(request.getParameter("countOfInstances")));
        List<byte[]> photosOfBook = getAllPhotoOfBook(request);
        List<Author> authorsForBook = getAllAuthorsForBook(request);
        List<Genre> genres = getGenresForBook(request);
        BookRegistrationManagement.registrationOfBook(book, photosOfBook, authorsForBook, genres);
        RequestDispatcher dispatcher = request.getRequestDispatcher("bookRegistration.jsp");
        dispatcher.forward(request, response);
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
            if(filePart != null){
                String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
                InputStream fileContent = filePart.getInputStream();
                byte[] photo = fileContent.readAllBytes();
                if(photo.length != 0){
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
            if(!request.getParameter(authorParam + num).equals("")){
                Part filePart = request.getPart(photoParam + num);
                if(filePart != null){
                    String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
                    InputStream fileContent = filePart.getInputStream();
                    byte[] photo = fileContent.readAllBytes();
                    if(photo.length != 0) {
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
