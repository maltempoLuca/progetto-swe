package com.company.store.shipping.strategy.cancelbehavior;
import com.company.store.events.OperationResult;
import com.company.store.shipping.Shipment;

public interface CancelBehavior {

    OperationResult cancelShipment(Shipment shipment, String userEmail);
}
