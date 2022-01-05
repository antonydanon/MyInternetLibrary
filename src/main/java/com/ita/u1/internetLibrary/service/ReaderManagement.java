package com.ita.u1.internetLibrary.service;

import com.ita.u1.internetLibrary.dao.ReaderDAO;
import com.ita.u1.internetLibrary.model.Reader;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ReaderManagement {
    public static void addNewReaderInDB(Reader reader){
        try {
            ReaderDAO.updateTableOfReaders(reader);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static List<Reader> loadListOfReadersFromDB(){
        List<Reader> listOfReaders = new ArrayList<>();
        try {
            ReaderDAO.selectAllReaders(listOfReaders);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listOfReaders;
    }
}
