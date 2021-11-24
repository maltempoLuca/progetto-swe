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
    public OperationResult createReturn(Shipment shipment) {

        //TODO: ora queste operazioni le fa shipping department?

        String tempSender = shipment.getSender();
        shipment.setSender(shipment.getReceiver());
        shipment.setReceiver(tempSender);

        String tempSenderAddress = shipment.getSenderAddress();
        shipment.setSenderAddress(shipment.getDestinationAddress());
        shipment.setDestinationAddress(tempSenderAddress);

        shipment.setState(Constants.RETURN_CREATED);

        ShipmentEventManager.getInstance().notify(new ShipmentEvent(ShipEventIdentifier.CANCELED, new Shipment(shipment)));
        return new OperationResult("Shipment: " + shipment.getId() + "return accepted", true);
    }

    private static ReturnAllower instance = null;
}
