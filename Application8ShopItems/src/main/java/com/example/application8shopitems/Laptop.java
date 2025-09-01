package com.example.application8shopitems;

import java.util.Arrays;
import java.util.List;

public class Laptop extends Item {
    public Laptop(String model, double price) {
        super(model, price);
    }
    public static List<Item> getLaptopList(){
        return Arrays.asList(
            new Laptop("MacBook Pro 16 M3 Max", 3999.99),
            new Laptop("MacBook Air 15 M2 Pro", 1199.99),
            new Laptop("MacBook Air 14 M2", 999.99),
            new Laptop("HP Spectre x360 16", 1799.99),
            new Laptop("HP Omen 16", 1199.99),
            new Laptop("HP Envy x360 15", 899.99),
            new Laptop("HP Pavilion Plus 14", 799.99)
        );
    }
}
