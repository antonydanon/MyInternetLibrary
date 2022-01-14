package com.ita.u1.internetLibrary.model;

import java.time.LocalDate;
import java.util.List;

public class MessageForReader {
    private String readerName;
    private List<String> titlesOfBooks;
    private int penalty;
    private String email;

    public MessageForReader(String readerName, List<String> titlesOfBooks, int penalty, String email) {
        this.readerName = readerName;
        this.titlesOfBooks = titlesOfBooks;
        this.penalty = penalty;
        this.email = email;
    }

    public String getReaderName() {
        return readerName;
    }

    public void setReaderName(String readerName) {
        this.readerName = readerName;
    }

    public List<String> getTitlesOfBooks() {
        return titlesOfBooks;
    }

    public void setTitlesOfBooks(List<String> titlesOfBooks) {
        this.titlesOfBooks = titlesOfBooks;
    }

    public int getPenalty() {
        return penalty;
    }

    public void setPenalty(int penalty) {
        this.penalty = penalty;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
