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

    public static String getEmail(int readerId){
        Connector.loadDriver();
        Connection connection = Connector.getConnection();
        String email = OrderDAO.getEmailFromDB(readerId, connection);
        Connector.closeConnection(connection);
        return email;
    }

    public static int getOrderPrice(List<String> titlesOfBooks){
        List<PriceOfBook> pricesOfBooks = loadPricesOfBooksFromDB(titlesOfBooks);
        float discount = getDiscount(pricesOfBooks);
        int orderPrice = getSummaryPrice(discount, pricesOfBooks);
        return orderPrice;
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

    public static void registerOrderOfReader(String email, List<String> titlesOfBooks){
        Connector.loadDriver();
        Connection connection = Connector.getConnection();
        orderRegistration(connection, email, titlesOfBooks);
        Connector.closeConnection(connection);
    }

    public static boolean emailAndTitlesOfBooksIsValid(String email, List<String> titlesOfBooks){
        if(ReaderManagement.emailNotValid(email))
            return false;
        if(titlesOfBooksIsNotValid(titlesOfBooks))
            return false;
        if(databaseNotContainsTitles(titlesOfBooks)){
            return false;
        }
        return true;
    }

    private static boolean databaseNotContainsTitles(List<String> titlesOfBooks){
        Connector.loadDriver();
        Connection connection = Connector.getConnection();
        if(OrderDAO.haveSuchTitlesInDB(connection, titlesOfBooks)){
            Connector.closeConnection(connection);
            return false;
        } else {
            Connector.closeConnection(connection);
            return true;
        }
    }

    private static boolean titlesOfBooksIsNotValid(List<String> titlesOfBooks){
        if(titlesOfBooks.size() == 0)
            return true;
        for (var title : titlesOfBooks) {
            if(!title.matches("[A-Za-z0-9\s]{1,50}"))
                return true;
        }
        return false;
    }

    private static void orderRegistration(Connection connection, String email, List<String> titlesOfBooks){
        int readerId = OrderDAO.getReaderId(connection, email);
        if(OrderDAO.readerReturnedBooks(connection, readerId)) {
            List<Order> orderListOfReader = OrderDAO.getInstanceIdForAllTitlesOfBook(connection, titlesOfBooks, readerId);
            OrderDAO.loadOrderInDB(connection, orderListOfReader);
            OrderDAO.makeInstancesOfBooksUnavailable(connection, orderListOfReader, readerId);
        } else {
            System.out.println("This reader still didn't return books!");
        }
    }
}
