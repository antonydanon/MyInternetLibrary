package com.ita.u1.internetLibrary.service;

import com.ita.u1.internetLibrary.Constants;
import com.ita.u1.internetLibrary.dao.Connector;
import com.ita.u1.internetLibrary.dao.OrderDAO;
import com.ita.u1.internetLibrary.model.Order;
import com.ita.u1.internetLibrary.model.PriceOfBook;

import java.sql.Connection;
import java.time.LocalDate;
import java.util.List;

public class OrderManagement {
    public static LocalDate getReturnDate(){
        LocalDate bookReturnDate = LocalDate.now();
        bookReturnDate = bookReturnDate.plusMonths(1);
        return bookReturnDate;
    }

    public static List<String> getTitlesOfBooks(List<Integer> booksId){
        Connector.loadDriver();
        Connection connection = Connector.getConnection();
        List<String> titlesOfBooks = OrderDAO.getTitlesOfBooksFromDB(booksId, connection);
        Connector.closeConnection(connection);
        return titlesOfBooks;
    }

    public static String getPassportId(int readerId){
        Connector.loadDriver();
        Connection connection = Connector.getConnection();
        String passportId = OrderDAO.getPassportIdFromDB(readerId, connection);
        Connector.closeConnection(connection);
        return passportId;
    }

    public static int getOrderPrice(List<String> titlesOfBooks){
        validationOfOrders(titlesOfBooks);
        List<PriceOfBook> pricesOfBooks = loadPricesOfBooksFromDB(titlesOfBooks);
        float discount = getDiscount(pricesOfBooks);
        int orderPrice = getSummaryPrice(discount, pricesOfBooks);
        return orderPrice;
    }

    private static List<String> validationOfOrders(List<String> titlesOfBooks){
        //TODO: make a validation
        return titlesOfBooks;
    }

    public static float getDiscount(List<PriceOfBook> pricesOfBooks){
        if(pricesOfBooks.size() > 4)
            return Constants.discount15percent;
        if(pricesOfBooks.size() > 2)
            return Constants.discount10percent;
        else
            return 0;
    }

    private static List<PriceOfBook> loadPricesOfBooksFromDB(List<String> titlesOfBooks){
        List<PriceOfBook> pricesOfBooks = null;
        Connector.loadDriver();
        Connection connection = Connector.getConnection();
        pricesOfBooks = OrderDAO.getPricesFromDB(titlesOfBooks, connection);
        Connector.closeConnection(connection);
        return pricesOfBooks;
    }

    private static int getSummaryPrice(float discount, List<PriceOfBook> pricesOfBooks){
        int summaryPrice = 0;
        for (var priceOfBook : pricesOfBooks){
            summaryPrice += priceOfBook.getPrice() + priceOfBook.getPricePerDay() * Constants.countOfDaysInMonth;
        }
        summaryPrice *= (1-discount);
        return summaryPrice;
    }

    public static void registerOrderOfReader(String passportID, List<String> titlesOfBooks){
        Connector.loadDriver();
        Connection connection = Connector.getConnection();
        orderRegistration(connection, passportID, titlesOfBooks);
        Connector.closeConnection(connection);
    }

    private static void orderRegistration(Connection connection, String passportID, List<String> titlesOfBooks){
        int readerId = OrderDAO.getReaderId(connection, passportID);
        if(OrderDAO.readerReturnedBooks(connection, readerId)) {
            List<Order> orderListOfReader = OrderDAO.getInstanceIdForAllTitlesOfBook(connection, titlesOfBooks, readerId);
            OrderDAO.loadOrderInDB(connection, orderListOfReader);
            OrderDAO.makeInstancesOfBooksUnavailable(connection, orderListOfReader, readerId);
        } else {
            System.out.println("This reader still didn't return books!");
        }
    }
}
