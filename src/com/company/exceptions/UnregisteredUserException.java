package com.company.exceptions;

public final class UnregisteredUserException extends Exception {

    public UnregisteredUserException(String userEmail) {
        super(userEmail + ": " + ExceptionMessages.UNREG_USER);
    }
}
