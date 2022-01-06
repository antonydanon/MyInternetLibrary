package com.ita.u1.internetLibrary.service;

import com.ita.u1.internetLibrary.dao.BookRegistrationDAO;
import com.ita.u1.internetLibrary.dao.Connector;
import com.ita.u1.internetLibrary.model.Author;
import com.ita.u1.internetLibrary.model.BookRegistration;
import com.ita.u1.internetLibrary.model.Genre;

import java.sql.Connection;
import java.util.List;

public class BookRegistrationManagement {
    public static void registrationOfBook(BookRegistration book, List<byte[]> photosOfBook, List<Author> authorsForBook, List<Genre> genres){
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
