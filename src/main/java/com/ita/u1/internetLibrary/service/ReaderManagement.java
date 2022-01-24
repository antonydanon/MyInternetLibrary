package com.ita.u1.internetLibrary.service;

import com.ita.u1.internetLibrary.Constants;
import com.ita.u1.internetLibrary.dao.Connector;
import com.ita.u1.internetLibrary.dao.ReaderDAO;
import com.ita.u1.internetLibrary.model.Reader;
import com.ita.u1.internetLibrary.model.additional.BooleanHolder;

import java.sql.Connection;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ReaderManagement {
    public static List<Reader> sortListOfReaders(List<Reader> listOfReaders, String sortParam, BooleanHolder
            isAscForSurname, BooleanHolder isAscForName, BooleanHolder isAscForBirthday, BooleanHolder isAscForAddress, BooleanHolder isAscForEmail){
        if(sortParam.equals("Surname")){
            listOfReaders = sortBySurname(listOfReaders, isAscForSurname);
        }
        if(sortParam.equals("Name")){
            listOfReaders = sortByName(listOfReaders, isAscForName);
        }
        if(sortParam.equals("Birthday")){
            listOfReaders = sortByBirthday(listOfReaders, isAscForBirthday);
        }
        if(sortParam.equals("Address")){
            listOfReaders = sortByAddress(listOfReaders, isAscForAddress);
        }
        if(sortParam.equals("Email")){
            listOfReaders = sortByEmail(listOfReaders, isAscForEmail);
        }
        return listOfReaders;
    }

    private static List<Reader> sortBySurname(List<Reader> listOfReaders, BooleanHolder isAscForSurname){
        if(isAscForSurname.value) {
            listOfReaders.sort((o1, o2) -> o1.getSurname().compareTo(o2.getSurname()));
            isAscForSurname.value = false;
        }
        else {
            listOfReaders.sort((o1, o2) -> o2.getSurname().compareTo(o1.getSurname()));
            isAscForSurname.value = true;
        }
        return listOfReaders;
    }

    private static List<Reader> sortByName(List<Reader> listOfReaders, BooleanHolder isAscForName){
        if(isAscForName.value) {
            listOfReaders.sort((o1, o2) -> o1.getName().compareTo(o2.getName()));
            isAscForName.value = false;
        }
        else {
            listOfReaders.sort((o1, o2) -> o2.getName().compareTo(o1.getName()));
            isAscForName.value = true;
        }
        return listOfReaders;
    }

    private static List<Reader> sortByBirthday(List<Reader> listOfReaders, BooleanHolder isAscForBirthday){
        if(isAscForBirthday.value) {
            listOfReaders.sort((o1, o2) -> o1.getBirthday().compareTo(o2.getBirthday()));
            isAscForBirthday.value = false;
        }
        else {
            listOfReaders.sort((o1, o2) -> o2.getBirthday().compareTo(o1.getBirthday()));
            isAscForBirthday.value = true;
        }
        return listOfReaders;
    }

    private static List<Reader> sortByAddress(List<Reader> listOfReaders, BooleanHolder isAscForAddress){
        if(isAscForAddress.value) {
            listOfReaders.sort((o1, o2) -> o1.getAddress().compareTo(o2.getAddress()));
            isAscForAddress.value = false;
        }
        else {
            listOfReaders.sort((o1, o2) -> o2.getAddress().compareTo(o1.getAddress()));
            isAscForAddress.value = true;
        }
        return listOfReaders;
    }

    private static List<Reader> sortByEmail(List<Reader> listOfReaders, BooleanHolder isAscForEmail){
        if(isAscForEmail.value) {
            listOfReaders.sort((o1, o2) -> o1.getEmail().compareTo(o2.getEmail()));
            isAscForEmail.value = false;
        }
        else {
            listOfReaders.sort((o1, o2) -> o2.getEmail().compareTo(o1.getEmail()));
            isAscForEmail.value = true;
        }
        return listOfReaders;
    }

    public static List<Reader> getListOfReadersForCurrentPage(List<Reader> listOfReaders, int currentPage){
        List<Reader> listOfReadersForCurrentPage = new ArrayList<>();
        int endPosition = Constants.countOfRecordsForPage * currentPage;
        currentPage--;
        int startPosition = currentPage * Constants.countOfRecordsForPage;
        for(int i = startPosition; i < endPosition; i++){
            if(listOfReaders.size() > i)
                listOfReadersForCurrentPage.add(listOfReaders.get(i));
        }
        return listOfReadersForCurrentPage;
    }

    public static int getCountOfPages(List<Reader> listOfReaders){
        int countOfPages = listOfReaders.size() / Constants.countOfRecordsForPage;
        int checkLastPage = listOfReaders.size() % Constants.countOfRecordsForPage;
        if(checkLastPage > 0)
            return countOfPages + 1;
        return countOfPages;
    }

    public static int getCurrentPage(Map<String, String[]> params, String buttonParam, String currPageParam){
        int currentPage;
        if(params.containsKey("currentPageReaders") && buttonParam.equals("  >  ") ) {
            currentPage = Integer.parseInt(currPageParam);
            currentPage++;
        }
        else {
            if(params.containsKey("currentPageReaders") && buttonParam.equals("  <  ")) {
                currentPage = Integer.parseInt(currPageParam);
                currentPage--;
            } else
                currentPage = 1;
        }
        return currentPage;
    }

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
