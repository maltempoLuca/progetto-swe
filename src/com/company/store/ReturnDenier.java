package com.company.store;

import com.company.constants.Constants;

public class ReturnDenier implements ReturnBehavior {

    private ReturnDenier() {

    }

    public static ReturnDenier getInstance() {
        if (instance == null)
            instance = new ReturnDenier();
        return instance;
    }

    @Override
    public OperationResult createReturn(Shipment shipment) {

        return new OperationResult("Cannot return shipment: " + shipment.getId() + " " + Constants.RETURN_REASON, false);
    }

    private static ReturnDenier instance = null;
}
