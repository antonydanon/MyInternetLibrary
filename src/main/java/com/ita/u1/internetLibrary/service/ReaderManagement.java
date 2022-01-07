package com.ita.u1.internetLibrary.service;

import com.ita.u1.internetLibrary.dao.ReaderDAO;
import com.ita.u1.internetLibrary.model.Reader;

import java.util.ArrayList;
import java.util.List;

public class ReaderManagement {
    public static void addNewReaderInDB(Reader reader){
        ReaderDAO.updateTableOfReaders(reader);
    }

    public static List<Reader> loadListOfReadersFromDB(){
        List<Reader> listOfReaders = new ArrayList<>();
        ReaderDAO.selectAllReaders(listOfReaders);
        return listOfReaders;
    }

    public static List<Reader> loadListOfReadersWithoutDebtsFromDB(){
        List<Reader> listOfReadersWithoutDebts = new ArrayList<>();
        ReaderDAO.selectAllReadersWithoutDebts(listOfReadersWithoutDebts);
        return listOfReadersWithoutDebts;
    }

    public static List<Reader> loadListOfReadersWithDebtsFromDB(){
        List<Reader> listOfReadersWithDebts = new ArrayList<>();
        ReaderDAO.selectAllReadersWithDebts(listOfReadersWithDebts);
        return listOfReadersWithDebts;
    }
}
