package com.ita.u1.internetLibrary.model;

import java.util.List;

public class Book {
    String russianNameOfBook;
    String originalNameOfBook;
    String genre;
    Integer price;
    Integer countOfInstances;
    List<Author> author;
    PhotoOfBook photoOfBook;
    Integer pricePerDay;
    Integer yearOfPublication;
    String dateOfRegistration;
    Integer countOfPage;

    public Book(String russianNameOfBook, String originalNameOfBook, String genre, Integer price, Integer countOfInstances, List<Author> author, PhotoOfBook photoOfBook, Integer pricePerDay, Integer yearOfPublication, String dateOfRegistration, Integer countOfPage) {
        this.russianNameOfBook = russianNameOfBook;
        this.originalNameOfBook = originalNameOfBook;
        this.genre = genre;
        this.price = price;
        this.countOfInstances = countOfInstances;
        this.author = author;
        this.photoOfBook = photoOfBook;
        this.pricePerDay = pricePerDay;
        this.yearOfPublication = yearOfPublication;
        this.dateOfRegistration = dateOfRegistration;
        this.countOfPage = countOfPage;
    }

    public String getRussianNameOfBook() {
        return russianNameOfBook;
    }

    public void setRussianNameOfBook(String russianNameOfBook) {
        this.russianNameOfBook = russianNameOfBook;
    }

    public String getOriginalNameOfBook() {
        return originalNameOfBook;
    }

    public void setOriginalNameOfBook(String originalNameOfBook) {
        this.originalNameOfBook = originalNameOfBook;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getCountOfInstances() {
        return countOfInstances;
    }

    public void setCountOfInstances(Integer countOfInstances) {
        this.countOfInstances = countOfInstances;
    }

    public List<Author> getAuthor() {
        return author;
    }

    public void setAuthor(List<Author> author) {
        this.author = author;
    }

    public PhotoOfBook getPhotoOfBook() {
        return photoOfBook;
    }

    public void setPhotoOfBook(PhotoOfBook photoOfBook) {
        this.photoOfBook = photoOfBook;
    }

    public Integer getPricePerDay() {
        return pricePerDay;
    }

    public void setPricePerDay(Integer pricePerDay) {
        this.pricePerDay = pricePerDay;
    }

    public Integer getYearOfPublication() {
        return yearOfPublication;
    }

    public void setYearOfPublication(Integer yearOfPublication) {
        this.yearOfPublication = yearOfPublication;
    }

    public String getDateOfRegistration() {
        return dateOfRegistration;
    }

    public void setDateOfRegistration(String dateOfRegistration) {
        this.dateOfRegistration = dateOfRegistration;
    }

    public Integer getCountOfPage() {
        return countOfPage;
    }

    public void setCountOfPage(Integer countOfPage) {
        this.countOfPage = countOfPage;
    }
}
