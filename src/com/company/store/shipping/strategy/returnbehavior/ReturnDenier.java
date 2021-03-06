package com.company.store.shipping.strategy.returnbehavior;

import com.company.constants.Constants;
import com.company.store.OperationResult;
import com.company.store.shipping.Shipment;

public final class ReturnDenier implements ReturnBehavior {
    //deny returning a shipment

    private ReturnDenier() {

    }

    public static ReturnDenier getInstance() {
        if (instance == null)
            instance = new ReturnDenier();
        return instance;
    }

    @Override
    public OperationResult createReturn(Shipment shipment, String userEmail) {
        String message = "Cannot return shipment: " + shipment.getId() + " as state is " + shipment.getState().getCurrentState();
        return new OperationResult(message, false);
    }

    private static ReturnDenier instance = null;
}
