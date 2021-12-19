package com.ita.u1.internetLibrary.model;

public class Reader {
    private String surname;
    private String name;
    private String patronymic;
    private String passport_id;
    private String email;
    private String address;
    private String birthday;

    public Reader(String surname, String name, String patronymic, String passport_id, String email, String address, String birthday) {
        this.surname = surname;
        this.name = name;
        this.patronymic = patronymic;
        this.passport_id = passport_id;
        this.email = email;
        this.address = address;
        this.birthday = birthday;
    }

    public String getSurname() {
        return surname;
    }

    public String getName() {
        return name;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public String getPassport_id() {
        return passport_id;
    }

    public String getEmail() {
        return email;
    }

    public String getAddress() {
        return address;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    public void setPassport_id(String passport_id) {
        this.passport_id = passport_id;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }
}
