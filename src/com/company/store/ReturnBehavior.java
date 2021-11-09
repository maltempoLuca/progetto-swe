package com.company.store;
import com.company.store.Shipment;

public interface ReturnBehavior {

    boolean createReturn(Shipment shipment) throws ReturnException;
}
