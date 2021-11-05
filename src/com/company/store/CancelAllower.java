package com.company.store;

public class CancelAllower implements CancelBehavior {

    private CancelAllower() {

    }

    public static CancelAllower getInstance() {
        if (instance == null)
            instance = new CancelAllower();
        return instance;
    }

    @Override
    public boolean cancelShipment(Shipment shipment) {
        shipment = null;
        return true;
    }

    private static CancelAllower instance = null;
}
