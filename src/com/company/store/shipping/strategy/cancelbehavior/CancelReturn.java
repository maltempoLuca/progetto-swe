package com.company.store.shipping.strategy.cancelbehavior;

import com.company.store.events.OperationResult;
import com.company.store.shipping.Shipment;

public class CancelReturn implements CancelBehavior {


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
