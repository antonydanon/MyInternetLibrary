package com.ita.u1.internetLibrary.model;

import java.time.LocalDate;

public class Reader {
    private String surname;
    private String name;
    private String patronymic;
    private String passport_id;
    private String email;
    private String address;
    private LocalDate birthday;
    private int id;

    public Reader(String surname, String name, String patronymic, String passport_id, String email, String address, LocalDate birthday, int id) {
        this.surname = surname;
        this.name = name;
        this.patronymic = patronymic;
        this.passport_id = passport_id;
        this.email = email;
        this.address = address;
        this.birthday = birthday;
        this.id = id;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    public String getPassport_id() {
        return passport_id;
    }

    public void setPassport_id(String passport_id) {
        this.passport_id = passport_id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
