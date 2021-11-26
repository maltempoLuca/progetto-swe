package com.company.store.events.shipments;

public interface ShipmentEventListener {
    void handleEvent(ShipmentEvent event);
}
