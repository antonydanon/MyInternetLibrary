package com.ita.u1.internetLibrary.service;

import com.ita.u1.internetLibrary.Constants;
import com.ita.u1.internetLibrary.dao.Connector;
import com.ita.u1.internetLibrary.dao.MailDAO;
import com.ita.u1.internetLibrary.model.MessageForReader;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

public class MailManagement {
    public static void sendMailToReaderFirstTime(){
        Connector.loadDriver();
        Connection connection = Connector.getConnection();
        List<Integer> readersId = MailDAO.getReadersIdWithDebts(connection, Constants.countOfDaysInMonth);
        if(!readersId.isEmpty()){
            List<List<String>> titlesOfBooksForReaders = MailDAO.getTitlesOfBooksForReaders(connection, readersId);
            List<MessageForReader> messagesForReaders = MailDAO.getEmailsAndNamesOfReaders(connection, readersId, titlesOfBooksForReaders);
            for (var message : messagesForReaders) {
                MessageSender.sendEmail(message.getEmail(), message.getReaderName(), message.getTitlesOfBooks(), -1);
            }
        }
        Connector.closeConnection(connection);
    }

    public static void sendMailToReaderAfterFiveDays(){
        Connector.loadDriver();
        Connection connection = Connector.getConnection();
        List<Integer> readersId = MailDAO.getReadersIdWithDebts(connection, Constants.countOfDaysInMonth + Constants.countOfDaysForEveryDayMessage);
        if(!readersId.isEmpty()){
            List<List<String>> titlesOfBooksForReaders = MailDAO.getTitlesOfBooksForReaders(connection, readersId);
            List<MessageForReader> messagesForReaders = MailDAO.getEmailsAndNamesOfReaders(connection, readersId, titlesOfBooksForReaders);
            List<Integer> listOfPrices = getAllPrices(readersId);
            int listIndex = 0;
            for (var message : messagesForReaders) {
                MessageSender.sendEmail(message.getEmail(), message.getReaderName(), message.getTitlesOfBooks(), listOfPrices.get(listIndex));
                listIndex++;
            }
        }
        Connector.closeConnection(connection);
    }

    private static List<Integer> getAllPrices(List<Integer> readersId){
        List<Integer> pricesForReaders = new ArrayList<>();
        for (var readerId : readersId) {
            pricesForReaders.add(BookReturningManagement.getFinalPrice(readerId));
        }
        return pricesForReaders;
    }
}
