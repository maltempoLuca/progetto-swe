package com.company.exceptions;

public class MissingInputException extends Exception {
    public MissingInputException(String inputKey, String requestName) {
        super(ExceptionMessages.KEY_MISSING_INPUT + inputKey + ExceptionMessages.IN_REQUEST_MISSING_INPUT + requestName);
    }
}
