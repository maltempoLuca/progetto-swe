package com.company.store;

public class ChangeAddressException extends Exception{

    public ChangeAddressException() {
        super("Impossibile cambiare indirizzo");
    }

    public ChangeAddressException(String message) {
        super(message);
    }
}
