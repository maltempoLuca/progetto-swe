package com.company.exceptions;

public class StoreInitializationException extends Exception {
    public StoreInitializationException() {
        super(ExceptionMessages.MISSING_DEPARTMENT);
    }
}
