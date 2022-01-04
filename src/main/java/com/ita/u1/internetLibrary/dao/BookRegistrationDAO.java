package com.ita.u1.internetLibrary.dao;

import com.ita.u1.internetLibrary.model.Genre;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class BookRegistrationDAO {
    public static void insertDateAboutBookIntoBooksTable(String russianTitle, String originalTitle, int price, int pricePerDay, int yearPublishing, LocalDate dateRegistration, int countOfPages, Connection connection){
        String sqlQuery = "INSERT INTO books (russian_name, original_name, price, price_per_day, year_of_publication, registration_date, number_of_pages)" +
                " VALUES ('" + russianTitle + "', '" + originalTitle + "'," + price + "," + pricePerDay + ",'" + yearPublishing + "'," + dateRegistration + "," + countOfPages + ")";
        try{
            Statement statement = connection.createStatement();
            statement.executeUpdate(sqlQuery);
        } catch (Exception ex){
            System.out.println("error");
            System.out.println(ex.getMessage());
        }
    }

    public static int getBookId(Connection connection){
        int bookId = 0;
        String sqlQuery = "SELECT MAX(book_id) FROM books";
        try(Statement stmt = connection.createStatement();
            ResultSet resultSet = stmt.executeQuery(sqlQuery)
        ) {
            while(resultSet.next()){
                bookId = resultSet.getInt("max");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bookId;
    }

    public static List<Integer> getGenresId(Connection connection, List<Genre> genres){
        String sqlQuery = "";
        List<Integer> genresId = new ArrayList<>();
            for(var genre : genres){
                sqlQuery = "SELECT genre_id FROM genres WHERE genre = '" + genre.toString() + "'";
                try(Statement stmt = connection.createStatement();
                    ResultSet resultSet = stmt.executeQuery(sqlQuery)
                ) {
                    while(resultSet.next()){
                        genresId.add(resultSet.getInt("genre_id"));
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        return genresId;
    }

    public static void makeConnectionBetweenBooksAndGenresInDB(Connection connection, List<Integer> genresId, int bookId){
        String sqlQuery = "";
        for (var genreId : genresId){
            try {
                Statement statement = connection.createStatement();
                sqlQuery = "insert into books_genres " +
                        "values (" + bookId + "," + genreId + ")";
                statement.executeUpdate(sqlQuery);
            } catch (Exception ex) {
                System.out.println("error");
                System.out.println(ex.getMessage());
            }
        }
    }

    public static void createInstancesInDB(Connection connection, int countOfInstance, int bookId){
        String sqlQuery = "";
        for (int count = 0; count < countOfInstance; count++) {
            try{
                Statement statement = connection.createStatement();
                sqlQuery = "insert into instances (access, fk_instances_books) " +
                        "values('true'," + bookId +")";
                statement.executeUpdate(sqlQuery);
            } catch (Exception ex) {
                System.out.println("error");
                System.out.println(ex.getMessage());
            }
        }
    }

    public static void putPhotosOfBookIntoDB(Connection connection, int bookId, List<byte[]> photosOfBook){
        String sqlQuery = "";
        for(var photo : photosOfBook){
            try{
                Statement statement = connection.createStatement();
                sqlQuery = "insert into photos (photo, fk_photo_books) " +
                        "values(" + photo + "," + bookId +")";
                statement.executeUpdate(sqlQuery);
            } catch (Exception ex) {
                System.out.println("error");
                System.out.println(ex.getMessage());
            }
        }
    }
}
