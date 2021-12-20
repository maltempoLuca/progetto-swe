package com.company.store.shipping;

import com.company.outsideworld.couriers.CourierAgency;
import com.company.constants.Constants;
import com.company.store.OperationResult;
import com.company.store.events.shipmentevents.ShipEventIdentifier;
import com.company.store.events.shipmentevents.ShipmentEvent;
import com.company.store.events.shipmentevents.ShipmentEventListener;
import com.company.store.events.shipmentevents.ShipmentEventManager;
import com.company.store.factory.ShipmentFactory;
import com.company.exceptions.MissingAgencyException;
import com.company.exceptions.UnregisteredUserException;

import java.util.Map;
import java.util.HashMap;

public class ShippingDepartment implements ShipmentEventListener {

    public ShippingDepartment() {
        ShipmentEventManager.getInstance().subscribe(this, ShipEventIdentifier.RETURNED);
    }

    @Override
    public void handleShipmentEvent(ShipmentEvent event) {
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

    public void addUserServices(String email) {
        if (!activeServices.containsKey(email))
            activeServices.put(email, new HashMap<>());
    }

    private void createService(String typeOfService, Shipment shipment, String userEmail) {
        try {
            ShipmentService newService = ShipmentFactory.getInstance().createService(shipment, typeOfService, userEmail);
            if (!activeServices.containsKey(userEmail)) {
                throw new UnregisteredUserException(userEmail);
            }
            activeServices.get(userEmail).put(shipment.getId(), newService);
            requestCourier(newService);
        } catch (Exception e) {
            currentId--;
            System.out.println(Constants.ANSI_RED + e.getMessage() + ", shipment not created" + Constants.ANSI_RESET);
        }

        ShipmentEvent event = new ShipmentEvent(ShipEventIdentifier.CREATED, shipment, userEmail);
        ShipmentEventManager.getInstance().notify(event);
    }

    private Shipment createShipment(String sender, String receiver, String senderAddress, String destinationAddress, String contents, String id) {
        return new Shipment(sender, receiver, senderAddress, destinationAddress, contents, id);
    }

    public OperationResult cancelService(String email, String shipmentID) {

        OperationResult result;
        result = validateServiceCredentials(email, shipmentID);

        if (result.isSuccessful()) {
            result = activeServices.get(email).get(shipmentID).cancelShipment();
        }

        return result;
    }

    private void createReturn(Shipment shipment, String userEmail) {
        Shipment newShipment = createShipment(shipment.getReceiver(), shipment.getSender(), shipment.getDestinationAddress(),
                shipment.getSenderAddress(), shipment.getContents(), generateId());
        createService(Constants.RETURN, newShipment, userEmail);
    }

    public OperationResult changeAddress(String email, String shipmentID, String newAddress) {
        OperationResult result;
        result = validateServiceCredentials(email, shipmentID);

        if(result.isSuccessful()) {
            result = activeServices.get(email).get(shipmentID).changeAddress(newAddress);
        }

        return result;
    }

    public OperationResult requestReturn(String email, String shipmentID) {
        OperationResult result;
        result = validateServiceCredentials(email, shipmentID);

        if(result.isSuccessful()) {
            result = activeServices.get(email).get(shipmentID).createReturn();
        }
        return result;
    }

    private OperationResult validateServiceCredentials(String email, String shipmentID) {
        OperationResult result;
        if(activeServices.containsKey(email)) {
            if(activeServices.get(email).containsKey(shipmentID)) {
                result = new OperationResult("Valid credentials", true);
            } else {
                result = new OperationResult("No such shipment found, user may not be owner of this shipment", false);
            }
        } else {
            result = new OperationResult("Unregistered user", false);
        }

        return result;
    }

    private String generateId() {
        currentId++;
        String currentString = "#" + String.format("%06d", currentId);
        return currentString;
    }

    private void requestCourier(ShipmentService shipmentService) throws MissingAgencyException {
        if (courierAgency != null)
            courierAgency.requestCourier(shipmentService);
        else
           throw new MissingAgencyException();

    }

    public void setCourierAgency(CourierAgency courierAgency) {
        this.courierAgency = courierAgency;
    }

    private CourierAgency courierAgency = null;
    private final Map<String, Map<String, ShipmentService>> activeServices = new HashMap<>();
    private int currentId = 0;


}