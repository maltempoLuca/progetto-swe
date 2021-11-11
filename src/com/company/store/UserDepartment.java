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
        if (emailFailed.equals(Constants.SUCCESS))  {
            String pswFailed = checkPasswordValidity(password);
            if (pswFailed.equals(Constants.SUCCESS)) {
                if (usrLoginInfo.containsKey(lowerCaseEmail)) {
                    return Constants.EMAIL_ALREADY_USED;
                } else
                    usrLoginInfo.put(lowerCaseEmail, password);
                return Constants.SUCCESS;
            } else
                return pswFailed;
        } else
            return emailFailed;
    }

    public String loginUser(String email, String password) {
        String lowerCaseEmail = email.toLowerCase();
        if (usrLoginInfo.containsKey(lowerCaseEmail)) {
            if (password.equals(usrLoginInfo.get(lowerCaseEmail))) {
                return Constants.SUCCESS;
            } else
                return Constants.WRONG_PSW;
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

    //TODO: implement logged users
    private final HashMap<String, String> usrLoginInfo = new HashMap<String, String>();
    private static UserDepartment instance = null;
}