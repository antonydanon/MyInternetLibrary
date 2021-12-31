package com.ita.u1.internetLibrary.model;

import java.util.List;

public class Book {
    private String russianNameOfBook;
    private List<String> genres;
    private Integer countOfInstances;
    private Integer yearOfPublication;
    private Integer countOfInstancesAvailable;
    private Integer id;

    public Book(String russianNameOfBook, List<String> genres, Integer countOfInstances, Integer yearOfPublication, Integer countOfInstancesAvailable, Integer id) {
        this.russianNameOfBook = russianNameOfBook;
        this.genres = genres;
        this.countOfInstances = countOfInstances;
        this.yearOfPublication = yearOfPublication;
        this.countOfInstancesAvailable = countOfInstancesAvailable;
        this.id = id;
    }

    public String getRussianNameOfBook() {
        return russianNameOfBook;
    }

    public void setRussianNameOfBook(String russianNameOfBook) {
        this.russianNameOfBook = russianNameOfBook;
    }

    public List<String> getGenres() {
        return genres;
    }

    public void setGenres(List<String> genres) {
        this.genres = genres;
    }

    public Integer getCountOfInstances() {
        return countOfInstances;
    }

    public void setCountOfInstances(Integer countOfInstances) {
        this.countOfInstances = countOfInstances;
    }

    public Integer getYearOfPublication() {
        return yearOfPublication;
    }

    public void setYearOfPublication(Integer yearOfPublication) {
        this.yearOfPublication = yearOfPublication;
    }

    public Integer getCountOfInstancesAvailable() {
        return countOfInstancesAvailable;
    }

    public void setCountOfInstancesAvailable(Integer countOfInstancesAvailable) {
        this.countOfInstancesAvailable = countOfInstancesAvailable;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "russianNameOfBook='" + russianNameOfBook + '\'' +
                ", genres=" + genres +
                ", countOfInstances=" + countOfInstances +
                ", yearOfPublication=" + yearOfPublication +
                ", countOfInstancesAvailable=" + countOfInstancesAvailable +
                ", id=" + id + "\n";
    }
}
