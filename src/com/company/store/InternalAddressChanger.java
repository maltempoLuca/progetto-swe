package com.company.store;
import com.company.strategy.AddressBehavior;

public class InternalAddressChanger implements AddressBehavior {

    private InternalAddressChanger() {

    }

    public static InternalAddressChanger getInstance() {
        if (instance == null)
            instance = new InternalAddressChanger();
        return instance;
    }

    //TODO: implement method
    @Override
    public void changeAddress(Shipment shipment, String newAddress) {
        //TODO: throw exception
    }

    private static InternalAddressChanger instance = null;
}
