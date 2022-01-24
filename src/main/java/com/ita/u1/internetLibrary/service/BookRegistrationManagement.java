package com.ita.u1.internetLibrary.service;

import com.ita.u1.internetLibrary.Constants;
import com.ita.u1.internetLibrary.dao.BookRegistrationDAO;
import com.ita.u1.internetLibrary.dao.Connector;
import com.ita.u1.internetLibrary.model.Author;
import com.ita.u1.internetLibrary.model.BookRegistration;
import com.ita.u1.internetLibrary.model.Genre;

import java.sql.Connection;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class BookRegistrationManagement {
    public static void registrationOfBook(BookRegistration book, List<byte[]> photosOfBook, List<Author> authorsForBook, List<Genre> genres){
        if(componentsOfBookIsValid(book, photosOfBook, authorsForBook, genres)) {
            Connector.loadDriver();
            Connection connection = Connector.getConnection();
            BookRegistrationDAO.insertDateAboutBookIntoBooksTable(book.getRussianTitle(),
                    book.getOriginalTitle(),
                    book.getPrice(),
                    book.getPricePerDay(),
                    book.getYearOfPublishing(),
                    book.getDateOfRegistration(),
                    book.getCountOfPages(), connection);
            int bookId = BookRegistrationDAO.getBookId(connection);
            List<Integer> genresId = BookRegistrationDAO.getGenresId(connection, genres);
            BookRegistrationDAO.makeConnectionBetweenBooksAndGenresInDB(connection, genresId, bookId);
            BookRegistrationDAO.createInstancesInDB(connection, book.getCountOfInstances(), bookId);
            BookRegistrationDAO.putPhotosOfBookIntoDB(connection, bookId, photosOfBook);
            BookRegistrationDAO.putAuthorsOfBookIntoDB(connection, authorsForBook);
            BookRegistrationDAO.makeConnectionBetweenBooksAndAuthors(connection, bookId, authorsForBook);
            Connector.closeConnection(connection);
        }
    }

    public static boolean paramsIsValid( Map<String, String[]> params){
        if(params.get("price")[0].length() == 0)
            return false;
        if(params.get("pricePerDay")[0].length() == 0)
            return false;
        if(params.get("countOfInstances")[0].length() == 0)
            return false;
        return true;
    }

    public static BookRegistration getBook(Map<String, String[]> params){
        if(params.get("yearOfPublishing")[0].length() == 0) {
            return new BookRegistration(params.get("russianTitle")[0],
                    params.get("originalTitle")[0],
                    Integer.parseInt(params.get("price")[0]),
                    Integer.parseInt(params.get("pricePerDay")[0]),
                    null,
                    LocalDate.parse(params.get("dateOfRegistration")[0]),
                    Integer.parseInt(params.get("countOfPages")[0]),
                    Integer.parseInt(params.get("countOfInstances")[0]));
        }
        if(params.get("countOfPages")[0].length() == 0) {
            return new BookRegistration(params.get("russianTitle")[0],
                    params.get("originalTitle")[0],
                    Integer.parseInt(params.get("price")[0]),
                    Integer.parseInt(params.get("pricePerDay")[0]),
                    Integer.parseInt(params.get("yearOfPublishing")[0]),
                    LocalDate.parse(params.get("dateOfRegistration")[0]),
                    null,
                    Integer.parseInt(params.get("countOfInstances")[0]));
        }
        return new BookRegistration(params.get("russianTitle")[0],
                params.get("originalTitle")[0],
                Integer.parseInt(params.get("price")[0]),
                Integer.parseInt(params.get("pricePerDay")[0]),
                Integer.parseInt(params.get("yearOfPublishing")[0]),
                LocalDate.parse(params.get("dateOfRegistration")[0]),
                Integer.parseInt(params.get("countOfPages")[0]),
                Integer.parseInt(params.get("countOfInstances")[0]));
    }

    private static boolean componentsOfBookIsValid(BookRegistration book, List<byte[]> photosOfBook, List<Author> authorsForBook, List<Genre> genres){
        if(bookIsNotValid(book))
            return false;
        if(photosOfBookIsNotValid(photosOfBook))
            return false;
        if(authorsForBookIsNotValid(authorsForBook))
            return false;
        if(genresIsNotValid(genres))
            return false;
        return true;
    }

    private static boolean photosOfBookIsNotValid(List<byte[]> photosOfBook){
        if(photosOfBook.size() == Constants.minSizeOfList)
            return true;
        for(var photo : photosOfBook){
            if(photo.length == Constants.minSizeOfList)
                return true;
        }
        return false;
    }

    private static boolean genresIsNotValid(List<Genre> genres){
        if(genres.size() == Constants.minSizeOfList)
            return true;
        return false;
    }

    private static boolean authorsForBookIsNotValid(List<Author> authorsForBook){
        if(authorsForBook.size() == Constants.minSizeOfList)
            return true;
        for(var author : authorsForBook){
            if(!author.getName().matches("([A-Za-z\s]{1,70})"))
                return true;
        }
        return false;
    }

    private static boolean bookIsNotValid(BookRegistration book){
        if(!book.getRussianTitle().matches("([А-Яа-я0-9\s]{1,50})"))
            return true;
        if(!book.getOriginalTitle().matches("([A-Za-z0-9\s]{1,50})"))
            return true;
        if(book.getDateOfRegistration().compareTo(LocalDate.now()) != 0 || book.getDateOfRegistration() == null)
            return true;
        if(book.getCountOfPages() < Constants.minCountOfPages)
            return true;
        if(book.getPrice() < Constants.minPrice || book.getPrice() == null)
            return true;
        if(book.getCountOfInstances() < Constants.minCountOfInstances || book.getCountOfInstances() == null)
            return true;
        if(book.getPricePerDay() < Constants.minPrice || book.getPricePerDay() == null)
            return true;
        if(book.getYearOfPublishing() < Constants.minYearOfPublication)
            return true;
        return false;
    }

}
