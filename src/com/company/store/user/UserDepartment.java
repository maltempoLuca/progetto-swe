package com.company.store.user;

import com.company.constants.Constants;
import com.company.store.events.OperationResult;

import java.util.HashMap;
import java.util.concurrent.Semaphore;

public final class UserDepartment {
    private UserDepartment() {

    }

    public static UserDepartment getInstance() {
        if (instance == null)
            instance = new UserDepartment();
        return instance;
    }

    public static void clearInstance() {
        instance = null;
    }

    public OperationResult registerUser(String email, String password) {
        String lowerCaseEmail = email.toLowerCase();
        String emailFailed = checkEmailValidity(lowerCaseEmail);
        String message;
        boolean successful = false;
        if (emailFailed.equals(Constants.SUCCESS)) {
            String pswFailed = checkPasswordValidity(password);
            if (pswFailed.equals(Constants.SUCCESS)) {
                if (usrLoginInfo.containsKey(lowerCaseEmail)) {
                    message = Constants.EMAIL_ALREADY_USED;
                } else {
                    usrLoginInfo.put(lowerCaseEmail, new UserData(password));
                    successful = true;
                    message = Constants.REGISTRATION_SUCCESS;
                }
            } else {
                message = pswFailed;
            }
        } else {
            message = emailFailed;
        }

        return new OperationResult(message, successful);
    }

    public OperationResult loginUser(String email, String password) {
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


    private static class UserData {  //static -> dentro DataPair non hai accesso agli attributi di userDepartment.
        private UserData(String password) {
            this.password = password;
            this.userIsLogged = false;
        }

        private Boolean userIsLogged;
        private final String password;
    }

    private Semaphore usrLoginInfoMutex = new Semaphore(1);
    private final HashMap<String, UserData> usrLoginInfo = new HashMap<>();
    private static UserDepartment instance = null;

    //TODO:: servono i semafori???
   /*
    -controls concurrent access to a shared resource.
    -access to the resource will be requested from multiple, disparate parts of the system.
    -there can be only one object.
    */
}