package com.company;

import com.company.constants.Constants;
import com.company.store.UserDepartment;
import com.company.store.eventsys.events.EventBuilder;
import com.company.store.eventsys.events.EventIdentifier;
import com.company.store.eventsys.events.StoreEvent;
import com.company.store.eventsys.management.StoreEventManager;

public class Buttons {

    private Buttons() {
    }

    public static Buttons getInstance() {
        if (instance == null)
            instance = new Buttons();
        return instance;
    }

    public void registerUser(String email, String password) {
        StoreEvent registerEvent = new StoreEvent(EventBuilder.buildStoreEvent()
                .withInfo(Constants.USER_EMAIL, email)
                .withInfo(Constants.USER_PSW, password)
                .withIdentifier(EventIdentifier.REGISTER_REQUEST));
        StoreEventManager.getInstance().notify(registerEvent);
    }

    public void loginUser(String email, String password) {
        StoreEvent loginEvent = new StoreEvent(EventBuilder.buildStoreEvent()
                .withInfo(Constants.USER_EMAIL, email)
                .withInfo(Constants.USER_PSW, password)
                .withIdentifier(EventIdentifier.LOGIN_REQUEST));
        StoreEventManager.getInstance().notify(loginEvent);
    }

    public void logoutUser(String email) {
        StoreEvent logoutEvent = new StoreEvent(EventBuilder.buildStoreEvent()
                .withInfo(Constants.USER_EMAIL, email)
                .withIdentifier(EventIdentifier.LOGOUT_REQUEST));
        StoreEventManager.getInstance().notify(logoutEvent);
    }

    private static Buttons instance = null;
}
