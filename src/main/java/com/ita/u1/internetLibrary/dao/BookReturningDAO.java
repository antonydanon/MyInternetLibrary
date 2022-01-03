package com.ita.u1.internetLibrary.dao;

import com.ita.u1.internetLibrary.model.PriceOfBook;
import com.ita.u1.internetLibrary.model.Reader;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BookReturningDAO {
    public static void makePayment(Connection connection, int price, int readerId){
        String sqlQuery = "INSERT INTO book_returning (date_returning, price, fk_book_returning_readers)" +
                " VALUES ('" + LocalDate.now() + "', " + price + ", + readerId +)";
        try{
            Statement statement = connection.createStatement();
            statement.executeUpdate(sqlQuery);
        } catch (Exception ex){
            System.out.println("error");
            System.out.println(ex.getMessage());
        }
    }

    public static void makeInstancesAvailable(Connection connection, int readerId){
        String sqlQuery = "UPDATE instances SET access = 'true' WHERE reader_id = " + readerId;
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate(sqlQuery);
        } catch (Exception ex) {
            System.out.println("error");
            System.out.println(ex.getMessage());
        }
    }

    public static void deleteOrders(Connection connection, int readerId){
        String sqlQuery = "DELETE FROM orders WHERE fk_orders_readers = " + readerId;
        try{
            Statement statement = connection.createStatement();
            int rows = statement.executeUpdate(sqlQuery);
            System.out.printf("%d row(s) deleted", rows);
        } catch(Exception ex){
            System.out.println("Connection failed...");
            System.out.println(ex);
        }
    }

    public static List<Integer> getAllIdOfInstancesAndOrderDateInOrderTableOfDB(Connection connection, int readerId, ZonedDateTime orderDate) {
        List<Integer> listOfIdOfInstances = new ArrayList<>();
        String sqlQuery = "SELECT fk_orders_instances, order_date FROM orders WHERE fk_orders_readers = " + readerId;
        try(Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(sqlQuery);
        ) {
            while(rs.next()){
                listOfIdOfInstances.add(rs.getInt("fk_orders_instances"));
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                orderDate = ZonedDateTime.parse(rs.getString("order_date"), formatter);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listOfIdOfInstances;
    }

    public static List<PriceOfBook> getAllPricesOfOrder(Connection connection, List<Integer> listOfIdOfInstances){
        List<PriceOfBook> listOfPricesOfBooks = new ArrayList<>();
        String sqlQuery = "";
        for(var idOfInstances : listOfIdOfInstances){
            sqlQuery = "SELECT price, price_per_day FROM books" +
                    " JOIN instances ON instances.fk_instances_books = books.book_id" +
                    " WHERE instance_id = " + idOfInstances;
            try(Statement stmt = connection.createStatement();
                ResultSet rs = stmt.executeQuery(sqlQuery);
            ) {
                while(rs.next()){
                    listOfPricesOfBooks.add(new PriceOfBook(rs.getInt("price"), rs.getInt("price_per_day")));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return listOfPricesOfBooks;
    }
}
