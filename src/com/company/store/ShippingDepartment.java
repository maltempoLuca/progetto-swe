package com.company.store;

import com.company.store.events.shipments.ShipEventIdentifier;
import com.company.store.events.shipments.ShipmentEvent;
import com.company.store.events.shipments.ShipmentEventListener;
import com.company.store.events.shipments.ShipmentEventManager;

import java.util.Map;
import java.util.HashMap;

public class ShippingDepartment implements ShipmentEventListener {

    private ShippingDepartment() {
        ShipmentEventManager.getInstance().subscribe(this, ShipEventIdentifier.RETURNED);
    }

    @Override
    public void handleEvent(ShipmentEvent event) {
        if (event.getId() == ShipEventIdentifier.RETURNED) {

        }
    }

    public void handlePurchase(String userEmail, String service, String destination, String receiver, String contents) {
        //TODO: call createShipment
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

    Shipment createShipment(String userEmail, String sender, String receiver, String senderAddress, String destinationAddress, String contents, String id) {
        return new Shipment(userEmail, sender, receiver, senderAddress, destinationAddress, contents, id);
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