package com.company.store;

import com.company.constants.Constants;

public class InternalAddressChanger implements AddressBehavior {

    private InternalAddressChanger() {

    }

    public static InternalAddressChanger getInstance() {
        if (instance == null)
            instance = new InternalAddressChanger();
        return instance;
    }

    @Override
    public OperationResult changeAddress(Shipment shipment, String newAddress) {
        return new OperationResult("Destination of shipment: " + shipment.getId() + "could not be changed " + Constants.INTERNAL_ADDRESS_REASON, false);
    }

    private static InternalAddressChanger instance = null;
}
