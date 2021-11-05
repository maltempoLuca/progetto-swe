package com.company.store;

public class ReturnDenier implements ReturnBehavior {

    private ReturnDenier() {

    }

    public static ReturnDenier getInstance() {
        if (instance == null)
            instance = new ReturnDenier();
        return instance;
    }

    //TODO: implement method
    @Override
    public boolean createReturn(Shipment shipment) {
        //TODO: throw exception
        return false;
    }

    private static ReturnDenier instance = null;
}
