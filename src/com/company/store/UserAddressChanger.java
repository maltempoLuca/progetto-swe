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

    //TODO: implements method
    @Override
    public void changeAddress() {

    }

    private static UserAddressChanger instance = null;
}
