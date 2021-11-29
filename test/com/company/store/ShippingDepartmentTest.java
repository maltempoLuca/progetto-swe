package com.company.store;

import com.company.constants.Constants;
import com.company.outsideworld.Courier;
import com.company.outsideworld.CourierAgency;
import com.company.store.events.shipments.ShipEventIdentifier;
import com.company.store.events.shipments.ShipmentEvent;
import com.company.store.events.shipments.ShipmentEventListener;
import com.company.store.events.shipments.ShipmentEventManager;
import org.junit.Assert;
import org.junit.Test;

import javax.management.monitor.Monitor;

public class ShippingDepartmentTest {
    final ShipmentEventTester eventTester = new ShipmentEventTester();

    @Test
    public void handlePurchaseTest() {
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
       CourierAgency.getInstance().start();
       String email = "pie@pippo.com";
       String destination = "destination";
       String receiver = "receiver";
       String contents = "contents";
       String id = "id";
       ShippingDepartment.getInstance().addUserServices(email);
       ShippingDepartment.getInstance().handlePurchase(email, Constants.STANDARD, destination, receiver, contents, id);
       Shipment testerShipment;
       synchronized (LockObj.getLock()) {
           while (!eventTester.getShipment().getState().equals(Constants.DELIVERED)) {
               try {
                   LockObj.getLock().wait();
               } catch (InterruptedException e) {
                   e.printStackTrace();
               }
           }
       }

       CourierAgency.getInstance().setProgramFinished();
       Assert.assertTrue((ShippingDepartment.getInstance().requestReturn(email, id)).isSuccessful());

       synchronized (LockObj.getLock()) {
           while (!eventTester.getShipment().getState().equals(Constants.RETURN_CREATED)) {
               try {
                   LockObj.getLock().wait();
               } catch (InterruptedException e) {
                   e.printStackTrace();
               }
           }

           testerShipment = eventTester.getShipment();
       }
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
        synchronized (LockObj.getLock()) {
                this.email = event.getUserEmail();
                this.shipment = new Shipment(event.getShipment());
                if(event.getId() == ShipEventIdentifier.CREATED)
                    System.out.println("Created");
                LockObj.getLock().notifyAll();
        }
    }

    public String getEmail() {
        return email;
    }

    public Shipment getShipment() {
        return shipment;
    }

    private String email;
    private static final Integer sync = null;
    private Shipment shipment;
}

enum LockObj {
    INSTANCE;
    public static LockObj getLock() {
        return LockObj.INSTANCE;
    }
}

