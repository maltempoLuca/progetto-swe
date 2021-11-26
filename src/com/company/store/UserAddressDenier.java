package com.company.store;

import com.company.constants.Constants;

public class UserAddressDenier implements AddressBehavior {

    private UserAddressDenier() {

    }

    public static UserAddressDenier getInstance() {
        if (instance == null)
            instance = new UserAddressDenier();
        return instance;
    }

    //TODO: implement method
    @Override
    public OperationResult changeAddress(Shipment shipment, String newAddress) {
        return new OperationResult("Destination address of shipment " + shipment.getId() + "cannot be changed " + Constants.CHANGE_ADDRESS_REASON, false);
    }

    private static UserAddressDenier instance = null;
}
