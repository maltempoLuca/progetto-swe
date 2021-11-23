package com.company.store.eventsys.events.shipments;

public interface ShipmentEventListener {
    void handleEvent(ShipmentEvent event);
}
