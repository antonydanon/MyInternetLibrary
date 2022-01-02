package com.ita.u1.internetLibrary.service;

import com.ita.u1.internetLibrary.Constants;
import com.ita.u1.internetLibrary.dao.Connector;
import com.ita.u1.internetLibrary.dao.OrderDAO;
import com.ita.u1.internetLibrary.model.Order;
import com.ita.u1.internetLibrary.model.PriceOfBook;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class OrderManagement {
    public static LocalDate getReturnDate(){
        LocalDate bookReturnDate = LocalDate.now();
        bookReturnDate = bookReturnDate.plusMonths(1);
        return bookReturnDate;
    }

    public static int getOrderPrice(HttpServletRequest request){
        List<String> titlesOfBooks = getListOfOrders(request);
        validationOfOrders(titlesOfBooks);
        List<PriceOfBook> pricesOfBooks = loadPricesOfBooksFromDB(titlesOfBooks);
        float discount = getDiscount(pricesOfBooks);
        int orderPrice = getSummaryPrice(discount, pricesOfBooks);
        return orderPrice;
    }

    private static List<String> getListOfOrders(HttpServletRequest request){
        List<String> titlesOfBooks = new ArrayList<>();
        titlesOfBooks.add(request.getParameter("firstBook"));
        titlesOfBooks.add(request.getParameter("secondBook"));
        titlesOfBooks.add(request.getParameter("thirdBook"));
        titlesOfBooks.add(request.getParameter("fourthBook"));
        titlesOfBooks.add(request.getParameter("fifthBook"));
        return titlesOfBooks;
    }

    private static List<String> validationOfOrders(List<String> titlesOfBooks){
        //TODO: make a validation
        return titlesOfBooks;
    }

    private static float getDiscount(List<PriceOfBook> pricesOfBooks){
        if(pricesOfBooks.size() > 4)
            return Constants.discount15percent;
        if(pricesOfBooks.size() > 2)
            return Constants.discount10percent;
        else
            return 0;
    }

    private static List<PriceOfBook> loadPricesOfBooksFromDB(List<String> titlesOfBooks){
        List<PriceOfBook> pricesOfBooks = null;
        try {
            Connector.loadDriver();
            Connection connection = Connector.getConnection();
            pricesOfBooks = OrderDAO.getPricesFromDB(titlesOfBooks, connection);
            Connector.closeConnection(connection);
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
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

    public static void registerOrderOfReader(String passportID, HttpServletRequest request){
        List<String> titlesOfBooks = getListOfOrders(request);
        try {
            Connector.loadDriver();
            Connection connection = Connector.getConnection();
            orderRegistration(connection, passportID, titlesOfBooks);
            Connector.closeConnection(connection);
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }

    private static void orderRegistration(Connection connection, String passportID, List<String> titlesOfBooks){
        int readerId = OrderDAO.getReaderId(connection, passportID);
        if(OrderDAO.readerReturnedBooks(connection, readerId)) {
            List<Order> orderListOfReader = OrderDAO.getInstanceIdForAllTitlesOfBook(connection, titlesOfBooks, readerId);
            OrderDAO.loadOrderInDB(connection, orderListOfReader);
            OrderDAO.makeInstancesOfBooksUnavailable(connection, orderListOfReader);
        } else {
            System.out.println("This reader still didn't return books!");
        }
    }
}
