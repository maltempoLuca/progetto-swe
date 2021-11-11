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
    public boolean createReturn(Shipment shipment) throws ReturnException {
        throw new ReturnException();
    }

    private static ReturnDenier instance = null;
}
