package com.ita.u1.internetLibrary.service;

import com.ita.u1.internetLibrary.dao.BookRegistrationDAO;
import com.ita.u1.internetLibrary.dao.Connector;
import com.ita.u1.internetLibrary.model.Author;
import com.ita.u1.internetLibrary.model.Genre;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Paths;
import java.sql.Connection;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class BookRegistrationManagement {
    public static void registrationOfBook(HttpServletRequest request){
        List<Genre> genres = getGenresForBook(request);
        try{
            Connector.loadDriver();
            Connection connection = Connector.getConnection();
            BookRegistrationDAO.insertDateAboutBookIntoBooksTable(request.getParameter("russianTitle"),
                                                                  request.getParameter("originalTitle"),
                                                                  Integer.parseInt(request.getParameter("price")),
                                                                  Integer.parseInt(request.getParameter("pricePerDay")),
                                                                  Integer.parseInt(request.getParameter("yearOfPublishing")),
                                                                  LocalDate.parse(request.getParameter("dateOfRegistration")),
                                                                  Integer.parseInt(request.getParameter("countOfPages")), connection);
            int bookId = BookRegistrationDAO.getBookId(connection);
            List<Integer> genresId = BookRegistrationDAO.getGenresId(connection, genres);
            BookRegistrationDAO.makeConnectionBetweenBooksAndGenresInDB(connection, genresId, bookId);
            BookRegistrationDAO.createInstancesInDB(connection, Integer.parseInt(request.getParameter("countOfInstances")), bookId);
            List<byte[]> photosOfBook = getAllPhotoOfBook(request);
            BookRegistrationDAO.putPhotosOfBookIntoDB(connection, bookId, photosOfBook);
            List<Author> authorsForBook = getAllAuthorsForBook(request);
            BookRegistrationDAO.putAuthorsOfBookIntoDB(connection, authorsForBook);
            BookRegistrationDAO.makeConnectionBetweenBooksAndAuthors(connection, bookId, authorsForBook);
            Connector.closeConnection(connection);
        } catch (IOException | ServletException e) {
            e.printStackTrace();
        }
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
}
