package com.ita.u1.internetLibrary.web;

import com.ita.u1.internetLibrary.model.Author;
import com.ita.u1.internetLibrary.model.Genre;
import com.ita.u1.internetLibrary.model.Reader;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class RecipientOfParameters {
    public static Reader getReader(HttpServletRequest request){
        Reader reader = new Reader(request.getParameter("surname"), request.getParameter("name"),
                request.getParameter("patronymic"), request.getParameter("passport-id"),
                request.getParameter("email"), request.getParameter("address"), LocalDate.parse(request.getParameter("birthday")), 0);
        return reader;
    }
    public static List<String> getTitlesOfBooks(HttpServletRequest request){
        Map<String, String[]> params = request.getParameterMap();
        List<String> titlesOfBooks = new ArrayList<>();
        Collections.addAll(titlesOfBooks,params.get("book"));
        return titlesOfBooks;
    }

    public static List<Genre> getGenresForBook(HttpServletRequest request){
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

    public static List<byte[]> getAllPhotoOfBook(HttpServletRequest request) throws ServletException, IOException {
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

    public static List<Author> getAllAuthorsForBook(HttpServletRequest request) throws ServletException, IOException {
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

    public static List<Integer> getAllBooksId(HttpServletRequest request){
        Map<String, String[]> params = request.getParameterMap();
        List<Integer> listOfBooksId = new ArrayList<>();
        for (var bookId : params.get("choiceOfBook")) {
            listOfBooksId.add(Integer.parseInt(bookId));
        }
        return listOfBooksId;
    }
}
