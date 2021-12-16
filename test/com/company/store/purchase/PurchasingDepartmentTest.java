package com.company.store.purchase;

import com.company.constants.Constants;
import com.company.store.Store;
import com.company.exceptions.UnregisteredUserException;
import com.company.store.shipping.ShippingDepartment;
import com.company.store.user.UserDepartment;
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
    public void purchaseTest() {
        PurchasingDepartment.getInstance().addUserCart(userEmail);
        Assert.assertTrue(PurchasingDepartment.getInstance().addToCart(CatalogUtility.SHOES_ID, 2, userEmail).isSuccessful());
        Assert.assertTrue(PurchasingDepartment.getInstance().addToCart(CatalogUtility.LAPTOP_ID, 1, userEmail).isSuccessful());

        Assert.assertTrue(PurchasingDepartment.getInstance().purchase(typeOfservice, userEmail, destinationAddress, receiver).isSuccessful());
    }

    @Test
    public void consecutivePurchaseTest() {
        PurchasingDepartment.getInstance().addUserCart(userEmail);
        Assert.assertTrue(PurchasingDepartment.getInstance().addToCart(CatalogUtility.SHOES_ID, 2, userEmail).isSuccessful());
        Assert.assertTrue(PurchasingDepartment.getInstance().addToCart(CatalogUtility.LAPTOP_ID, 1, userEmail).isSuccessful());

        Assert.assertTrue(PurchasingDepartment.getInstance().purchase(typeOfservice, userEmail, destinationAddress, receiver).isSuccessful());
        Assert.assertFalse(PurchasingDepartment.getInstance().purchase(typeOfservice, userEmail, destinationAddress, receiver).isSuccessful());
    }

    @Test
    public void uppercasePurchaseTest() {
        PurchasingDepartment.getInstance().addUserCart(userEmailUpperCase);
        Assert.assertTrue(PurchasingDepartment.getInstance().addToCart(CatalogUtility.SHOES_ID, 2, userEmail).isSuccessful());
        Assert.assertTrue(PurchasingDepartment.getInstance().addToCart(CatalogUtility.LAPTOP_ID, 1, userEmail).isSuccessful());

        Assert.assertTrue(PurchasingDepartment.getInstance().purchase(typeOfservice, userEmail, destinationAddress, receiver).isSuccessful());
    }

    @Test
    public void absentProductTest() {
        PurchasingDepartment.getInstance().addUserCart(anotherUserEmail);
        Assert.assertFalse(PurchasingDepartment.getInstance().addToCart("non existent id", 2, anotherUserEmail).isSuccessful());
        Assert.assertFalse(PurchasingDepartment.getInstance().purchase(typeOfservice, userEmail, destinationAddress, receiver).isSuccessful());

        //Assert.assertFalse(eventTester.eventReceived());
    }

    @Test
    public void emptyCartTest() {
        Assert.assertFalse(PurchasingDepartment.getInstance().purchase(typeOfservice, userEmail, destinationAddress, receiver).isSuccessful());
    }

    @Test
    public void absentCartTest() {
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
