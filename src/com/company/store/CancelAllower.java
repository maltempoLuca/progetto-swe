package com.company.store;

import com.company.constants.Constants;
import com.company.store.events.shipments.ShipEventIdentifier;
import com.company.store.events.shipments.ShipmentEvent;
import com.company.store.events.shipments.ShipmentEventManager;

public class CancelAllower implements CancelBehavior {

    private CancelAllower() {

    }

    public static CancelAllower getInstance() {
        if (instance == null)
            instance = new CancelAllower();
        return instance;
    }

    @Override
    public OperationResult cancelShipment(Shipment shipment, String userEmail) {
        String shipmentId = shipment.getId();
        shipment.setState(Constants.CANCELLED);
        ShipmentEventManager.getInstance().notify(new ShipmentEvent(ShipEventIdentifier.CANCELED, new Shipment(shipment), userEmail));

        return new OperationResult("Shipment: " + shipmentId + "successfully canceled", true);
    }

    private static CancelAllower instance = null;
}
