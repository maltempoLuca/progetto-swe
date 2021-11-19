package com.company.store;

import com.company.constants.Constants;
import com.company.store.eventsys.events.EventBuilder;
import com.company.store.eventsys.events.EventIdentifier;
import com.company.store.eventsys.events.StoreEvent;
import com.company.store.eventsys.management.StoreEventManager;

import java.util.HashMap;

public class UserDepartment {
    private UserDepartment() {

    }

    public static UserDepartment getInstance() {
        if (instance == null)
            instance = new UserDepartment();
        return instance;
    }

    public String registerUser(String email, String password) {
        String lowerCaseEmail = email.toLowerCase();
        String emailFailed = checkEmailValidity(lowerCaseEmail);
        if (emailFailed.equals(Constants.SUCCESS)) {
            String pswFailed = checkPasswordValidity(password);
            if (pswFailed.equals(Constants.SUCCESS)) {
                if (usrLoginInfo.containsKey(lowerCaseEmail)) {
                    StoreEvent registerEvent = new StoreEvent(EventBuilder.buildStoreEvent()
                            .withInfo(Constants.REGISTRATION_RESULT, Constants.EMAIL_ALREADY_USED)
                            .withIdentifier(EventIdentifier.REGISTRATION_REFUSED));
                    StoreEventManager.getInstance().notify(registerEvent);
                    return Constants.EMAIL_ALREADY_USED;
                } else {
                    usrLoginInfo.put(lowerCaseEmail, new UserData(password));
                    StoreEvent registerEvent = new StoreEvent(EventBuilder.buildStoreEvent()
                            .withInfo(Constants.REGISTRATION_RESULT, Constants.REGISTRATION_SUCCESS)
                            .withIdentifier(EventIdentifier.REGISTRATION_ACCEPTED));
                    StoreEventManager.getInstance().notify(registerEvent);
                    return Constants.SUCCESS;
                }
            } else {
                StoreEvent registerEvent = new StoreEvent(EventBuilder.buildStoreEvent()
                        .withInfo(Constants.REGISTRATION_RESULT, pswFailed)
                        .withIdentifier(EventIdentifier.REGISTRATION_REFUSED));
                StoreEventManager.getInstance().notify(registerEvent);
                return pswFailed;
            }
        } else {
            StoreEvent registerEvent = new StoreEvent(EventBuilder.buildStoreEvent()
                    .withInfo(Constants.REGISTRATION_RESULT, emailFailed)
                    .withIdentifier(EventIdentifier.REGISTRATION_REFUSED));
            StoreEventManager.getInstance().notify(registerEvent);
            return emailFailed;
        }
    }

    public String loginUser(String email, String password) {
        String lowerCaseEmail = email.toLowerCase();
        if (usrLoginInfo.containsKey(lowerCaseEmail)) {
            if (password.equals(usrLoginInfo.get(lowerCaseEmail).password)) {
                usrLoginInfo.get(lowerCaseEmail).userIsLogged = true;
                StoreEvent loginEvent = new StoreEvent(EventBuilder.buildStoreEvent()
                        .withInfo(Constants.LOGIN_RESULT, Constants.LOGIN_SUCCESS)
                        .withIdentifier(EventIdentifier.LOGIN_ACCEPTED));
                StoreEventManager.getInstance().notify(loginEvent);
                return Constants.SUCCESS;
            } else {
                StoreEvent loginEvent = new StoreEvent(EventBuilder.buildStoreEvent()
                        .withInfo(Constants.LOGIN_RESULT, Constants.WRONG_PSW)
                        .withIdentifier(EventIdentifier.LOGIN_REFUSED));
                StoreEventManager.getInstance().notify(loginEvent);
                return Constants.WRONG_PSW;
            }
        } else {
            StoreEvent loginEvent = new StoreEvent(EventBuilder.buildStoreEvent()
                    .withInfo(Constants.LOGIN_RESULT, Constants.WRONG_EMAIL)
                    .withIdentifier(EventIdentifier.LOGIN_REFUSED));
            StoreEventManager.getInstance().notify(loginEvent);
            return Constants.WRONG_EMAIL;
        }
    }

    public String logOut(String email) {
        String lowerCaseEmail = email.toLowerCase();
        if (usrLoginInfo.containsKey(lowerCaseEmail)) {
            if (usrLoginInfo.get(lowerCaseEmail).userIsLogged) {
                usrLoginInfo.get(lowerCaseEmail).userIsLogged = false;
                StoreEvent logoutEvent = new StoreEvent(EventBuilder.buildStoreEvent()
                        .withInfo(Constants.LOGOUT_RESULT, Constants.LOGOUT_SUCCESS)
                        .withIdentifier(EventIdentifier.LOGOUT_ACCEPTED));
                StoreEventManager.getInstance().notify(logoutEvent);
                return Constants.SUCCESS;
            } else {
                StoreEvent logoutEvent = new StoreEvent(EventBuilder.buildStoreEvent()
                        .withInfo(Constants.LOGOUT_RESULT, Constants.ALREADY_LOGGED_OUT)
                        .withIdentifier(EventIdentifier.LOGOUT_REFUSED));
                StoreEventManager.getInstance().notify(logoutEvent);
                return Constants.ALREADY_LOGGED_OUT;
            }
        } else {
            StoreEvent logoutEvent = new StoreEvent(EventBuilder.buildStoreEvent()
                    .withInfo(Constants.LOGOUT_RESULT, Constants.WRONG_EMAIL)
                    .withIdentifier(EventIdentifier.LOGOUT_REFUSED));
            StoreEventManager.getInstance().notify(logoutEvent);
            return Constants.WRONG_EMAIL;
        }
    }

    private String checkPasswordValidity(String password) {
        String numbers = ".*[0-9].*";
        String letters = ".*[a-zA-Z].*";
        if (password.length() >= 6) {
            if (password.matches(numbers)) {
                if (password.matches(letters)) {
                    return Constants.SUCCESS;
                } else
                    return Constants.ONLY_NUMBERS_PSW;
            } else
                return Constants.ONLY_LETTERS_PSW;
        } else
            return Constants.SHORT_PSW;
    }

    private String checkEmailValidity(String email) {
        String atSymbol = ".*[@].*";
        if (email.matches(atSymbol))
            return Constants.SUCCESS;
        return Constants.INVALID_EMAIL;
    }

    private static class UserData {  //static -> dentro DataPair non hai accesso agli attributi di userDepartment.
        private UserData(String password) {
            this.password = password;
            this.userIsLogged = false;
        }

        private Boolean userIsLogged;
        private final String password;
    }

    private final HashMap<String, UserData> usrLoginInfo = new HashMap<>();
    private static UserDepartment instance = null;
}