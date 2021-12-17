package com.company.store.events.shipmentevents;

import com.company.store.shipping.Shipment;
import com.company.store.controller.Loggable;

public class ShipmentEvent implements Loggable {

    public ShipmentEvent(ShipEventIdentifier id, Shipment shipment, String userEmail) {
        this.id = id;
        this.shipment = shipment;
        this.userEmail = userEmail;
    }

    @Override
    public String getLogMessage() {
        String shipmentEventText = " Shipment event of type: ";
        String onShipmentText = " on shipment: ";

        return shipmentEventText + id.name() + onShipmentText + shipment.getId();
    }

    public ShipEventIdentifier getId() {
        return id;
    }

    public Shipment getShipment() {
        return new Shipment(shipment);
    }

    public String getUserEmail() {
        return userEmail;
    }

    private final ShipEventIdentifier id;
    private final Shipment shipment;
    private final String userEmail;
}
