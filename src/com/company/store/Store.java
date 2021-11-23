package com.company.store;

import com.company.constants.Constants;
import com.company.store.eventsys.events.EventBuilder;
import com.company.store.eventsys.events.EventIdentifier;
import com.company.store.eventsys.events.StoreEvent;
import com.company.store.eventsys.management.StoreEventManager;

public class Store {

    //TODO: crea store con getInstance()?
    private Store() {

    }

    public static Store getInstance() {
        if (instance == null)
            instance = new Store();
        return instance;
    }

    public void requestCancel() {

    }

    public void requestAddressChange() {

    }

    public void requestReturn() {

    }

    public void requestPurchase() {

    }

    public void registerUser(String email, String password) {
        UserDepartment.getInstance().registerUser(email, password);
    }

    public void loginUser(String email, String password) {
        UserDepartment.getInstance().loginUser(email, password);
    }

    public void logoutUser(String email) {
        UserDepartment.getInstance().logOut(email);
    }

    private static Store instance = null;


}
