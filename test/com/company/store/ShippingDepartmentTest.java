package com.company.store;

import com.company.constants.Constants;
import com.company.outsideworld.couriers.CourierAgency;
import com.company.store.events.shipmentevents.ShipEventIdentifier;
import com.company.store.events.shipmentevents.ShipmentEvent;
import com.company.store.events.shipmentevents.ShipmentEventListener;
import com.company.store.events.shipmentevents.ShipmentEventManager;
import com.company.store.shipping.ShippingDepartment;
import com.company.store.shipping.Shipment;
import com.company.store.shipping.ShipmentService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ShippingDepartmentTest {
    final ShipmentEventTester eventTester = new ShipmentEventTester();
    CourierAgency agency = new HelperCourierAgency();

    @Before
    public void setAgency() {
        this.agency = new HelperCourierAgency();
        ShippingDepartment.getInstance().setCourierAgency(agency);
    }

    @Test
    public void handlePurchaseTest() {
        ShippingDepartment.getInstance().setCourierAgency(agency);
        String email = "pie@pippo.com";
        String destination = "destination";
        String receiver = "receiver";
        String contents = "contents";
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
        String email = "pie@pippo.com";
        String destination = "destination";
        String receiver = "receiver";
        String contents = "contents";
        String id = "id";
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

