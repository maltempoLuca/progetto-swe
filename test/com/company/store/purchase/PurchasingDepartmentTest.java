package com.company.store.purchase;

import com.company.constants.Constants;
import com.company.store.Store;
import com.company.exceptions.UnregisteredUserException;
import com.company.store.shipping.ShippingDepartment;
import com.company.store.user.UserDepartment;
import org.junit.*;

public final class PurchasingDepartmentTest {

    @Before
    public void initUserServices() {
        shippingDepartment.addUserServices(userEmail);
        shippingDepartment.addUserServices(anotherUserEmail);
    }

    @Test
    public void purchaseTest() {
        purchasingDepartment.addUserCart(userEmail);
        Assert.assertTrue(purchasingDepartment.addToCart(CatalogUtility.SHOES_ID, 2, userEmail).isSuccessful());
        Assert.assertTrue(purchasingDepartment.addToCart(CatalogUtility.LAPTOP_ID, 1, userEmail).isSuccessful());

        Assert.assertTrue(purchasingDepartment.purchase(typeOfservice, userEmail, destinationAddress, receiver).isSuccessful());
    }

    @Test
    public void consecutivePurchaseTest() {
        purchasingDepartment.addUserCart(userEmail);
        Assert.assertTrue(purchasingDepartment.addToCart(CatalogUtility.SHOES_ID, 2, userEmail).isSuccessful());
        Assert.assertTrue(purchasingDepartment.addToCart(CatalogUtility.LAPTOP_ID, 1, userEmail).isSuccessful());

        Assert.assertTrue(purchasingDepartment.purchase(typeOfservice, userEmail, destinationAddress, receiver).isSuccessful());
        Assert.assertFalse(purchasingDepartment.purchase(typeOfservice, userEmail, destinationAddress, receiver).isSuccessful());
    }

    @Test
    public void uppercasePurchaseTest() {
        purchasingDepartment.addUserCart(userEmailUpperCase);
        Assert.assertTrue(purchasingDepartment.addToCart(CatalogUtility.SHOES_ID, 2, userEmail).isSuccessful());
        Assert.assertTrue(purchasingDepartment.addToCart(CatalogUtility.LAPTOP_ID, 1, userEmail).isSuccessful());

        Assert.assertTrue(purchasingDepartment.purchase(typeOfservice, userEmail, destinationAddress, receiver).isSuccessful());
    }

    @Test
    public void absentProductTest() {
        purchasingDepartment.addUserCart(anotherUserEmail);
        Assert.assertFalse(purchasingDepartment.addToCart("non existent id", 2, anotherUserEmail).isSuccessful());
        Assert.assertFalse(purchasingDepartment.purchase(typeOfservice, userEmail, destinationAddress, receiver).isSuccessful());

        //Assert.assertFalse(eventTester.eventReceived());
    }

    @Test
    public void emptyCartTest() {
        Assert.assertFalse(purchasingDepartment.purchase(typeOfservice, userEmail, destinationAddress, receiver).isSuccessful());
    }

    @Test
    public void absentCartTest() {
        Assert.assertFalse(purchasingDepartment.addToCart(CatalogUtility.SHOES_ID, 2, anotherUserEmail).isSuccessful());
        Assert.assertFalse(purchasingDepartment.purchase(typeOfservice, userEmail, destinationAddress, receiver).isSuccessful());
    }

    @Test
    public void unregisteredUserTest() {
        String unregisteredEmail = "test@email.com";
        Assert.assertFalse(purchasingDepartment.addToCart(CatalogUtility.SHOES_ID, 1, unregisteredEmail).isSuccessful());
        Assert.assertFalse(purchasingDepartment.purchase(typeOfservice, unregisteredEmail, destinationAddress, receiver).isSuccessful());
    }

    String userEmail = "test@email.com";
    String userEmailUpperCase = "TEST@EMAIL.COM";
    String anotherUserEmail = "email@test.com";
    String destinationAddress = "destination";
    String receiver = "receiver";
    String typeOfservice = Constants.STANDARD;
    ShippingDepartment shippingDepartment = new ShippingDepartment();
    PurchasingDepartment purchasingDepartment = new PurchasingDepartment(shippingDepartment);
}
