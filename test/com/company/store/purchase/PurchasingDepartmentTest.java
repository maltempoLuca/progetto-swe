package com.company.store.purchase;

import com.company.constants.Constants;
import com.company.store.ShippingDepartment;
import com.company.store.Store;
import com.company.store.UserDepartment;
import exceptions.UnregisteredUserException;
import org.junit.*;

public class PurchasingDepartmentTest {
    static String userEmail = "test@email.com";
    static String userEmailUpperCase = "TEST@EMAIL.COM";
    static String anotherUserEmail = "email@test.com";
    static String destinationAddress = "destination";
    static String receiver = "receiver";
    static String typeOfservice = Constants.STANDARD;
    //PurchaseEventTester eventTester = new PurchaseEventTester();

    @Before
    public void initUserServices() {
        ShippingDepartment.getInstance().addUserServices(userEmail);
        ShippingDepartment.getInstance().addUserServices(anotherUserEmail);
    }

    @Test
    public void purchaseTest() throws UnregisteredUserException {
        //double errorDelta = 0.0001;
        PurchasingDepartment.getInstance().addUserCart(userEmailUpperCase);
        Assert.assertTrue(PurchasingDepartment.getInstance().addToCart(CatalogUtility.SHOES_ID, 2, userEmail).isSuccessful());
        Assert.assertTrue(PurchasingDepartment.getInstance().addToCart(CatalogUtility.LAPTOP_ID, 1, userEmail).isSuccessful());

        //double totalPrice = CatalogUtility.SHOES_PRICE*2 + CatalogUtility.LAPTOP_PRICE;

        Assert.assertTrue(PurchasingDepartment.getInstance().purchase(typeOfservice, userEmail, destinationAddress, receiver).isSuccessful());

    }

    @Test
    public void absentProductPurchase() throws UnregisteredUserException {
        PurchasingDepartment.getInstance().addUserCart(anotherUserEmail);
        Assert.assertFalse(PurchasingDepartment.getInstance().addToCart("non existent id", 2, anotherUserEmail).isSuccessful());
        Assert.assertFalse(PurchasingDepartment.getInstance().purchase(typeOfservice, userEmail, destinationAddress, receiver).isSuccessful());

        //Assert.assertFalse(eventTester.eventReceived());
    }

    @Test
    public void absentCartPurchase() throws UnregisteredUserException {
        Assert.assertFalse(PurchasingDepartment.getInstance().addToCart(CatalogUtility.SHOES_ID, 2, anotherUserEmail).isSuccessful());
        Assert.assertFalse(PurchasingDepartment.getInstance().purchase(typeOfservice, userEmail, destinationAddress, receiver).isSuccessful());
    }

    @Test
    public void unregisteredUserTest() {
        String unregisteredEmail = "test@email.com";
        Assert.assertFalse(PurchasingDepartment.getInstance().addToCart(CatalogUtility.SHOES_ID, 1, unregisteredEmail).isSuccessful());
        Assert.assertFalse(PurchasingDepartment.getInstance().purchase(typeOfservice, unregisteredEmail, destinationAddress, receiver).isSuccessful());
    }

    @After
    public void clearInstances() {
        Store.clearInstance();
        PurchasingDepartment.clearInstance();
        ShippingDepartment.clearInstance();
        ShippingDepartment.clearInstance();
        UserDepartment.clearInstance();
    }
}
