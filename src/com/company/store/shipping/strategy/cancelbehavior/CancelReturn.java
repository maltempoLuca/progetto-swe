package com.company.store.shipping.strategy.cancelbehavior;

import com.company.store.OperationResult;
import com.company.store.shipping.Shipment;

public final class CancelReturn implements CancelBehavior {
    //deny canceling a shipment because it's a return

    private CancelReturn() {

    }

    public static CancelReturn getInstance() {
        if (instance == null)
            instance = new CancelReturn();
        return instance;
    }

    @Override
    public OperationResult cancelShipment(Shipment shipment, String userEmail) {
        String message = "Shipment: " + shipment.getId() + " could not be canceled because it is a Return!";
        return new OperationResult(message, false);
    }

    private static CancelReturn instance = null;
}
