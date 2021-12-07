package com.company.store.shipping.strategy.addressbehavior;

import com.company.constants.Constants;
import com.company.store.events.OperationResult;
import com.company.store.shipping.Shipment;

public class InternalAddressChanger implements AddressBehavior {

    private InternalAddressChanger() {

    }

    public static InternalAddressChanger getInstance() {
        if (instance == null)
            instance = new InternalAddressChanger();
        return instance;
    }

    @Override
    public OperationResult changeAddress(Shipment shipment, String userEmail, String newAddress) {
        return new OperationResult("Destination address of shipment: " + shipment.getId() + "could not be changed " + Constants.INTERNAL_ADDRESS_REASON, false);
    }

    private static InternalAddressChanger instance = null;
}
