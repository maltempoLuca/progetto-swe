package com.company.exceptions;

public final class MissingAgencyException extends Exception {

    public MissingAgencyException() {
        super(ExceptionMessages.MISSING_AGENCY);
    }
}
