package com.company.store;

public class ReturnError implements ReturnBehavior {

    private ReturnError() {

    }

    public static ReturnError getInstance() {
        if (instance == null)
            instance = new ReturnError();
        return instance;
    }

    @Override
    public OperationResult createReturn(Shipment shipment, String userEmail) throws UnsupportedOperationException {
        throw new UnsupportedOperationException("Impossibile effettuare il reso di un reso");
    }

    private static ReturnError instance = null;
}
