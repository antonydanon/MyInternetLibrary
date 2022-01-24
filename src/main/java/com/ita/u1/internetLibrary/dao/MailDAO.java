package com.ita.u1.internetLibrary.dao;

import com.ita.u1.internetLibrary.model.MessageForReader;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class MailDAO {
    public static List<Integer> getReadersIdWithDebts(Connection connection, int days){
        List<Integer> readersId = new ArrayList<>();
        String sqlQuery = "SELECT order_date, fk_orders_readers " +
                "FROM orders GROUP BY fk_orders_readers, order_date ORDER BY fk_orders_readers;";
        try(Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sqlQuery)){
            while(resultSet.next()){
                LocalDate returnDate = LocalDate.parse(resultSet.getString("order_date")).plusDays(days);
                if(returnDate.compareTo(LocalDate.now()) == 0)
                    readersId.add(resultSet.getInt("fk_orders_readers"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return readersId;
    }

    public static List<List<String>> getTitlesOfBooksForReaders(Connection connection, List<Integer> readersId){
        List<List<String>> titlesOfBooksForReaders = new ArrayList<>();
        String sqlQuery = "";
        for (var readerId : readersId) {
            sqlQuery = "SELECT russian_name FROM books " +
                    "JOIN instances ON books.book_id = instances.fk_instances_books " +
                    "JOIN orders ON instances.instance_id = orders.fk_orders_instances " +
                    "WHERE orders.fk_orders_readers = " + readerId;
            try(Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(sqlQuery)){
                List<String> titlesOfBooks = new ArrayList<>();
                while(resultSet.next()){
                    titlesOfBooks.add(resultSet.getString("russian_name"));
                }
                titlesOfBooksForReaders.add(titlesOfBooks);
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
        return titlesOfBooksForReaders;
    }

    public static List<MessageForReader> getEmailsAndNamesOfReaders(Connection connection, List<Integer> readersId, List<List<String>> titlesOfBooks){
        List<MessageForReader> messagesForReaders = new ArrayList<>();
        String sqlQuery = "";
        int listIndex = 0;
        for (var readerId : readersId) {
            sqlQuery = "SELECT email, name FROM readers\n" +
                    "WHERE readers_id = " + readerId;
            try(Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(sqlQuery)){
                if(resultSet.next()){
                    String name = resultSet.getString("name");
                    String email = resultSet.getString("email");
                    messagesForReaders.add(new MessageForReader(name, titlesOfBooks.get(listIndex), 0, email));
                }
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
            listIndex++;
        }
        return messagesForReaders;
    }
}
