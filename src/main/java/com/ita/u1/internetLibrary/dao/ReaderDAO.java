package com.ita.u1.internetLibrary.dao;

import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;
import java.util.List;

import com.ita.u1.internetLibrary.model.Reader;

public class ReaderDAO {

    static public void updateTableOfReaders(Reader reader){
        String sqlQuery = "INSERT INTO readers (surname, name, patronymic, passport_id, email, address, birthday) VALUES" +
                " ('"+reader.getSurname()+"', '"+reader.getName()+
                "', '"+reader.getPatronymic()+"', '"+reader.getPassport_id()+"', '"+reader.getEmail()+
                "', '"+reader.getAddress()+"', '"+reader.getBirthday()+"')";

        Connector.loadDriver();
        try(Connection connection = Connector.getConnection()){
            Statement statement = connection.createStatement();
            statement.executeUpdate(sqlQuery);
        } catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }

    static public void selectAllReaders(List<Reader> listOfReaders){
        String sqlQuery = "SELECT readers_id, surname, name, birthday, address, email FROM readers";
        Connector.loadDriver();
        try(Connection connection = Connector.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sqlQuery)
        ) {
            while(resultSet.next()){
                listOfReaders.add(new Reader(resultSet.getString("surname"), resultSet.getString("name"),
                        null, null, resultSet.getString("email"),resultSet.getString("address"),
                                  LocalDate.parse( resultSet.getString("birthday")), resultSet.getInt("readers_id")));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    static public void selectAllReadersWithoutDebts(List<Reader> listOfReaders){
        String sqlQuery = "SELECT readers_id, surname, name, birthday, address, email from readers " +
                "WHERE readers.readers_id NOT IN (SELECT readers_id FROM readers " +
                "                                            JOIN instances " +
                "                                            ON readers_id = reader_id AND access = 'false')";
        Connector.loadDriver();
        try(Connection connection = Connector.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sqlQuery)
        ) {
            while(resultSet.next()){
                listOfReaders.add(new Reader(resultSet.getString("surname"), resultSet.getString("name"),
                        null, null, resultSet.getString("email"),resultSet.getString("address"),
                        LocalDate.parse( resultSet.getString("birthday")), resultSet.getInt("readers_id")));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    static public void selectAllReadersWithDebts(List<Reader> listOfReaders){
        String sqlQuery = "SELECT readers_id, surname, name, birthday, address, email from readers " +
                "WHERE readers.readers_id IN (SELECT readers_id FROM readers " +
                "                                            JOIN instances " +
                "                                            ON readers_id = reader_id AND access = 'false')";
        Connector.loadDriver();
        try(Connection connection = Connector.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sqlQuery)
        ) {
            while(resultSet.next()){
                listOfReaders.add(new Reader(resultSet.getString("surname"), resultSet.getString("name"),
                        null, null, resultSet.getString("email"),resultSet.getString("address"),
                        LocalDate.parse( resultSet.getString("birthday")), resultSet.getInt("readers_id")));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
