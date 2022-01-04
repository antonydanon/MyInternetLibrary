package com.ita.u1.internetLibrary.dao;

import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;
import java.util.List;

import com.ita.u1.internetLibrary.model.Reader;

public class ReaderDAO {

    static public void updateTableOfReaders(Reader reader) throws SQLException {
        Connector.loadDriver();

        String sqlQuery = "";
        try(Connection conn = Connector.getConnection()){
            Statement statement = conn.createStatement();
            sqlQuery = "INSERT INTO readers (surname, name, patronymic, passport_id, email, address, birthday) VALUES" +
                    " ('"+reader.getSurname()+"', '"+reader.getName()+
                    "', '"+reader.getPatronymic()+"', '"+reader.getPassport_id()+"', '"+reader.getEmail()+
                    "', '"+reader.getAddress()+"', '"+reader.getBirthday()+"')";
            statement.executeUpdate(sqlQuery);
        } catch (Exception ex){
            System.out.println("error");
            System.out.println(ex.getMessage());
        }
    }

    static public void selectAllReaders(List<Reader> listOfReaders) throws SQLException {
        Connector.loadDriver();

        String sqlQuery = "SELECT surname, name, birthday, address, email FROM readers";
        try(Connection conn = Connector.getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sqlQuery);
        ) {
            while(rs.next()){
                listOfReaders.add(new Reader(rs.getString("surname"), rs.getString("name"),
                        null, null, rs.getString("email"),rs.getString("address"),
                        LocalDate.parse( rs.getString("birthday"))));
            }
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }
}
