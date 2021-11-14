package com.company.store.purchase;

public final class Product {

    Product(String id, String name, int price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    Product(Product toCopy) {
        //all fields are final right now, still commit to a copy constructor

        this.id = toCopy.id;
        this.name = toCopy.name;
        this.price = toCopy.price;
    }

    String getId() {
        return id;
    }

    String getName() {
        return name;
    }

    int getPrice() {
        return price;
    }

    private final String id;
    private final String name;
    private final int price;
}
