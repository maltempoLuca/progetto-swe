package com.company.store.shipping.strategy.addressbehavior;

import com.company.store.OperationResult;
import com.company.store.shipping.Shipment;

public class UserAddressDenier implements AddressBehavior {

    private UserAddressDenier() {

    }

    public static UserAddressDenier getInstance() {
        if (instance == null)
            instance = new UserAddressDenier();
        return instance;
    }

    @Override
    public OperationResult changeAddress(Shipment shipment, String userEmail, String newAddress) {
        return new OperationResult("Destination address of shipment " + shipment.getId() + "cannot be changed as state is " + shipment.getState().getCurrentState(), false);
    }

    private static UserAddressDenier instance = null;
}
