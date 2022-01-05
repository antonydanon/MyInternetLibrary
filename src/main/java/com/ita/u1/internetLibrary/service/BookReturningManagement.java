package com.ita.u1.internetLibrary.service;

import com.ita.u1.internetLibrary.Constants;
import com.ita.u1.internetLibrary.dao.BookReturningDAO;
import com.ita.u1.internetLibrary.dao.Connector;
import com.ita.u1.internetLibrary.dao.OrderDAO;
import com.ita.u1.internetLibrary.model.PriceOfBook;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.GregorianCalendar;
import java.util.List;

public class BookReturningManagement {
    public static void returningOfBooks(String passportID){
        int readerId = 0;
        int price = 0;
        try {
            Connector.loadDriver();
            Connection connection = Connector.getConnection();
            readerId = OrderDAO.getReaderId(connection, passportID);
            price = getFinalPrice(connection, readerId);
            BookReturningDAO.makePayment(connection, price, readerId);
            BookReturningDAO.makeInstancesAvailable(connection, readerId);
            BookReturningDAO.deleteOrders(connection, readerId);
            Connector.closeConnection(connection);
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }

    }

    private static int getFinalPrice(Connection connection, int readerId){
        List<Integer> listOfIdOfInstances = BookReturningDAO.getAllIdOfInstancesInOrderTableOfDB(connection, readerId);
        List<PriceOfBook> listOfPricesOfBooks = BookReturningDAO.getAllPricesOfOrder(connection, listOfIdOfInstances);
        GregorianCalendar orderDate = BookReturningDAO.getOrderDate(connection, readerId);
        long countOfDays = getCountOfDaysBetweenDates(orderDate);
        float discount = OrderManagement.getDiscount(listOfPricesOfBooks);
        int finalPrice = 0;
        for (var pricesOfBooks : listOfPricesOfBooks){
            finalPrice += pricesOfBooks.getPrice() + countOfDays * pricesOfBooks.getPricePerDay();
        }
        finalPrice *= (1-discount);
        finalPrice += getPenalty(countOfDays, finalPrice);
        return finalPrice;
    }

    private static long getCountOfDaysBetweenDates(GregorianCalendar orderDate){
        LocalDate retDate = LocalDate.now();
        int year = retDate.getYear();
        int month = retDate.getMonthValue();
        int day = retDate.getDayOfMonth();
        GregorianCalendar returnDate = new GregorianCalendar(year, month, day);
        int countOfDays = (int) ((returnDate.getTimeInMillis()-orderDate.getTimeInMillis())/Constants.milliseconds/Constants.seconds/ Constants.minutes/Constants.hours);
        return countOfDays;
    }

    private static int getPenalty(long countOfDays, int finalPrice){
        if(countOfDays > Constants.countOfDaysInMonth){
            return (int)((countOfDays - Constants.countOfDaysInMonth) * Constants.penalty * finalPrice);
        } else {
            return 0;
        }
    }

}
