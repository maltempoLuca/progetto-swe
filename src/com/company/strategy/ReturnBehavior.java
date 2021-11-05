package com.company.strategy;
import com.company.store.Shipment;

public interface ReturnBehavior {

    boolean createReturn(Shipment shipment);
}
