package com.company.ui;

import com.company.events.listener.Event;
import com.company.store.Shipment;

public class ShipmentButton extends Button {

    public ShipmentButton(Event thrownEvent, String shipmentId, String shipmentState) {
        super(thrownEvent);
        this.shipmentId = shipmentId;
        this.shipmentState = shipmentState;
    }

    @Override
    public String getText() {
        return shipmentId + " " + shipmentState;
    }

    public String getShipmentId() {
        return shipmentId;
    }

    //TODO: try to eliminate dependency from shipment
    public void setText(Shipment shipment) {
        shipmentState = shipment.getState().getCurrentState();
    }

    private final String shipmentId;
    private String shipmentState;
}
