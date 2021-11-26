package com.company.store;

import com.company.outsideworld.CourierAgency;
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
        requestCourier(newService); // deve stare qui o in handlePurchase?
    }

    private Shipment createShipment(String sender, String receiver, String senderAddress, String destinationAddress, String contents, String id) {
        return new Shipment(sender, receiver, senderAddress, destinationAddress, contents, id);
    }

    public OperationResult deleteService(String email, String shipmentID) {
        return activeServices.get(email).get(shipmentID).cancelShipment();
    }

    public OperationResult changeAddress(String email, String shipmentID, String newAddress) {
        return activeServices.get(email).get(shipmentID).changeAddress(newAddress);
    }

    public OperationResult requestReturn(String email, String shipmentID) {
        return activeServices.get(email).get(shipmentID).createReturn();
    }

    private String generateId() {
        currentId++;
        String currentString = "#" + String.format("%06d", currentId);
        return currentString;
    }

    private void requestCourier(ShipmentService shipmentService) {
        courierAgency.requestCourier(shipmentService);
    }


    private static ShippingDepartment instance = null;
    private final CourierAgency courierAgency = new CourierAgency();
    private final Map<String, Map<String, ShipmentService>> activeServices = new HashMap<>();
    private int currentId = 0;


}