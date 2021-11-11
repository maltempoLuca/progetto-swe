package com.company.store;

public class CancelDenierException extends Exception {

    public CancelDenierException() {
        super("Impossibile cancellare la spedizione");
    }

    public CancelDenierException(String message) {
        super(message);
    }
}
