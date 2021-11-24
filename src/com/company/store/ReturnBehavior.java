package com.company.store;
import com.company.store.Shipment;

public interface ReturnBehavior {

    OperationResult createReturn(Shipment shipment);
}
