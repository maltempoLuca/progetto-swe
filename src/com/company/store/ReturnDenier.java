package com.company.store;

public class ReturnDenier implements ReturnBehavior {

    private ReturnDenier() {

    }

    public static ReturnDenier getInstance() {
        if (instance == null)
            instance = new ReturnDenier();
        return instance;
    }

    @Override
    public boolean createReturn(Shipment shipment) {
        return false;
    }

    private static ReturnDenier instance = null;
}
