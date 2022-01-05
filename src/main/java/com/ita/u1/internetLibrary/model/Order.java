package com.ita.u1.internetLibrary.model;

public class Order {
    private int instanceId;
    private int readerId;
    private String titleOfBook;

    public Order(int instanceId, int readerId, String titleOfBook) {
        this.instanceId = instanceId;
        this.readerId = readerId;
        this.titleOfBook = titleOfBook;
    }

    public int getInstanceId() {
        return instanceId;
    }

    public void setInstanceId(int instanceId) {
        this.instanceId = instanceId;
    }

    public int getReaderId() {
        return readerId;
    }

    public void setReaderId(int readerId) {
        this.readerId = readerId;
    }

    public String getTitleOfBook() {
        return titleOfBook;
    }

    public void setTitleOfBook(String titleOfBook) {
        this.titleOfBook = titleOfBook;
    }
}
