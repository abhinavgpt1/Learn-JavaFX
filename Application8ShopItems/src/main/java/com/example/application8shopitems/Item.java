package com.example.application8shopitems;

public abstract class Item {
    private String model;
    private Double price;

    public Item(String model, double price) {
        this.model = model;
        this.price = price;
    }

    public String getModel() {
        return model;
    }

    public Double getPrice() {
        return price;
    }
}
