package com.company.store;

import com.company.constants.Constants;

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
        String message = "Shipment: " + shipment.getId() + " could not be canceled " + Constants.CANCEL_REASON;
        return new OperationResult(message, false);
    }

    private static CancelDenier instance = null;
}
