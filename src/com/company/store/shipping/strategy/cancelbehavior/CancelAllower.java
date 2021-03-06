package com.company.store.shipping.strategy.cancelbehavior;

import com.company.constants.Constants;
import com.company.store.OperationResult;
import com.company.store.shipping.Shipment;
import com.company.store.events.shipmentevents.ShipEventIdentifier;
import com.company.store.events.shipmentevents.ShipmentEvent;
import com.company.store.events.shipmentevents.ShipmentEventManager;

public final class CancelAllower implements CancelBehavior {
    //allow to cancel a shipment

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

        String message = "Shipment: " + shipmentId + " successfully canceled";
        return new OperationResult(message, true);
    }

    private static CancelAllower instance = null;
}
