package com.company.store.shipping.strategy.returnbehavior;

import com.company.constants.Constants;
import com.company.store.OperationResult;
import com.company.store.shipping.Shipment;

public final class ReturnDenier implements ReturnBehavior {

    private ReturnDenier() {

    }

    public static ReturnDenier getInstance() {
        if (instance == null)
            instance = new ReturnDenier();
        return instance;
    }

    @Override
    public OperationResult createReturn(Shipment shipment, String userEmail) {
        String message = "Cannot return shipment: " + shipment.getId() + " " + Constants.RETURN_REASON;
        return new OperationResult(message, false);
    }

    private static ReturnDenier instance = null;
}
