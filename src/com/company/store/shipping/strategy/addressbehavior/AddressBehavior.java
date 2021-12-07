package com.company.store.shipping.strategy.addressbehavior;
import com.company.store.events.OperationResult;
import com.company.store.shipping.Shipment;

public interface AddressBehavior {

    OperationResult changeAddress(Shipment shipment, String userEmail, String newAddress);
}
