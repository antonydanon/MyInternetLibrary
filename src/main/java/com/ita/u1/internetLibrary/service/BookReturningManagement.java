package com.ita.u1.internetLibrary.service;

import com.ita.u1.internetLibrary.Constants;
import com.ita.u1.internetLibrary.dao.BookReturningDAO;
import com.ita.u1.internetLibrary.dao.Connector;
import com.ita.u1.internetLibrary.dao.OrderDAO;
import com.ita.u1.internetLibrary.model.PriceOfBook;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.Date;
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
        ZonedDateTime orderDate = null;
        List<Integer> listOfIdOfInstances = BookReturningDAO.getAllIdOfInstancesAndOrderDateInOrderTableOfDB(connection, readerId, orderDate);
        List<PriceOfBook> listOfPricesOfBooks = BookReturningDAO.getAllPricesOfOrder(connection, listOfIdOfInstances);
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

    private static long getCountOfDaysBetweenDates(ZonedDateTime orderDate){
        long countOfDays = 0;
        //SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        /*LocalDate date = LocalDate.now();
        Date returnDate = dateFormat.parse(date.getDayOfMonth()+"."+date.getMonthValue()+"."+date.getYear());
        long milliseconds = returnDate.getTime() - orderDate.getTime();
        countOfDays = (int) (milliseconds / (Constants.hours * Constants.minutes * Constants.seconds * Constants.milliseconds));*/
        ZonedDateTime returnDate = ZonedDateTime.now();
        Duration duration = Duration.between(orderDate, returnDate);
        countOfDays = duration.toDays();
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
