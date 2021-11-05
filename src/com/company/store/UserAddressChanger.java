package com.company.store;
import com.company.strategy.AddressBehavior;

public class UserAddressChanger implements AddressBehavior {

    private UserAddressChanger() {

    }

    public static UserAddressChanger getInstance() {
        if (instance == null)
            instance = new UserAddressChanger();
        return instance;
    }

    @Override
    public void changeAddress(Shipment shipment, String newAddress) {
        shipment.setDestinationAddress(newAddress);
    }

    private static UserAddressChanger instance = null;
}
