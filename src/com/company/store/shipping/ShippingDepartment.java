package com.company.store.shipping;

import com.company.outsideworld.couriers.CourierAgency;
import com.company.constants.Constants;
import com.company.store.events.OperationResult;
import com.company.store.events.shipmentevents.ShipEventIdentifier;
import com.company.store.events.shipmentevents.ShipmentEvent;
import com.company.store.events.shipmentevents.ShipmentEventListener;
import com.company.store.events.shipmentevents.ShipmentEventManager;
import com.company.store.factory.ShipmentFactory;
import com.company.exceptions.InvalidParameterException;

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

    private void createService(String typeOfService, Shipment shipment, String userEmail) {
        try {
            ShipmentService newService = ShipmentFactory.getInstance().createService(shipment, typeOfService, userEmail);
            requestCourier(newService);
            activeServices.get(userEmail).put(shipment.getId(), newService);
        } catch (InvalidParameterException e) {
            //TODO: gestisci;
        }

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
        if (courierAgency != null)
            courierAgency.requestCourier(shipmentService);
        else
            //TODO: throw exception
            ;

    }

    public void setCourierAgency(CourierAgency courierAgency) {
        this.courierAgency = courierAgency;
    }

    private CourierAgency courierAgency = null;
    private static ShippingDepartment instance = null;
    private final Map<String, Map<String, ShipmentService>> activeServices = new HashMap<>();
    private int currentId = 0;


}