package com.company.store;
import com.company.store.Shipment;

public interface AddressBehavior {

    boolean changeAddress(Shipment shipment, String newAddress);
}
