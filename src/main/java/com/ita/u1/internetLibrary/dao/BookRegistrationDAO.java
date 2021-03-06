package com.ita.u1.internetLibrary.dao;

import com.ita.u1.internetLibrary.model.Author;
import com.ita.u1.internetLibrary.model.Genre;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class BookRegistrationDAO{
    public static void insertDateAboutBookIntoBooksTable(String russianTitle, String originalTitle, int price, int pricePerDay, int yearPublishing, LocalDate dateRegistration, int countOfPages, Connection connection){
        String sqlQuery = "INSERT INTO books (russian_name, original_name, price, price_per_day, year_of_publication, registration_date, number_of_pages)" +
                " VALUES ('" + russianTitle + "', '" + originalTitle + "'," + price + "," + pricePerDay + "," + yearPublishing + ",'" + dateRegistration + "'," + countOfPages + ")";
        try(Statement statement = connection.createStatement()){
            statement.executeUpdate(sqlQuery);
        } catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }

    public static int getBookId(Connection connection){
        int bookId = 0;
        String sqlQuery = "SELECT MAX(book_id) FROM books";
        try(Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sqlQuery)){
            while(resultSet.next()){
                bookId = resultSet.getInt("max");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return bookId;
    }

    public static List<Integer> getGenresId(Connection connection, List<Genre> genres){
        String sqlQuery = "";
        List<Integer> genresId = new ArrayList<>();
            for(var genre : genres){
                sqlQuery = "SELECT genre_id FROM genres WHERE genre = '" + genre.toString() + "'";
                try(Statement statement = connection.createStatement();
                    ResultSet resultSet = statement.executeQuery(sqlQuery)){
                    while(resultSet.next()){
                        genresId.add(resultSet.getInt("genre_id"));
                    }
                } catch (SQLException e) {
                    System.out.println(e.getMessage());
                }
            }
        return genresId;
    }

    public static void makeConnectionBetweenBooksAndGenresInDB(Connection connection, List<Integer> genresId, int bookId){
        String sqlQuery = "";
        for (var genreId : genresId){
            try(Statement statement = connection.createStatement()){
                sqlQuery = "INSERT INTO books_genres " +
                        "VALUES (" + bookId + "," + genreId + ")";
                statement.executeUpdate(sqlQuery);
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public static void createInstancesInDB(Connection connection, int countOfInstance, int bookId){
        String sqlQuery = "";
        for (int count = 0; count < countOfInstance; count++) {
            try(Statement statement = connection.createStatement()){
                sqlQuery = "INSERT INTO instances (access, fk_instances_books) " +
                        "VALUES('true'," + bookId +")";
                statement.executeUpdate(sqlQuery);
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public static void putPhotosOfBookIntoDB(Connection connection, int bookId, List<byte[]> photosOfBook){
        String sqlQuery = "";
        for(var photo : photosOfBook){
            try(Statement statement = connection.createStatement()){
                sqlQuery = "INSERT INTO photo (photo, fk_photo_books) VALUES ('{";
                for (var partOfPhoto : photo) {
                    sqlQuery += partOfPhoto + ",";
                }
                sqlQuery = sqlQuery.substring(0,sqlQuery.length()-2);
                sqlQuery = sqlQuery + "}', " + bookId + ")";
                statement.executeUpdate(sqlQuery);
            } catch (SQLException e){
                System.out.println(e.getMessage());
            }
        }
    }

    public static void putAuthorsOfBookIntoDB(Connection connection, List<Author> authors){
        String sqlQuery = "";
        for(var author : authors){
            sqlQuery = "SELECT name, author_id FROM authors WHERE name = '" + author.getName() + "'";
            try(Statement statement = connection.createStatement()) {
                ResultSet resultSet = statement.executeQuery(sqlQuery);

                if(resultSet.next()){
                    author.setAuthorId(resultSet.getInt("author_id"));
                } else{
                    sqlQuery = "";

                    if(author.getPhotoOfAuthor() != null) {
                        sqlQuery = "INSERT INTO authors (photo, name) VALUES ('{";
                        for (var partOfPhoto : author.getPhotoOfAuthor()) {
                            sqlQuery += partOfPhoto + ",";
                        }
                        sqlQuery = sqlQuery.substring(0, sqlQuery.length() - 2);
                        sqlQuery = sqlQuery + "}', '" + author.getName() + "')";
                    } else {
                        sqlQuery = "INSERT INTO authors (name) VALUES ('" + author.getName() + "')";
                    }
                    statement.executeUpdate(sqlQuery);
                    sqlQuery = "SELECT MAX(author_id) FROM authors";
                    resultSet = statement.executeQuery(sqlQuery);
                    if(resultSet.next()) {
                        author.setAuthorId(resultSet.getInt("max"));
                    }
                }
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public static void makeConnectionBetweenBooksAndAuthors(Connection connection, int bookId, List<Author> authors){
        String sqlQuery = "";
        for (var author : authors) {
            try(Statement statement = connection.createStatement()){
                sqlQuery = "INSERT INTO books_authors " +
                        "VALUES (" + bookId + "," + author.getAuthorId() + ")";
                statement.executeUpdate(sqlQuery);
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
