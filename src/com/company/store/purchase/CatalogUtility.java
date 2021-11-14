package com.company.store.purchase;

import java.util.HashMap;

public final class CatalogUtility {
    //TODO: which visibility? how to assign catalog to PurchasingDepartment?
    //holds product info and builds catalog

    private CatalogUtility() {}

    static HashMap<String, Product> buildCatalog() {
        HashMap<String, Product> catalog = new HashMap<>();
        catalog.put(SHOES_ID, new Product(SHOES_ID, SHOES_NAME, SHOES_PRICE));
        catalog.put(LAPTOP_ID, new Product(LAPTOP_ID, LAPTOP_NAME, LAPTOP_PRICE));
        return catalog;
    }

    static final String SHOES_ID = "00001";
    static final String SHOES_NAME = "Paio di scarpe";
    static final int SHOES_PRICE = 120;

    static final String LAPTOP_ID = "02310";
    static final String LAPTOP_NAME = "Laptop";
    static final int LAPTOP_PRICE = 799;


}