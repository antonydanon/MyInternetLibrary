package com.ita.u1.internetLibrary.model;

public class PriceOfBook {
    private Integer price;
    private Integer pricePerDay;

    public PriceOfBook(Integer price, Integer pricePerDay) {
        this.price = price;
        this.pricePerDay = pricePerDay;
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
}
