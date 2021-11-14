package com.company.store;

import com.company.constants.Constants;

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
                    return Constants.EMAIL_ALREADY_USED;
                } else
                    usrLoginInfo.put(lowerCaseEmail, new UserData(password));
                return Constants.SUCCESS;
            } else
                return pswFailed;
        } else
            return emailFailed;
    }

    public String loginUser(String email, String password) {
        String lowerCaseEmail = email.toLowerCase();
        if (usrLoginInfo.containsKey(lowerCaseEmail)) {
            if (password.equals(usrLoginInfo.get(lowerCaseEmail).password)) {
                usrLoginInfo.get(lowerCaseEmail).userIsLogged = true;
                return Constants.SUCCESS;
            } else
                return Constants.WRONG_PSW;
        } else
            return Constants.WRONG_EMAIL;
    }

    public String logOut(String email) {
        String lowerCaseEmail = email.toLowerCase();
        if (usrLoginInfo.containsKey(lowerCaseEmail)) {
            if (usrLoginInfo.get(lowerCaseEmail).userIsLogged) {
                usrLoginInfo.get(lowerCaseEmail).userIsLogged = false;
                return Constants.SUCCESS;
            } else
                return Constants.ALREADY_LOGGED_OUT;
        } else
            return Constants.WRONG_EMAIL;
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