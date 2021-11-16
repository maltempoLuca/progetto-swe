package com.company.store;
import com.company.listener.Event;
import com.company.listener.EventListener;
import com.company.store.eventsys.events.EventIdentifier;
import com.company.store.eventsys.management.StoreEventManager;

import java.util.Map;
import java.util.HashMap;

public class ShippingDepartment implements EventListener {

    private ShippingDepartment() {
        StoreEventManager.getInstance().subscribe(this, EventIdentifier.PURCHASE_COMPLETED);
    }

    @Override
    public void handleEvent(Event event) {
        EventIdentifier eventIdentifier = event.getIdentifier();
        switch(eventIdentifier) {
            case PURCHASE_COMPLETED:
                //TODO: call createShipment()
                break;
        }
    }

    public static ShippingDepartment getInstance() {
        if (instance == null)
            instance = new ShippingDepartment();
        return instance;
    }

    void createService(String typeOfService, Shipment shipment) {
        ShipmentFactory.getInstance().factoryMethod(typeOfService, shipment);
    }

    Shipment createShipment(String sender, String receiver, String senderAddress, String destinationAddress, String contents, String id) {
        return new Shipment(sender, receiver, senderAddress, destinationAddress, contents, id);
    }

    void deleteService() {

    }

    private String generateId() {
        currentId++;
        String currentString = "#" + String.format("%06d", currentId);
        return currentString;
    }

    private static ShippingDepartment instance = null;
    private final Map<String, ShipmentService> activeServices = new HashMap<String, ShipmentService>();
    private int currentId = 0;

}
