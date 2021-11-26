package com.company.store;

public class CancelReturn implements CancelBehavior{


    private CancelReturn() {

    }

    public static CancelReturn getInstance() {
        if (instance == null)
            instance = new CancelReturn();
        return instance;
    }

    @Override
    public OperationResult cancelShipment(Shipment shipment, String userEmail) throws UnsupportedOperationException {
        throw new UnsupportedOperationException("Impossibile effettuare cancellazione di un reso");
    }

    private static CancelReturn instance = null;
}
