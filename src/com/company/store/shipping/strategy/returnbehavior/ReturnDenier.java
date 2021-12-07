package com.company.store.shipping.strategy.returnbehavior;

import com.company.constants.Constants;
import com.company.store.events.OperationResult;
import com.company.store.shipping.Shipment;

public class ReturnDenier implements ReturnBehavior {

    private ReturnDenier() {

    }

    public static ReturnDenier getInstance() {
        if (instance == null)
            instance = new ReturnDenier();
        return instance;
    }

    @Override
    public OperationResult createReturn(Shipment shipment, String userEmail) {
        return new OperationResult("Cannot return shipment: " + shipment.getId() + " " + Constants.RETURN_REASON, false);
    }

    private static ReturnDenier instance = null;
}
