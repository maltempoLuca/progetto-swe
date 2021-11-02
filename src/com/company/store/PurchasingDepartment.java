package com.company.store;

public class PurchasingDepartment {

    private PurchasingDepartment() {

    }

    public static PurchasingDepartment getInstance() {
        if (instance == null)
            instance = new PurchasingDepartment();
        return instance;
    }

    private static PurchasingDepartment instance = null;
}
