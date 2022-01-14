package com.ita.u1.internetLibrary.service;

import com.ita.u1.internetLibrary.dao.BookDAO;
import com.ita.u1.internetLibrary.dao.Connector;
import com.ita.u1.internetLibrary.model.Book;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

public class BookManagement {
    public static List<Book> loadListOfBooksFromDB(){
        List<Book> listOfBooks = new ArrayList<>();
        Connector.loadDriver();
        Connection connection = Connector.getConnection();
        BookDAO.selectTitleAndYearForBooks(listOfBooks, connection);
        BookDAO.selectCountOfInstancesForBooks(listOfBooks, connection);
        BookDAO.selectGenreForBooks(listOfBooks, connection);
        Connector.closeConnection(connection);
        return listOfBooks;
    }

    public static List<Book>
    getListOfAvailableBooksFromDB(){
        List<Book> listOfBooks = loadListOfBooksFromDB();
        List<Book> listOfAvailableBooks = new ArrayList<>();
        for (var book : listOfBooks) {
            if(book.getCountOfInstancesAvailable() > 0){
                listOfAvailableBooks.add(book);
            }
        }
        return listOfAvailableBooks;
    }
}
