package com.company.store;

public class InternalAddressChanger implements AddressBehavior {

    private InternalAddressChanger() {

    }

    public static InternalAddressChanger getInstance() {
        if (instance == null)
            instance = new InternalAddressChanger();
        return instance;
    }

    @Override
    public void changeAddress(Shipment shipment, String newAddress) throws ChangeAddressException {
        //throw new ChangeAddressException("Operazione impossibile da parte dell'utente");
        throw new UnsupportedOperationException();
    }

    private static InternalAddressChanger instance = null;
}
