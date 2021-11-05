package com.company.store;

public class CancelDenier implements CancelBehavior {

    private CancelDenier() {

    }

    public static CancelDenier getInstance() {
        if (instance == null)
            instance = new CancelDenier();
        return instance;
    }

    //TODO: implement method
    @Override
    public boolean cancelShipment(Shipment shipment) {
        //TODO: throw exception
        return false;
    }

    private static CancelDenier instance = null;
}
