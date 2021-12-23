package com.company.store.shipping.strategy.cancelbehavior;

import com.company.store.OperationResult;
import com.company.store.shipping.Shipment;

public class CancelDenier implements CancelBehavior {

    private CancelDenier() {

    }

    public static CancelDenier getInstance() {
        if (instance == null)
            instance = new CancelDenier();
        return instance;
    }

    @Override
    public OperationResult cancelShipment(Shipment shipment, String userEmail) {
        String message = "Shipment: " + shipment.getId() + " could not be canceled as state is "
                + shipment.getState().getCurrentState();
        return new OperationResult(message, false);
    }

    private static CancelDenier instance = null;
}
