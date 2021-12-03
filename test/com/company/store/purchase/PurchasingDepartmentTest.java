package com.company.store.purchase;

import com.company.constants.Constants;
import org.junit.Assert;
import org.junit.Test;

import java.lang.constant.Constable;

public class PurchasingDepartmentTest {
    String userEmail = "test@email.com";
    String userEmailUpperCase = "TEST@EMAIL.COM";
    String userEmail2 = "email@test.com";
    String destinationAddress = "destination";
    String receiver = "receiver";
    String typeOfservice = Constants.STANDARD;
    //PurchaseEventTester eventTester = new PurchaseEventTester();

    @Test
    public void purchaseTest() {
        //double errorDelta = 0.0001;
        PurchasingDepartment.getInstance().addUserCart(userEmailUpperCase);
        Assert.assertTrue(PurchasingDepartment.getInstance().addToCart(CatalogUtility.SHOES_ID, 2, userEmail).isSuccessful());
        Assert.assertTrue(PurchasingDepartment.getInstance().addToCart(CatalogUtility.LAPTOP_ID, 1, userEmail).isSuccessful());

        //double totalPrice = CatalogUtility.SHOES_PRICE*2 + CatalogUtility.LAPTOP_PRICE;

        Assert.assertTrue(PurchasingDepartment.getInstance().purchase(typeOfservice, userEmail, destinationAddress, receiver).isSuccessful());

        /*
        Assert.assertTrue(eventTester.eventReceived());
        Assert.assertEquals("indirizzo",eventTester.getAddress()); //TODO: remove placeholder
        Assert.assertNotNull(eventTester.getContents());
        Assert.assertEquals(totalPrice, eventTester.getTotal(), errorDelta);
        Assert.assertEquals(Constants.STANDARD ,eventTester.getService()); //TODO: remove placeholder
        Assert.assertEquals(userEmail ,eventTester.getUserEmail());
         */

    }

    @Test
    public void absentProductPurchase() {
        //TODO: should test for exception?
        PurchasingDepartment.getInstance().addUserCart(userEmail2);
        Assert.assertFalse(PurchasingDepartment.getInstance().addToCart("non existent id", 2, userEmail2).isSuccessful());
        Assert.assertFalse(PurchasingDepartment.getInstance().purchase(typeOfservice, userEmail, destinationAddress, receiver).isSuccessful());

        //Assert.assertFalse(eventTester.eventReceived());
    }

    @Test
    public void absentCartPurchase() {
        //TODO: should test for exception?
        Assert.assertFalse(PurchasingDepartment.getInstance().addToCart(CatalogUtility.SHOES_ID, 2, userEmail2).isSuccessful());
        Assert.assertFalse(PurchasingDepartment.getInstance().purchase(typeOfservice, userEmail, destinationAddress, receiver).isSuccessful());

        //Assert.assertFalse(eventTester.eventReceived());
    }
}
