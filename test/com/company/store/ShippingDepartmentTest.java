package com.company.store;

import com.company.constants.Constants;
import com.company.store.purchase.PurchasingDepartment;
import com.company.store.testagencies.InstantDeliveryAgency;
import com.company.store.user.UserDepartment;
import org.junit.After;
import com.company.outsideworld.couriers.CourierAgency;
import com.company.store.events.shipmentevents.ShipEventIdentifier;
import com.company.store.events.shipmentevents.ShipmentEvent;
import com.company.store.events.shipmentevents.ShipmentEventListener;
import com.company.store.events.shipmentevents.ShipmentEventManager;
import com.company.store.shipping.ShippingDepartment;
import com.company.store.shipping.Shipment;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public final class ShippingDepartmentTest {

    @Before
    public void setAgency() {
        this.agency = new InstantDeliveryAgency();
        shippingDepartment.setCourierAgency(agency);
    }

    @Test
    public void handlePurchaseTest() {
        shippingDepartment.setCourierAgency(agency);
        shippingDepartment.addUserServices(email);
        shippingDepartment.handlePurchase(email, Constants.STANDARD, destination, receiver, contents);
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
        shippingDepartment.addUserServices(email);
        shippingDepartment.handlePurchase(email, Constants.STANDARD, destination, receiver, contents, id);
        Shipment testerShipment;

        boolean result = (shippingDepartment.requestReturn(email, id)).isSuccessful();
        Assert.assertTrue(result);
        testerShipment = eventTester.getShipment();

        Assert.assertEquals(contents, testerShipment.getContents());
        Assert.assertEquals(destination, testerShipment.getSenderAddress());
        Assert.assertEquals(receiver, testerShipment.getSender());
        Assert.assertEquals(Constants.STORE_ADDRESS, testerShipment.getDestinationAddress());
        Assert.assertEquals(Constants.STORE_NAME, testerShipment.getReceiver());
    }


    final ShipmentEventTester eventTester = new ShipmentEventTester();
    CourierAgency agency = new InstantDeliveryAgency();
    String email = "pie@pippo.com";
    String destination = "destination";
    String receiver = "receiver";
    String contents = "contents";
    String id = "id";
    ShippingDepartment shippingDepartment = new ShippingDepartment();
}

final class ShipmentEventTester implements ShipmentEventListener {

    ShipmentEventTester() {
        ShipmentEventManager.getInstance().subscribe(this, ShipEventIdentifier.CANCELED,
                ShipEventIdentifier.CREATED, ShipEventIdentifier.UPDATED);
    }

    @Override
    public void handleShipmentEvent(ShipmentEvent event) {
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

