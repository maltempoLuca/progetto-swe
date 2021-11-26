package com.company.store;
import com.company.store.Shipment;

public interface CancelBehavior {

    OperationResult cancelShipment(Shipment shipment, String userEmail);
}
