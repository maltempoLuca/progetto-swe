package com.company.store;
import com.company.store.Shipment;

public interface CancelBehavior {

    boolean cancelShipment(Shipment shipment) throws CancelDenierException;
}
