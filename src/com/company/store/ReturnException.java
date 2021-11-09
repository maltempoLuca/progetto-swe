package com.company.store;

public class ReturnException extends Exception{

    public ReturnException() {
        super("Impossibile creare il reso");
    }

    public ReturnException(String message) {
        super(message);
    }
}
