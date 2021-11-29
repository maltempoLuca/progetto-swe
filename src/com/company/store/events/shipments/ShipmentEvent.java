package com.company.store.events.shipments;

import com.company.constants.Utility;
import com.company.store.Shipment;
import com.company.store.controller.Loggable;

public class ShipmentEvent implements Loggable {

    public ShipmentEvent(ShipEventIdentifier id, Shipment shipment, String userEmail) {
        this.id = id;
        this.shipment = shipment;
        this.userEmail = userEmail;
    }

    @Override
    public String getLogMessage() {
        return " Shipment event of type: " + id.name() + " on shipment: " + shipment.getId();
    }

    public ShipEventIdentifier getId() {
        return id;
    }

    public Shipment getShipment() {
        //TODO: deepcopy
        return shipment;
    }

    public String getUserEmail() {
        return userEmail;
    }

    private final ShipEventIdentifier id;
    private final Shipment shipment;
    private final String userEmail;
}
