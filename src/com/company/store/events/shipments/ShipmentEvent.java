package com.company.store.events.shipments;

import com.company.constants.Utility;
import com.company.store.Shipment;
import com.company.store.controller.Loggable;

public class ShipmentEvent implements Loggable {

    public ShipmentEvent(ShipEventIdentifier id, Shipment shipment) {
        this.id = id;
        this.shipment = shipment;
    }

    @Override
    public String getLogMessage() {
        return Utility.timeToString() + " Shipment event of type: " + id.name() + " on shipment: " + shipment.getId();
    }

    public ShipEventIdentifier getId() {
        return id;
    }

    public Shipment getShipment() {
        //TODO: deepcopy
        return shipment;
    }

    private final ShipEventIdentifier id;
    private final Shipment shipment;

}
