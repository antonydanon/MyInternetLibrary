package com.ita.u1.internetLibrary.model;

public class Author {
    String name;
    String surname;
    String patronymic;
    byte[] photoOfAuthor;

    public Author(String name, String surname, String patronymic, byte[] photoOfAuthor) {
        this.name = name;
        this.surname = surname;
        this.patronymic = patronymic;
        this.photoOfAuthor = photoOfAuthor;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    public byte[] getPhotoOfAuthor() {
        return photoOfAuthor;
    }

    public void setPhotoOfAuthor(byte[] photoOfAuthor) {
        this.photoOfAuthor = photoOfAuthor;
    }
}
