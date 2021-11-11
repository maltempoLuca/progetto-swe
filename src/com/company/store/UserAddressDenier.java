package com.company.store;

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
    public boolean changeAddress(Shipment shipment, String newAddress) {
        return false;
    }

    private static UserAddressDenier instance = null;
}
