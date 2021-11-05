package com.company.strategy;
import com.company.store.Shipment;

public interface CancelBehavior {

    boolean cancelShipment(Shipment shipment);
}
