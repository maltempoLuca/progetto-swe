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
    public void changeAddress(Shipment shipment, String newAddress) throws ChangeAddressException {
        throw new ChangeAddressException();
    }

    private static UserAddressDenier instance = null;
}
