package com.company.store.purchase;

import com.company.constants.Constants;
import com.company.constants.Utility;
import com.company.listener.Event;
import com.company.listener.EventListener;
import com.company.store.eventsys.events.EventIdentifier;
import com.company.store.eventsys.management.StoreEventManager;
import org.junit.Assert;
import org.junit.Test;

public class PurchasingDepartmentTest {
    String userEmail = "test@email.com";
    String userEmailUpperCase = "TEST@EMAIL.COM";
    String userEmail2 = "email@test.com";
    PurchaseEventTester eventTester = new PurchaseEventTester();

    @Test
    public void purchaseTest() {
        //only check if contents in event is not null because format could change
        double errorDelta = 0.0001;
        PurchasingDepartment.getInstance().addUserCart(userEmailUpperCase);
        PurchasingDepartment.getInstance().addToCart(CatalogUtility.SHOES_ID, 2, userEmail);
        PurchasingDepartment.getInstance().addToCart(CatalogUtility.LAPTOP_ID, 1, userEmail);

        double totalPrice = CatalogUtility.SHOES_PRICE*2 + CatalogUtility.LAPTOP_PRICE;

        PurchasingDepartment.getInstance().purchase(userEmail);

        Assert.assertTrue(eventTester.eventReceived());
        Assert.assertEquals("indirizzo",eventTester.getAddress()); //TODO: remove placeholder
        Assert.assertNotNull(eventTester.getContents());
        Assert.assertEquals(totalPrice, eventTester.getTotal(), errorDelta);
        Assert.assertEquals(Constants.STANDARD ,eventTester.getService()); //TODO: remove placeholder
        Assert.assertEquals(userEmail ,eventTester.getUserEmail());

    }

    @Test
    public void absentProductPurchase() {
        //TODO: shpuld test for exception?
        PurchasingDepartment.getInstance().addUserCart(userEmail2);
        PurchasingDepartment.getInstance().addToCart("non existent id", 2, userEmail2);
        PurchasingDepartment.getInstance().purchase(userEmail2);

        Assert.assertFalse(eventTester.eventReceived());
    }

    @Test
    public void absentCartPurchase() {
        //TODO: should test for exception?
        PurchasingDepartment.getInstance().addToCart(CatalogUtility.SHOES_ID, 2, userEmail2);
        PurchasingDepartment.getInstance().purchase(userEmail2);

        Assert.assertFalse(eventTester.eventReceived());
    }
}

class PurchaseEventTester implements EventListener {

    PurchaseEventTester() {
        StoreEventManager.getInstance().subscribe(this, EventIdentifier.PURCHASE_COMPLETED);
    }

    public void handleEvent(Event event) {
        EventIdentifier eventIdentifier = event.getIdentifier();

        if (eventIdentifier.equals(EventIdentifier.PURCHASE_COMPLETED)) {
            this.received = true;
            this.userEmail = event.getTextInfo(Constants.USEREMAIL);
            this.address = event.getTextInfo(Constants.DESTINATION_ADDRESS);
            this.service = event.getTextInfo(Constants.SHIPMENT_SERVICE);
            this.contents = event.getTextInfo(Constants.CONTENTS);
            this.total = event.getNumericInfo(Constants.PRICE);
        }
    }

    public boolean eventReceived() {
        return received;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public String getAddress() {
        return address;
    }

    public String getService() {
        return service;
    }

    public String getContents() {
        return contents;
    }

    public double getTotal() {
        return total;
    }

    private boolean received = false;
    private String userEmail;
    private String address;
    private String service;
    private String contents;
    private double total;

}
