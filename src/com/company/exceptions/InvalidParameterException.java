package com.company.exceptions;

public final class InvalidParameterException extends Exception {

    public InvalidParameterException(String errorMessage) {
        super(errorMessage);
    }

    public InvalidParameterException() {
        this(ExceptionMessages.DEFAULT_INVALID_PARAMETER);
    }
}
