package com.company.store;

public class StandardServiceException extends Exception {

    public StandardServiceException() {
        super("Il servizio di spedizione scelto non permette di effettuare ");
    }

    public StandardServiceException(String message) {
        super(message);
    }
}
