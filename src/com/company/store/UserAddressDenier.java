package com.company.store;
import com.company.strategy.AddressBehavior;

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
    public void changeAddress() {

    }

    private static UserAddressDenier instance = null;
}
