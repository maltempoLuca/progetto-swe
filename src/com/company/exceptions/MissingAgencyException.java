package com.company.exceptions;

public class MissingAgencyException extends Exception {
    public MissingAgencyException() {
        super(ExceptionMessages.MISSING_AGENCY);

    }
}
