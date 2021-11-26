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

    void createService(String typeOfService, Shipment shipment, String userEmail) {
        ShipmentService newService = ShipmentFactory.getInstance().factoryMethod(typeOfService, shipment).copy();
        requestCourier(newService); // deve stare qui o in handlePurchase?
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
    void createReturn(Shipment shipment, String userEmail) {
        //TODO: modifica la stringa vuota dell'id qui sotto
        Shipment newShipment = createShipment(shipment.getReceiver(), shipment.getSender(), shipment.getDestinationAddress(),
                shipment.getSenderAddress(), shipment.getContents(), "");
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
        courierAgency.requestCourier(shipmentService);
    }


    private static ShippingDepartment instance = null;
    private final CourierAgency courierAgency = new CourierAgency();
    private final Map<String, Map<String, ShipmentService>> activeServices = new HashMap<>();
    private int currentId = 0;


}