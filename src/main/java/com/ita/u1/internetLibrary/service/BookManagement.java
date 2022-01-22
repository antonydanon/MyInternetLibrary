package com.ita.u1.internetLibrary.service;

import com.ita.u1.internetLibrary.Constants;
import com.ita.u1.internetLibrary.dao.BookDAO;
import com.ita.u1.internetLibrary.dao.Connector;
import com.ita.u1.internetLibrary.model.Book;
import com.ita.u1.internetLibrary.model.Reader;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class BookManagement {
    public static int getCurrentPage(Map<String, String[]> params, String buttonParam, String currPageParam){
        int currentPage;
        if(params.containsKey("currentPage") && buttonParam.equals("  >  ") ) {
            currentPage = Integer.parseInt(currPageParam);
            currentPage++;
        }
        else {
            if(params.containsKey("currentPage") && buttonParam.equals("  <  ")) {
                currentPage = Integer.parseInt(currPageParam);
                currentPage--;
            } else
                currentPage = 1;
        }
        return currentPage;
    }
    public static List<Book> getListOfBooksForCurrentPage(List<Book> listOfBooks, int currentPage){
        List<Book> listOfBooksForCurrentPage = new ArrayList<>();
        int endPosition = Constants.countOfRecordsForPage * currentPage;
        currentPage--;
        int startPosition = currentPage * Constants.countOfRecordsForPage;
        for(int i = startPosition; i < endPosition; i++){
            if(listOfBooks.size() > i)
                listOfBooksForCurrentPage.add(listOfBooks.get(i));
        }
        return listOfBooksForCurrentPage;
    }

    public static int getCountOfPages(List<Book> listOfBooks){
        int countOfPages = listOfBooks.size() / Constants.countOfRecordsForPage;
        int checkLastPage = listOfBooks.size() % Constants.countOfRecordsForPage;
        return countOfPages+checkLastPage;
    }

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
