package com.company.store.shipping.strategy.addressbehavior;

import com.company.store.OperationResult;
import com.company.store.shipping.Shipment;

public final class UserAddressDenier implements AddressBehavior {
    //deny changing destination address because it's too late in the delivery process

    private UserAddressDenier() {

    }

    public static UserAddressDenier getInstance() {
        if (instance == null)
            instance = new UserAddressDenier();
        return instance;
    }

    @Override
    public OperationResult changeAddress(Shipment shipment, String userEmail, String newAddress) {
        String message = "Destination address of shipment " + shipment.getId()
                + "cannot be changed as state is " + shipment.getState().getCurrentState();
        return new OperationResult(message, false);
    }

    private static UserAddressDenier instance = null;
}
