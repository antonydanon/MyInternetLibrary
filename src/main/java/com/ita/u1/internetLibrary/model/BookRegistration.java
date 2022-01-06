package com.ita.u1.internetLibrary.model;

import java.time.LocalDate;

public class BookRegistration {
    private String russianTitle;
    private String originalTitle;
    private int price;
    private int pricePerDay;
    private int yearOfPublishing;
    private LocalDate dateOfRegistration;
    private int countOfPages;
    private int countOfInstances;

    public BookRegistration(String russianTitle, String originalTitle, int price, int pricePerDay, int yearOfPublishing, LocalDate dateOfRegistration, int countOfPages, int countOfInstances) {
        this.russianTitle = russianTitle;
        this.originalTitle = originalTitle;
        this.price = price;
        this.pricePerDay = pricePerDay;
        this.yearOfPublishing = yearOfPublishing;
        this.dateOfRegistration = dateOfRegistration;
        this.countOfPages = countOfPages;
        this.countOfInstances = countOfInstances;
    }

    public String getRussianTitle() {
        return russianTitle;
    }

    public void setRussianTitle(String russianTitle) {
        this.russianTitle = russianTitle;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public void setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getPricePerDay() {
        return pricePerDay;
    }

    public void setPricePerDay(int pricePerDay) {
        this.pricePerDay = pricePerDay;
    }

    public int getYearOfPublishing() {
        return yearOfPublishing;
    }

    public void setYearOfPublishing(int yearOfPublishing) {
        this.yearOfPublishing = yearOfPublishing;
    }

    public LocalDate getDateOfRegistration() {
        return dateOfRegistration;
    }

    public void setDateOfRegistration(LocalDate dateOfRegistration) {
        this.dateOfRegistration = dateOfRegistration;
    }

    public int getCountOfPages() {
        return countOfPages;
    }

    public void setCountOfPages(int countOfPages) {
        this.countOfPages = countOfPages;
    }

    public int getCountOfInstances() {
        return countOfInstances;
    }

    public void setCountOfInstances(int countOfInstances) {
        this.countOfInstances = countOfInstances;
    }
}
