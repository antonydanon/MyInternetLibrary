package com.ita.u1.internetLibrary.dao;

import java.sql.*;
import java.util.List;

import com.ita.u1.internetLibrary.model.Reader;

public class ReaderDAO {
    static final String user = "postgres";
    static final String password = "anton";
    static final String url = "jdbc:postgresql://127.0.0.1:5432/library";

    static public void updateTableOfReaders(Reader reader) throws SQLException {
        String sqlQuery = "";
        try {
            Class.forName("org.postgresql.Driver");
            System.out.println("подключилось");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        try(Connection conn = DriverManager.getConnection(url, user, password)){
            Statement statement = conn.createStatement();
            sqlQuery = "INSERT INTO readers (surname, name, patronymic, passport_id, email, address, birthday) VALUES" +
                    " ('"+reader.getSurname()+"', '"+reader.getName()+
                    "', '"+reader.getPatronymic()+"', '"+reader.getPassport_id()+"', '"+reader.getEmail()+
                    "', '"+reader.getAddress()+"', '"+reader.getBirthday()+"')";
            statement.executeUpdate(sqlQuery);
        } catch (Exception ex){
            System.out.println("ошибка");
            System.out.println(ex.getMessage());
        }
    }

    static public void selectAllReaders(List<Reader> listOfReaders) throws SQLException {
        String sqlQuery = "SELECT surname, name, birthday, address, email FROM readers";
        try {
            Class.forName("org.postgresql.Driver");
            System.out.println("подключилось");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try(Connection conn = DriverManager.getConnection(url, user, password);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sqlQuery);
        ) {
            while(rs.next()){
                listOfReaders.add(new Reader(rs.getString("surname"), rs.getString("name"),
                        null, null, rs.getString("email"),rs.getString("address"),
                        rs.getString("birthday")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
