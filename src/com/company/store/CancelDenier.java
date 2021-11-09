package com.company.store;

public class CancelDenier implements CancelBehavior {

    private CancelDenier() {

    }

    public static CancelDenier getInstance() {
        if (instance == null)
            instance = new CancelDenier();
        return instance;
    }

    @Override
    public boolean cancelShipment(Shipment shipment) throws CancelDenierException {
        throw new CancelDenierException();
    }

    private static CancelDenier instance = null;
}
