package com.company.store;

import com.company.outsideworld.CourierAgency;
import com.company.constants.Constants;
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
            createReturn(event.getShipment(), event.getUserEmail());
        }
    }

    public void handlePurchase(String userEmail, String service, String destination, String receiver, String contents, String id) {
        Shipment shipment = createShipment(Constants.STORE_NAME, receiver, Constants.STORE_ADDRESS, destination, contents, id);
        createService(service, shipment, userEmail);
    }

    public void handlePurchase(String userEmail, String service, String destination, String receiver, String contents) {
        handlePurchase(userEmail, service, destination, receiver, contents, generateId());
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

    void createService(String typeOfService, Shipment shipment, String userEmail) {
        ShipmentService newService = ShipmentFactory.getInstance().factoryMethod(typeOfService, userEmail, shipment).copy();
        requestCourier(newService);
        activeServices.get(userEmail).put(shipment.getId(), newService);

        ShipmentEvent event = new ShipmentEvent(ShipEventIdentifier.CREATED, shipment, userEmail);
        ShipmentEventManager.getInstance().notify(event);
    }

    private Shipment createShipment(String sender, String receiver, String senderAddress, String destinationAddress, String contents, String id) {
        return new Shipment(sender, receiver, senderAddress, destinationAddress, contents, id);
    }

    public OperationResult deleteService(String email, String shipmentID) {
        return activeServices.get(email).get(shipmentID).cancelShipment();
    }

    private void createReturn(Shipment shipment, String userEmail) {
        Shipment newShipment = createShipment(shipment.getReceiver(), shipment.getSender(), shipment.getDestinationAddress(),
                shipment.getSenderAddress(), shipment.getContents(), generateId());
        createService(Constants.RETURN, newShipment, userEmail);
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
        CourierAgency.getInstance().requestCourier(shipmentService);
    }


    private static ShippingDepartment instance = null;
    private final Map<String, Map<String, ShipmentService>> activeServices = new HashMap<>();
    private int currentId = 0;


}