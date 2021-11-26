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

    public void addUserServices(String email) {
        if (!activeServices.containsKey(email))
            activeServices.put(email, new HashMap<>());
    }

    void createService(String typeOfService, Shipment shipment, String email) {
        ShipmentService newService = ShipmentFactory.getInstance().factoryMethod(typeOfService, shipment).copy();
        activeServices.get(email).put(shipment.getId(), newService);
    }

    Shipment createShipment(String sender, String receiver, String senderAddress, String destinationAddress, String contents, String id) {
        return new Shipment(sender, receiver, senderAddress, destinationAddress, contents, id);
    }

    void createReturn(ShipmentService shipmentService) {
        shipmentService.getReturnBehavior().createReturn(shipmentService.getShipment());
    }

    void deleteService() {

    }

    private String generateId() {
        currentId++;
        String currentString = "#" + String.format("%06d", currentId);
        return currentString;
    }

    private static ShippingDepartment instance = null;
    private final Map<String, Map<String, ShipmentService>> activeServices = new HashMap<>();
    private int currentId = 0;

}
