package com.company;

import com.company.constants.Constants;
import com.company.store.events.requests.RequestEvent;
import com.company.store.events.requests.RequestIdentifier;
import com.company.store.events.requests.RequestManager;

public class Buttons {

    private Buttons() {
    }

    public static Buttons getInstance() {
        if (instance == null)
            instance = new Buttons();
        return instance;
    }

    public void registerUser(String email, String password) {
        RequestEvent request = new RequestEvent(RequestIdentifier.REGISTER_REQUEST);
        request.addInput(Constants.USER_EMAIL, email).addInput(Constants.USER_PSW, password);
        RequestManager.getInstance().notify(request);
    }

    public void loginUser(String email, String password) {
        RequestEvent request = new RequestEvent(RequestIdentifier.LOGIN_REQUEST);
        request.addInput(Constants.USER_EMAIL, email).addInput(Constants.USER_PSW, password);
        RequestManager.getInstance().notify(request);
    }

    public void logoutUser(String email) {
        RequestEvent request = new RequestEvent(RequestIdentifier.LOGOUT_REQUEST, email);
        RequestManager.getInstance().notify(request);
    }

    private static Buttons instance = null;
}
