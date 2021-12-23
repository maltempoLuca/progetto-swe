package com.company.store.shipping.strategy.returnbehavior;
import com.company.store.OperationResult;
import com.company.store.shipping.Shipment;

public interface ReturnBehavior {

    OperationResult createReturn(Shipment shipment, String userEmail);
}
