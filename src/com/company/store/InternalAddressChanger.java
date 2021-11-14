package com.company.store;

public class InternalAddressChanger implements AddressBehavior {

    private InternalAddressChanger() {

    }

    public static InternalAddressChanger getInstance() {
        if (instance == null)
            instance = new InternalAddressChanger();
        return instance;
    }

    @Override
    public boolean changeAddress(Shipment shipment, String newAddress) {
        try {

        } catch (UnsupportedOperationException exception) {}
        return false;
    }

    private static InternalAddressChanger instance = null;
}
