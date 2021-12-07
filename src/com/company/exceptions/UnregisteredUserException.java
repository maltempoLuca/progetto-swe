package com.company.exceptions;

public class UnregisteredUserException extends Exception {
    public UnregisteredUserException(String userEmail) {
        super(userEmail + ": " + ExceptionMessages.UNREG_USER);
    }
}
