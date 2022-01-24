package com.ita.u1.internetLibrary.dao;

import com.ita.u1.internetLibrary.model.BookReturning;
import com.ita.u1.internetLibrary.model.PriceOfBook;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

public class BookReturningDAO{
    public static void makePayment(Connection connection, int price, int readerId){
        String sqlQuery = "INSERT INTO book_returning (date_returning, price, fk_book_returning_readers)" +
                " VALUES ('" + LocalDate.now() + "',"  + price + "," + readerId + ")";
        try(Statement statement = connection.createStatement()){
            statement.executeUpdate(sqlQuery);
        } catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }

    public static List<Integer> getCountOfRating(List<BookReturning> booksWithRating, Connection connection){
        List<Integer> countsOfRatings = new ArrayList<>();
        String sqlQuery;
        for (var book : booksWithRating) {
            sqlQuery = "SELECT count_rating FROM books WHERE original_name = '" + book.getTitle() + "'";
            try(Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(sqlQuery)){
                while(resultSet.next()){
                    countsOfRatings.add(resultSet.getInt("count_rating"));
                }
            } catch (SQLException e){
                System.out.println(e.getMessage());
            }
        }
        return countsOfRatings;
    }

    public static List<Integer> getSumOfRating(List<BookReturning> booksWithRating, Connection connection){
        List<Integer> sumOfRatings = new ArrayList<>();
        String sqlQuery;
        for (var book : booksWithRating) {
            sqlQuery = "SELECT sum_rating FROM books WHERE original_name = '" + book.getTitle() + "'";
            try(Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(sqlQuery)){
                while(resultSet.next()){
                    sumOfRatings.add(resultSet.getInt("sum_rating"));
                }
            } catch (SQLException e){
                System.out.println(e.getMessage());
            }
        }
        return sumOfRatings;
    }

   public static void updateCountOfRating(List<BookReturning> booksWithRating, List<Integer> countsOfRatings, Connection connection){
        String sqlQuery;
       for (int i = 0; i < booksWithRating.size(); i++) {
           sqlQuery = "  UPDATE books SET count_rating = " + (countsOfRatings.get(i)+1) + " WHERE original_name = '" + booksWithRating.get(i).getTitle() + "'";
           try(Statement statement = connection.createStatement()){
               statement.executeUpdate(sqlQuery);
           } catch (SQLException e){
               System.out.println(e.getMessage());
           }
       }
   }

    public static void updateSumOfRating(List<BookReturning> booksWithRating, List<Integer> sumOfRatings, Connection connection){
        String sqlQuery;
        for (int i = 0; i < booksWithRating.size(); i++) {
            sqlQuery = "  UPDATE books SET sum_rating = " + (sumOfRatings.get(i)+booksWithRating.get(i).getRating()) + " WHERE original_name = '" + booksWithRating.get(i).getTitle() + "'";
            try(Statement statement = connection.createStatement()){
                statement.executeUpdate(sqlQuery);
            } catch (SQLException e){
                System.out.println(e.getMessage());
            }
        }
    }



    public static List<String> getTitlesOfBookFromDB(int readerId, Connection connection){
        List<String> titlesOfBooks = new ArrayList<>();
        String sqlQuery = "SELECT original_name FROM books JOIN instances ON books.book_id = instances.fk_instances_books WHERE access = 'false' AND reader_id = " +  readerId;
        try(Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sqlQuery)){
            while(resultSet.next()){
                titlesOfBooks.add(resultSet.getString("original_name"));
            }
        } catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return titlesOfBooks;
    }

    public static void makeInstancesAvailable(Connection connection, int readerId){
        String sqlQuery = "UPDATE instances SET access = 'true' WHERE reader_id = " + readerId;
        try(Statement statement = connection.createStatement()){
            statement.executeUpdate(sqlQuery);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void deleteOrders(Connection connection, int readerId){
        String sqlQuery = "DELETE FROM orders WHERE fk_orders_readers = " + readerId;
        try(Statement statement = connection.createStatement()){
            statement.executeUpdate(sqlQuery);
        } catch(SQLException e){
            System.out.println(e.getMessage());
        }
    }

    public static List<Integer> getAllIdOfInstancesInOrderTableOfDB(Connection connection, int readerId){
        List<Integer> listOfIdOfInstances = new ArrayList<>();
        String sqlQuery = "SELECT fk_orders_instances FROM orders WHERE fk_orders_readers = " + readerId;
        try(Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sqlQuery)){
            while(resultSet.next()){
                listOfIdOfInstances.add(resultSet.getInt("fk_orders_instances"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return listOfIdOfInstances;
    }

    public static GregorianCalendar getOrderDate(Connection connection, int readerId){
        GregorianCalendar orderDate = null;
        String sqlQuery = "SELECT  order_date FROM orders WHERE fk_orders_readers = " + readerId;
        try(Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sqlQuery)){
            if(resultSet.next()){
                String date = resultSet.getString("order_date");
                int year = Integer.parseInt(date.substring(0,4));
                int month = Integer.parseInt(date.substring(5,7));
                int day = Integer.parseInt(date.substring(8,10));
                orderDate = new GregorianCalendar(year, month, day);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return orderDate;
    }

    public static List<PriceOfBook> getAllPricesOfOrder(Connection connection, List<Integer> listOfIdOfInstances){
        List<PriceOfBook> listOfPricesOfBooks = new ArrayList<>();
        String sqlQuery = "";
        for(var idOfInstances : listOfIdOfInstances){
            sqlQuery = "SELECT price, price_per_day FROM books" +
                    " JOIN instances ON instances.fk_instances_books = books.book_id" +
                    " WHERE instance_id = " + idOfInstances;
            try(Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(sqlQuery)){
                while(resultSet.next()){
                    listOfPricesOfBooks.add(new PriceOfBook(resultSet.getInt("price"), resultSet.getInt("price_per_day")));
                }
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
        return listOfPricesOfBooks;
    }
}
