package com.company.store.user;

import com.company.constants.Constants;
import com.company.store.OperationResult;

import java.util.HashMap;
import java.util.concurrent.Semaphore;

public final class UserDepartment {

    public UserDepartment() {

    }

    public OperationResult registerUser(String email, String password) {
        //validate email and psw, if valid check if email doesn't already exist
        //if it doesn't create instance of UserData to store password and login state
        //insert new UserData instance in map of users

        String lowerCaseEmail = email.toLowerCase();
        String emailFormatMsg = checkEmailValidity(lowerCaseEmail);
        String message;
        boolean successful = false;
        if (emailFormatMsg.equals(Constants.SUCCESS)) {
            String pswFormatMsg = checkPasswordValidity(password);
            if (pswFormatMsg.equals(Constants.SUCCESS)) {
                if (usrLoginInfo.containsKey(lowerCaseEmail)) {
                    message = Constants.EMAIL_ALREADY_USED;
                } else {
                    usrLoginInfo.put(lowerCaseEmail, new UserData(password));
                    successful = true;
                    message = Constants.REGISTRATION_SUCCESS;
                }
            } else {
                message = pswFormatMsg;
            }
        } else {
            message = emailFormatMsg;
        }

        return new OperationResult(message, successful);
    }

    public OperationResult loginUser(String email, String password) {
        //check user exists then recover associated UserData instance and set userIsLogged to true

        String lowerCaseEmail = email.toLowerCase();
        String message;
        boolean successful = false;
        if (usrLoginInfo.containsKey(lowerCaseEmail)) {
            if (password.equals(usrLoginInfo.get(lowerCaseEmail).password)) {
                usrLoginInfo.get(lowerCaseEmail).userIsLogged = true;
                successful = true;
                message = Constants.LOGIN_SUCCESS;
            } else {
                message = Constants.WRONG_PSW;
            }
        } else {
            message = Constants.WRONG_EMAIL;
        }
        return new OperationResult(message, successful);
    }

    public OperationResult logOut(String email) {
        //check if user exists and is not logged out, then log out the user

        String lowerCaseEmail = email.toLowerCase();
        String message;
        boolean successful = false;
        if (usrLoginInfo.containsKey(lowerCaseEmail)) {
            if (usrLoginInfo.get(lowerCaseEmail).userIsLogged) {
                usrLoginInfo.get(lowerCaseEmail).userIsLogged = false;
                successful = true;
                message = Constants.LOGOUT_SUCCESS;
            } else {
                message = Constants.ALREADY_LOGGED_OUT;
            }
        } else {
            message = Constants.WRONG_EMAIL;
        }

        return new OperationResult(message, successful);
    }

    public boolean isLogged(String email) {
        boolean logged;
        String lowerCaseEmail = email.toLowerCase();

        if(!usrLoginInfo.containsKey(lowerCaseEmail))
            logged = false;
        else
            logged = usrLoginInfo.get(lowerCaseEmail).userIsLogged;

        return logged;
    }

    private String checkPasswordValidity(String password) {
        String numbers = ".*[0-9].*";
        String letters = ".*[a-zA-Z].*";
        String result;
        if (password.length() >= 6) {
            if (password.matches(numbers)) {
                if (password.matches(letters)) {
                    result =  Constants.SUCCESS;
                } else
                    result = Constants.ONLY_NUMBERS_PSW;
            } else
                result = Constants.ONLY_LETTERS_PSW;
        } else
            result = Constants.SHORT_PSW;
        return  result;
    }

    private String checkEmailValidity(String email) {
        String atSymbol = ".*[@].*";
        String result;
        if (email.matches(atSymbol))
            result =  Constants.SUCCESS;
        else
            result =  Constants.INVALID_EMAIL;
        return result;
    }


    private static class UserData {
        private UserData(String password) {
            this.password = password;
            this.userIsLogged = false;
        }

        private boolean userIsLogged;
        private final String password;
    }


    private final HashMap<String, UserData> usrLoginInfo = new HashMap<>();
}