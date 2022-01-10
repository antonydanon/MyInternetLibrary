package com.ita.u1.internetLibrary.model;

import java.time.LocalDate;

public class BookRegistration {
    private String russianTitle;
    private String originalTitle;
    private Integer price;
    private Integer pricePerDay;
    private Integer yearOfPublishing;
    private LocalDate dateOfRegistration;
    private Integer countOfPages;
    private Integer countOfInstances;

    public BookRegistration(String russianTitle, String originalTitle, Integer price, Integer pricePerDay, Integer yearOfPublishing, LocalDate dateOfRegistration, Integer countOfPages, Integer countOfInstances) {
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

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getPricePerDay() {
        return pricePerDay;
    }

    public void setPricePerDay(Integer pricePerDay) {
        this.pricePerDay = pricePerDay;
    }

    public Integer getYearOfPublishing() {
        return yearOfPublishing;
    }

    public void setYearOfPublishing(Integer yearOfPublishing) {
        this.yearOfPublishing = yearOfPublishing;
    }

    public LocalDate getDateOfRegistration() {
        return dateOfRegistration;
    }

    public void setDateOfRegistration(LocalDate dateOfRegistration) {
        this.dateOfRegistration = dateOfRegistration;
    }

    public Integer getCountOfPages() {
        return countOfPages;
    }

    public void setCountOfPages(Integer countOfPages) {
        this.countOfPages = countOfPages;
    }

    public Integer getCountOfInstances() {
        return countOfInstances;
    }

    public void setCountOfInstances(Integer countOfInstances) {
        this.countOfInstances = countOfInstances;
    }
}
