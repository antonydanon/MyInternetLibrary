package com.ita.u1.internetLibrary.service;

import com.ita.u1.internetLibrary.Constants;
import com.ita.u1.internetLibrary.dao.Connector;
import com.ita.u1.internetLibrary.dao.ReaderDAO;
import com.ita.u1.internetLibrary.model.Reader;

import java.sql.Connection;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ReaderManagement {
    public static void addNewReaderInDB(Reader reader){
        if(readerValid(reader) && readerEmailAndPassportIdUnique(reader)) {
            ReaderDAO.updateTableOfReaders(reader);
        }
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

    private static boolean readerEmailAndPassportIdUnique(Reader reader){
        Connector.loadDriver();
        Connection connection = Connector.getConnection();
        if(ReaderDAO.haveNotSamePassportId(reader.getPassport_id(), connection)){
            if(ReaderDAO.haveNotSameEmail(reader.getEmail(), connection)){
                Connector.closeConnection(connection);
                return true;
            }
            else {
                Connector.closeConnection(connection);
                return false;
            }
        }else {
            Connector.closeConnection(connection);
            return false;
        }
    }

    private static boolean readerValid(Reader reader){
        boolean isPatronymic = false;
        if(snpNotValid(reader.getName(), isPatronymic))
            return false;
        if(snpNotValid(reader.getSurname(), isPatronymic))
            return false;
        isPatronymic = true;
        if(snpNotValid(reader.getPatronymic(), isPatronymic))
            return false;
        if(passportIdNotValid(reader.getPassport_id()))
            return false;
        if(emailNotValid(reader.getEmail()))
            return false;
        if(addressNotValid(reader.getAddress()))
            return false;
        if(birthdayNotValid(reader.getBirthday()))
            return false;
        return true;
    }

    private static boolean snpNotValid(String snp, boolean isPatronymic){
        if(isPatronymic)
            return !snp.matches("([A-Za-z]{1,30})|");
        else
            return !snp.matches("[A-Za-z]{1,30}");
    }

    public static boolean emailNotValid(String email){
        return !(email.matches("\\w+([\\.-]?\\w+)*@\\w+([\\.-]?\\w+)*\\.\\w{2,4}") && email.length() <= Constants.emailMaxLength);
    }

    private static boolean passportIdNotValid(String passportId){
        return !passportId.matches("[A-Z]{2}[0-9]{7}|");
    }

    private static boolean addressNotValid(String address){
        return !address.matches("[A-Za-z0-9\s]{2,100}|");
    }

    private static boolean birthdayNotValid(LocalDate birthday){
        return LocalDate.now().compareTo(birthday) <= 0;
    }

}
