package com.company.store.shipping.strategy.returnbehavior;

import com.company.store.OperationResult;
import com.company.store.shipping.Shipment;
import com.company.store.events.shipmentevents.ShipEventIdentifier;
import com.company.store.events.shipmentevents.ShipmentEvent;
import com.company.store.events.shipmentevents.ShipmentEventManager;

public final class ReturnAllower implements ReturnBehavior {
    //allows to return a shipment

    private ReturnAllower() {

    }

    public static ReturnAllower getInstance() {
        if (instance == null)
            instance = new ReturnAllower();
        return instance;
    }

    @Override
    public OperationResult createReturn(Shipment shipment, String userEmail) {
        ShipmentEventManager.getInstance().notify(new ShipmentEvent(ShipEventIdentifier.RETURNED, new Shipment(shipment), userEmail));

        String message = "Shipment: " + shipment.getId() + " return accepted";
        return new OperationResult(message, true);
    }

    private static ReturnAllower instance = null;
}
