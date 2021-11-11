package com.company.store;

public class UserAddressChanger implements AddressBehavior {

    private UserAddressChanger() {

    }

    public static UserAddressChanger getInstance() {
        if (instance == null)
            instance = new UserAddressChanger();
        return instance;
    }

    @Override
    public boolean changeAddress(Shipment shipment, String newAddress) {
        shipment.setDestinationAddress(newAddress);
        return true;
    }

    private static UserAddressChanger instance = null;
}
