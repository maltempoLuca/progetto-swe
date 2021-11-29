package com.company.store;

import com.company.constants.Constants;
import com.company.store.events.shipments.ShipEventIdentifier;
import com.company.store.events.shipments.ShipmentEvent;
import com.company.store.events.shipments.ShipmentEventManager;

public class ReturnAllower implements ReturnBehavior {

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
        return new OperationResult("Shipment: " + shipment.getId() + "return accepted", true);
    }

    private static ReturnAllower instance = null;
}
