package com.ita.u1.internetLibrary.dao;

import com.ita.u1.internetLibrary.model.Order;
import com.ita.u1.internetLibrary.model.PriceOfBook;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class OrderDAO {

    static public List<PriceOfBook> getPricesFromDB(List<String> titlesOfBooks, Connection connection) throws SQLException {
        List<PriceOfBook> pricesOfBooks = new ArrayList<>();
        String sqlQuery;
        for (var titleOfBook : titlesOfBooks) {
            sqlQuery = "SELECT price, price_per_day FROM books " +
                    "where original_name = '" + titleOfBook + "';";
            try(Statement stmt = connection.createStatement();
                ResultSet resultSet = stmt.executeQuery(sqlQuery)
            ) {
                while(resultSet.next()){
                    pricesOfBooks.add(new PriceOfBook(resultSet.getInt("price"), resultSet.getInt("price_per_day")));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return pricesOfBooks;
    }

    public static int getReaderId(Connection connection, String passportID){
        String sqlQuery = "SELECT readers_id FROM readers WHERE passport_id = '" + passportID + "'";
        int readerId = 0;
        try(Statement stmt = connection.createStatement();
            ResultSet resultSet = stmt.executeQuery(sqlQuery)
        ) {
            while(resultSet.next()){
                readerId = resultSet.getInt("readers_id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return readerId;
    }

    public static List<Order> getInstanceIdForAllTitlesOfBook(Connection connection, List<String> titlesOfBooks, int readerId) {
        List<Order> orderListOfReader = new ArrayList<>();
        String sqlQuery = "";
        for(var titleOfBook : titlesOfBooks){
            sqlQuery = "select  instances.instance_id from instances " +
                    "join books on instances.fk_instances_books = books.book_id " +
                    "where books.original_name = '" + titleOfBook + "' and access = 'true'" +
                    "limit(1)";
            try(Statement stmt = connection.createStatement();
                ResultSet resultSet = stmt.executeQuery(sqlQuery)
            ) {
                while(resultSet.next()){
                    orderListOfReader.add(new Order(resultSet.getInt("instance_id"),readerId, titleOfBook));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return orderListOfReader;
    }

    public static void loadOrderInDB(Connection connection, List<Order> orderListOfReader){
        String sqlQuery = "";
        for(var order : orderListOfReader) {
            try {
                Statement statement = connection.createStatement();
                sqlQuery = "insert into orders (order_date, fk_orders_instances, fk_orders_readers)\n" +
                        "values('" + LocalDate.now() + "'," + order.getInstanceId() +"," + order.getReaderId() + ")";
                statement.executeUpdate(sqlQuery);
            } catch (Exception ex) {
                System.out.println("ошибка");
                System.out.println(ex.getMessage());
            }
        }
    }

    public static void makeInstancesOfBooksUnavailable(Connection connection, List<Order> orderListOfReader, int readerId){
        String sqlQuery = "";
        for(var order : orderListOfReader) {
            try {
                Statement statement = connection.createStatement();
                sqlQuery = "update instances set access = false, reader_id = " + readerId  + " where instance_id = " + order.getInstanceId();
                statement.executeUpdate(sqlQuery);
            } catch (Exception ex) {
                System.out.println("error");
                System.out.println(ex.getMessage());
            }
        }
    }

    public static boolean readerReturnedBooks(Connection connection, int readerId){
        String sqlQuery = "SELECT fk_orders_readers FROM orders";
        try(Statement stmt = connection.createStatement();
            ResultSet resultSet = stmt.executeQuery(sqlQuery)
        ) {
            while(resultSet.next()){
                if(resultSet.getInt("fk_orders_readers") == readerId){
                    return false;
                }
            }
        } catch (SQLException e) {
            System.out.println("error");
            e.printStackTrace();
        }
        return true;
    }

}
