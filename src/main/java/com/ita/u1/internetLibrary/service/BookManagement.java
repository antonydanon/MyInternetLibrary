package com.ita.u1.internetLibrary.service;

import com.ita.u1.internetLibrary.Constants;
import com.ita.u1.internetLibrary.dao.BookDAO;
import com.ita.u1.internetLibrary.dao.Connector;
import com.ita.u1.internetLibrary.model.Book;
import com.ita.u1.internetLibrary.model.additional.BooleanHolder;

import java.sql.Connection;
import java.util.*;

public class BookManagement {
    public static List<Book> sortListOfBooks(List<Book> listOfBooks, String sortParam, BooleanHolder
            isAscForTitle, BooleanHolder isAscForGenre, BooleanHolder isAscForYear, BooleanHolder isAscForInstances, BooleanHolder isAscForAvailable){
        if(sortParam.equals("Russian title of the book")){
            listOfBooks = sortByTitle(listOfBooks, isAscForTitle);
        }
        if(sortParam.equals("Genre")){
            listOfBooks = sortByGenre(listOfBooks, isAscForGenre);
        }
        if(sortParam.equals("Year of publication")){
            listOfBooks = sortByYear(listOfBooks, isAscForYear);
        }
        if(sortParam.equals("Count of instances")){
            listOfBooks = sortByInstances(listOfBooks, isAscForInstances);
        }
        if(sortParam.equals("Available")){
            listOfBooks = sortByAvailable(listOfBooks, isAscForAvailable);
        }
        return listOfBooks;
    }

    private static List<Book> sortByGenre(List<Book> listOfBooks, BooleanHolder isAscForGenre){
        if(isAscForGenre.value) {
            listOfBooks.sort((o1, o2) -> o1.getGenres().get(0).compareTo(o2.getGenres().get(0)));
            isAscForGenre.value = false;
        }
        else {
            listOfBooks.sort((o1, o2) -> o2.getGenres().get(0).compareTo(o1.getGenres().get(0)));
            isAscForGenre.value = true;
        }
        return listOfBooks;
    }

    private static List<Book> sortByYear(List<Book> listOfBooks, BooleanHolder isAscForYear){
        if(isAscForYear.value) {
            listOfBooks.sort((o1, o2) -> o1.getYearOfPublication() - o2.getYearOfPublication());
            isAscForYear.value = false;
        }
        else {
            listOfBooks.sort((o1, o2) -> o2.getYearOfPublication() - o1.getYearOfPublication());
            isAscForYear.value = true;
        }
        return listOfBooks;
    }

    private static List<Book> sortByInstances(List<Book> listOfBooks, BooleanHolder isAscForInstances){
        if(isAscForInstances.value) {
            listOfBooks.sort((o1, o2) -> o1.getCountOfInstances() - o2.getCountOfInstances());
            isAscForInstances.value = false;
        }
        else {
            listOfBooks.sort((o1, o2) -> o2.getCountOfInstances() - o1.getCountOfInstances());
            isAscForInstances.value = true;
        }
        return listOfBooks;
    }

    private static List<Book> sortByAvailable(List<Book> listOfBooks, BooleanHolder isAscForInstances){
        if(isAscForInstances.value) {
            listOfBooks.sort((o1, o2) -> o1.getCountOfInstancesAvailable() - o2.getCountOfInstancesAvailable());
            isAscForInstances.value = false;
        }
        else {
            listOfBooks.sort((o1, o2) -> o2.getCountOfInstancesAvailable() - o1.getCountOfInstancesAvailable());
            isAscForInstances.value = true;
        }
        return listOfBooks;
    }

    private static List<Book> sortByTitle(List<Book> listOfBooks, BooleanHolder isAscForAvailable){
        if(isAscForAvailable.value) {
            listOfBooks.sort((o1, o2) -> o1.getRussianNameOfBook().compareTo(o2.getRussianNameOfBook()));
            isAscForAvailable.value = false;
        }
        else {
            listOfBooks.sort((o1, o2) -> o2.getRussianNameOfBook().compareTo(o1.getRussianNameOfBook()));
            isAscForAvailable.value = true;
        }
        return listOfBooks;
    }

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
        if(checkLastPage > 0)
            return countOfPages + 1;
        return countOfPages;
    }

    public static List<Book> loadListOfBooksFromDB(){
        List<Book> listOfBooks = new ArrayList<>();
        Connector.loadDriver();
        Connection connection = Connector.getConnection();
        BookDAO.selectTitleAndYearForBooks(listOfBooks, connection);
        BookDAO.selectCountOfInstancesForBooks(listOfBooks, connection);
        BookDAO.selectGenreForBooks(listOfBooks, connection);
        sortListOfBooksByAvailableAndRussianTitle(listOfBooks);
        Connector.closeConnection(connection);
        return listOfBooks;
    }

    public static void sortListOfBooksByAvailableAndRussianTitle(List<Book> listOfBooks){
        listOfBooks.sort((o1, o2) -> {
            if(o1.getCountOfInstancesAvailable() - o2.getCountOfInstancesAvailable() == 0)
                return o1.getRussianNameOfBook().compareTo(o2.getRussianNameOfBook());
            else
                return o1.getCountOfInstancesAvailable() - o2.getCountOfInstancesAvailable();
        });
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
