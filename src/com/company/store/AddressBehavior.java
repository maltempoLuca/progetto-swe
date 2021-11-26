package com.company.store;
import com.company.store.Shipment;

public interface AddressBehavior {

    OperationResult changeAddress(Shipment shipment, String userEmail, String newAddress);
}
