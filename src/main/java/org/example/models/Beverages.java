package org.example.models;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Beverages {
    private int id;
    private String name;
    private int price;
    private int qty;

    public Beverages(int id, String name, int price, int qty) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.qty = qty;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public int getQty() {
        return qty;
    }

    public StringProperty nameProperty() {
        StringProperty name = new SimpleStringProperty(this.name);
        return name;
    }

    public IntegerProperty priceProperty() {
        IntegerProperty price = new SimpleIntegerProperty(this.price);
        return price;
    }

    public IntegerProperty qtyProperty() {
        IntegerProperty qty = new SimpleIntegerProperty(this.qty);
        return qty;
    }

}