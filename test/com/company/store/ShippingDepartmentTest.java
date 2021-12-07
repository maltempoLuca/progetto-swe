package com.company.store;

import com.company.constants.Constants;
import com.company.outsideworld.CourierAgency;
import com.company.outsideworld.StandardCourierAgency;
import com.company.store.events.shipments.ShipEventIdentifier;
import com.company.store.events.shipments.ShipmentEvent;
import com.company.store.events.shipments.ShipmentEventListener;
import com.company.store.events.shipments.ShipmentEventManager;
import exceptions.MissingAgencyException;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ShippingDepartmentTest {
    final ShipmentEventTester eventTester = new ShipmentEventTester();
    CourierAgency agency = new HelperCourierAgency();
    String email = "pie@pippo.com";
    String destination = "destination";
    String receiver = "receiver";
    String contents = "contents";
    String id = "id";

    @Before
    public void setAgency() {
        this.agency = new HelperCourierAgency();
        ShippingDepartment.getInstance().setCourierAgency(agency);
    }

    @Test
    public void handlePurchaseTest() {
        ShippingDepartment.getInstance().setCourierAgency(agency);
        ShippingDepartment.getInstance().addUserServices(email);
        ShippingDepartment.getInstance().handlePurchase(email, Constants.STANDARD, destination, receiver, contents);
        Shipment testerShipment = eventTester.getShipment();
        Assert.assertNotNull(eventTester.getShipment());
        Assert.assertEquals(email, eventTester.getEmail());
        Assert.assertEquals(destination, testerShipment.getDestinationAddress());
        Assert.assertEquals(receiver, testerShipment.getReceiver());
        Assert.assertEquals(contents, testerShipment.getContents());
        Assert.assertEquals(Constants.STORE_NAME, testerShipment.getSender());
        Assert.assertEquals(Constants.STORE_ADDRESS, testerShipment.getSenderAddress());
    }

    @Test
    public void returnCreationTest() {
        ShippingDepartment.getInstance().addUserServices(email);
        ShippingDepartment.getInstance().handlePurchase(email, Constants.STANDARD, destination, receiver, contents, id);
        Shipment testerShipment;

        boolean result = (ShippingDepartment.getInstance().requestReturn(email, id)).isSuccessful();
        Assert.assertTrue(result);
        testerShipment = eventTester.getShipment();

        Assert.assertEquals(contents, testerShipment.getContents());
        Assert.assertEquals(destination, testerShipment.getSenderAddress());
        Assert.assertEquals(receiver, testerShipment.getSender());
        Assert.assertEquals(Constants.STORE_ADDRESS, testerShipment.getDestinationAddress());
        Assert.assertEquals(Constants.STORE_NAME, testerShipment.getReceiver());
    }

    @After
    public void clearInstances() {
        Store.clearInstance();
        ShippingDepartment.clearInstance();
        ShippingDepartment.clearInstance();
        UserDepartment.clearInstance();
    }

}

class ShipmentEventTester implements ShipmentEventListener {

    ShipmentEventTester() {
        ShipmentEventManager.getInstance().subscribe(this, ShipEventIdentifier.CANCELED,
                ShipEventIdentifier.CREATED, ShipEventIdentifier.UPDATED);
    }

    @Override
    public void handleEvent(ShipmentEvent event) {
        this.email = event.getUserEmail();
        this.shipment = new Shipment(event.getShipment());
    }

    public String getEmail() {
        return email;
    }

    public Shipment getShipment() {
        return shipment;
    }

    private String email;
    private Shipment shipment;
}

class HelperCourierAgency implements CourierAgency {

    @Override
    public void requestCourier(ShipmentService shipmentService) {
        while (shipmentService.getShipment().getState().getNextState() != null) {
            shipmentService.updateShipmentState();
        }
    }
}

