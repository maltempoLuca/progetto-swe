package com.company.store.purchase;

import java.util.HashMap;

public final class CatalogUtility {
    //holds product info and builds catalog
    //ideally buildCatalog() would read data from database or file

    private CatalogUtility() {}

    static HashMap<String, Product> buildCatalog() {
        HashMap<String, Product> catalog = new HashMap<>();
        catalog.put(SHOES_ID, new Product(SHOES_ID, SHOES_NAME, SHOES_PRICE));
        catalog.put(LAPTOP_ID, new Product(LAPTOP_ID, LAPTOP_NAME, LAPTOP_PRICE));
        catalog.put(LAMPADA_ID, new Product(LAMPADA_ID, LAMPADA_NAME, LAMPADA_PRICE));
        catalog.put(CYCLETTE_ID, new Product(CYCLETTE_ID, CYCLETTE_NAME, CYCLETTE_PRICE));
        return catalog;
    }

    static final String SHOES_ID = "00001";
    static final String SHOES_NAME = "Paio di scarpe";
    static final double SHOES_PRICE = 120.99;

    static final String LAPTOP_ID = "02310";
    static final String LAPTOP_NAME = "Laptop";
    static final double LAPTOP_PRICE = 799.00;

    static final String LAMPADA_ID = "01998";
    static final String LAMPADA_NAME = "Lampada";
    static final double LAMPADA_PRICE = 12.57;

    static final String CYCLETTE_ID = "06060";
    static final String CYCLETTE_NAME = "Cyclette";
    static final double CYCLETTE_PRICE = 319.90;
}
