package com.ita.u1.internetLibrary.service;

import com.ita.u1.internetLibrary.dao.BookDAO;
import com.ita.u1.internetLibrary.dao.Connector;
import com.ita.u1.internetLibrary.model.Book;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BookManagement {
    public static List<Book> loadListOfBooksFromDB(){
        List<Book> listOfBooks = new ArrayList<>();
        try {
            Connector.loadDriver();
            Connection connection = Connector.getConnection();
            BookDAO.selectTitleAndYearForBooks(listOfBooks, connection);
            BookDAO.selectCountOfInstancesForBooks(listOfBooks, connection);
            BookDAO.selectGenreForBooks(listOfBooks, connection);
            Connector.closeConnection(connection);
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }

        return listOfBooks;
    }
}
