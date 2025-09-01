package com.example.application8shopitems;

import java.util.Arrays;
import java.util.List;

public class Phone extends Item {
    public Phone(String model, double price) {
        super(model, price);
    }
    public static List<Item> getPhoneList(){
        return Arrays.asList(
            new Phone("iPhone 15 Pro Max", 1199.99),
            new Phone("iPhone 15", 799.99),
            new Phone("iPhone 14 Plus", 499.99),
            new Phone("Galaxy S23 FE", 699.99),
            new Phone("Galaxy A54 5G", 399.99),
            new Phone("Galaxy A34 5G", 299.99)
        );
    }
}
