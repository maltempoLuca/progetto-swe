package com.company.store.usecases;

import com.company.constants.Constants;
import com.company.exceptions.StoreInitializationException;
import com.company.store.OperationResult;
import com.company.store.Store;
import com.company.store.purchase.PurchasingDepartment;
import com.company.store.shipping.ShippingDepartment;
import com.company.store.testagencies.InstantDeliveryAgency;
import com.company.store.user.UserDepartment;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public final class BuyProductsTest {


    @Before
    public void init() throws StoreInitializationException {
        UseCaseUtility.init(userDepartment, shippingDepartment, purchasingDepartment);
        shippingDepartment.setCourierAgency(instantAgency);
    }

    @Test
    public void successfulPurchaseTest() throws StoreInitializationException {
        int standardQuantity = 1;
        int premiumQuantity = 3;
        successfulPurchase(standardQuantity, Constants.STANDARD);
        successfulPurchase(premiumQuantity, Constants.PREMIUM);
    }

    public static void successfulPurchase(int quantity, String typeOfService) throws StoreInitializationException {
        OperationResult addProductResult = Store.getInstance().addToCartRequest(UseCaseConstants.USER_EMAIL, UseCaseConstants.LAMP_ID, quantity);
        OperationResult purchaseResult = Store.getInstance().requestPurchase(typeOfService, UseCaseConstants.USER_EMAIL, UseCaseConstants.DESTINATION_ADDRESS, UseCaseConstants.USER_NAME);
        Assert.assertTrue(addProductResult.isSuccessful());
        Assert.assertTrue(purchaseResult.isSuccessful());
    }

    @Test
    public void unregisteredUserTest() throws StoreInitializationException {
        int quantity = 1;
        String typeOfService = Constants.STANDARD;
        OperationResult addProductResult = Store.getInstance().addToCartRequest(UseCaseConstants.UNREG_USER_EMAIL, UseCaseConstants.LAMP_ID, quantity);
        OperationResult purchaseResult = Store.getInstance().requestPurchase(typeOfService, UseCaseConstants.UNREG_USER_EMAIL, UseCaseConstants.DESTINATION_ADDRESS, UseCaseConstants.USER_NAME);

        Assert.assertFalse(addProductResult.isSuccessful());
        Assert.assertFalse(purchaseResult.isSuccessful());
    }

    @Test
    public void unloggedUserTest() throws StoreInitializationException {
        int quantity = 2;
        String typeOfService= Constants.STANDARD;
        Store.getInstance().requestLogout(UseCaseConstants.USER_EMAIL);
        OperationResult addProductResult = Store.getInstance().addToCartRequest(UseCaseConstants.USER_EMAIL, UseCaseConstants.LAMP_ID, quantity);
        OperationResult purchaseResult = Store.getInstance().requestPurchase(typeOfService, UseCaseConstants.USER_EMAIL, UseCaseConstants.DESTINATION_ADDRESS, UseCaseConstants.USER_NAME);

        Assert.assertFalse(addProductResult.isSuccessful());
        Assert.assertFalse(purchaseResult.isSuccessful());
    }

    @After
    public void cleanup() {
        UseCaseUtility.cleanup();
    }

    InstantDeliveryAgency instantAgency = new InstantDeliveryAgency();
    UserDepartment userDepartment = new UserDepartment();
    ShippingDepartment shippingDepartment = new ShippingDepartment();
    PurchasingDepartment purchasingDepartment = new PurchasingDepartment(shippingDepartment);
}
