package com.company.store.events.shipmentevents;

public interface ShipmentEventListener {
    void handleShipmentEvent(ShipmentEvent event);
}
