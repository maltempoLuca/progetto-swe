package com.company.store;

import com.company.constants.Constants;

public class CancelReturn implements CancelBehavior{


    private CancelReturn() {

    }

    public static CancelReturn getInstance() {
        if (instance == null)
            instance = new CancelReturn();
        return instance;
    }

    @Override
    public OperationResult cancelShipment(Shipment shipment) throws UnsupportedOperationException {

        String message = "Shipment: " + shipment.getId() + " could not be canceled because it is a Return!";
        return new OperationResult(message, false);

        //throw new UnsupportedOperationException("Impossibile effettuare cancellazione di un reso");
    }

    private static CancelReturn instance = null;
}
