package com.company.store.events.shipmentevents;

public interface ShipmentEventListener {
    void handleEvent(ShipmentEvent event);
}
