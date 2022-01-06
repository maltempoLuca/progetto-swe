package com.company.exceptions;

public final class StoreInitializationException extends Exception {
    public StoreInitializationException() {
        super(ExceptionMessages.MISSING_DEPARTMENT);
    }
}
