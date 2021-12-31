package com.ita.u1.internetLibrary.dao;

import com.ita.u1.internetLibrary.model.Book;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookDAO {

    static public void selectTitleAndYearForBooks(List<Book> listOfBooks, Connection connection) throws SQLException{

        String sqlQuery = "SELECT book_id, russian_name, year_of_publication FROM books";
        try(Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(sqlQuery)
        ) {
            while(rs.next()){
                listOfBooks.add(new Book(rs.getString("russian_name"), null, null,
                        rs.getInt("year_of_publication"),null, rs.getInt("book_id")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    static public void selectGenreForBooks(List<Book> listOfBooks, Connection connection) throws SQLException{
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
            try(Statement stmt = connection.createStatement();
                ResultSet rs = stmt.executeQuery(sqlQuery)
            ) {
                while(rs.next()){
                    genres.add(rs.getString("genre"));
                }

                book.setGenres(genres);

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    static public void selectCountOfInstancesForBooks(List<Book> listOfBooks, Connection connection) throws SQLException{
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

            try(Statement stmt = connection.createStatement();
                ResultSet resultSetCountInstances = stmt.executeQuery(sqlQueryForCountInstances)
            ) {
                while(resultSetCountInstances.next()){
                    book.setCountOfInstances(resultSetCountInstances.getInt("count"));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

            try(Statement stmt = connection.createStatement();
                ResultSet resultSetCountAccessInstances = stmt.executeQuery(sqlQueryForCountAccessInstances)
            ) {
                while(resultSetCountAccessInstances.next()){
                    book.setCountOfInstancesAvailable(resultSetCountAccessInstances.getInt("count"));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
