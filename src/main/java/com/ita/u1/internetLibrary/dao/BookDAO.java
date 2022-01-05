package com.ita.u1.internetLibrary.dao;

import com.ita.u1.internetLibrary.model.Book;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookDAO {

    static public void selectTitleAndYearForBooks(List<Book> listOfBooks, Connection connection){
        String sqlQuery = "SELECT book_id, russian_name, year_of_publication FROM books";
        try(Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sqlQuery)){
            while(resultSet.next()){
                listOfBooks.add(new Book(resultSet.getString("russian_name"), null, null,
                        resultSet.getInt("year_of_publication"),null, resultSet.getInt("book_id")));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    static public void selectGenreForBooks(List<Book> listOfBooks, Connection connection){
        Connector.loadDriver();
        int bookId = 0;
        String sqlQuery = "";

        for (var book : listOfBooks) {
            List<String> genres = new ArrayList<>();
            bookId = book.getId();
            sqlQuery = "SELECT genres.genre FROM books " +
                    "JOIN books_genres ON books.book_id = books_genres.book_id " +
                    "JOIN genres ON genres.genre_id = books_genres.genre_id " +
                    "where books.book_id = " + bookId;
            try(Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(sqlQuery)){
                while(resultSet.next()){
                    genres.add(resultSet.getString("genre"));
                }
                book.setGenres(genres);
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    static public void selectCountOfInstancesForBooks(List<Book> listOfBooks, Connection connection){
        Connector.loadDriver();
        int bookId = 0;
        String sqlQueryForCountInstances = "";
        String sqlQueryForCountAccessInstances = "";

        for (var book: listOfBooks) {
            bookId = book.getId();
            sqlQueryForCountInstances = "SELECT count(instances.access) " +
                    "FROM books JOIN instances " +
                    "ON instances.fk_instances_books = books.book_id " +
                    "WHERE book_id = " + bookId;
            sqlQueryForCountAccessInstances = "SELECT count(instances.access) " +
                    "FROM books JOIN instances " +
                    "ON instances.fk_instances_books = books.book_id " +
                    "WHERE book_id = " + bookId + " and access = true";

            try(Statement statement = connection.createStatement();
                ResultSet resultSetCountInstances = statement.executeQuery(sqlQueryForCountInstances)){
                while(resultSetCountInstances.next()){
                    book.setCountOfInstances(resultSetCountInstances.getInt("count"));
                }
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }

            try(Statement statement = connection.createStatement();
                ResultSet resultSetCountAccessInstances = statement.executeQuery(sqlQueryForCountAccessInstances)){
                while(resultSetCountAccessInstances.next()){
                    book.setCountOfInstancesAvailable(resultSetCountAccessInstances.getInt("count"));
                }
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
