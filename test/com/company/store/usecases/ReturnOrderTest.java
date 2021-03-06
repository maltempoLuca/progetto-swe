package com.company.store.usecases;

import com.company.constants.Constants;
import com.company.exceptions.StoreInitializationException;
import com.company.store.OperationResult;
import com.company.store.Store;
import com.company.store.purchase.PurchasingDepartment;
import com.company.store.shipping.ShippingDepartment;
import com.company.store.testagencies.InstantDeliveryAgency;
import com.company.store.testagencies.ManualCourier;
import com.company.store.testagencies.ManualCourierAgency;
import com.company.store.user.UserDepartment;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public final class ReturnOrderTest {


    @Before
    public void init() throws StoreInitializationException {
        UseCaseUtility.init(userDepartment, shippingDepartment, purchasingDepartment);
    }

    @Test
    public void returnSuccessTest() throws StoreInitializationException {
        int quantity = 2;
        shippingDepartment.setCourierAgency(instantAgency);
        BuyProductsTest.successfulPurchase(quantity, Constants.PREMIUM);
        OperationResult result = Store.getInstance().requestReturn(UseCaseConstants.USER_EMAIL, UseCaseConstants.SHIPMENT_ID);

        Assert.assertTrue(result.isSuccessful());
    }

    @Test
    public void returnFailTest() throws StoreInitializationException {
        int quantity = 1;
        shippingDepartment.setCourierAgency(manualAgency);
        BuyProductsTest.successfulPurchase(quantity, Constants.PREMIUM);
        OperationResult result = Store.getInstance().requestReturn(UseCaseConstants.USER_EMAIL, UseCaseConstants.SHIPMENT_ID);

        Assert.assertFalse(result.isSuccessful());
    }

    @Test
    public void returnOtherTest() throws StoreInitializationException {
        int quantity = 1;
        shippingDepartment.setCourierAgency(instantAgency);
        BuyProductsTest.successfulPurchase(quantity, Constants.STANDARD);
        OperationResult result = Store.getInstance().requestReturn(UseCaseConstants.ANOTHER_USER_EMAIL, UseCaseConstants.SHIPMENT_ID);

        Assert.assertFalse(result.isSuccessful());
    }

    @Test
    public void missingShipmentTest() throws StoreInitializationException {
        OperationResult result = Store.getInstance().requestReturn(UseCaseConstants.USER_EMAIL, UseCaseConstants.SHIPMENT_ID);
        Assert.assertFalse(result.isSuccessful());
    }

    @Test
    public void missingUserTest() throws StoreInitializationException {
        int quantity = 2;
        shippingDepartment.setCourierAgency(instantAgency);
        String typeOfService = Constants.STANDARD;
        BuyProductsTest.successfulPurchase(quantity, typeOfService);
        OperationResult result = Store.getInstance().requestReturn(UseCaseConstants.UNREG_USER_EMAIL, UseCaseConstants.SHIPMENT_ID);

        Assert.assertFalse(result.isSuccessful());
    }

    @Test
    public void unloggedUserTest() throws StoreInitializationException {
        int quantity = 2;
        shippingDepartment.setCourierAgency(instantAgency);
        String typeOfService = Constants.STANDARD;
        BuyProductsTest.successfulPurchase(quantity, typeOfService);
        Store.getInstance().requestLogout(UseCaseConstants.USER_EMAIL);
        OperationResult result = Store.getInstance().requestReturn(UseCaseConstants.UNREG_USER_EMAIL, UseCaseConstants.SHIPMENT_ID);
    }

    @After
    public void cleanup() {
        UseCaseUtility.cleanup();
    }

    ManualCourier courier = new ManualCourier();
    InstantDeliveryAgency instantAgency = new InstantDeliveryAgency();
    ManualCourierAgency manualAgency = new ManualCourierAgency(courier);
    UserDepartment userDepartment = new UserDepartment();
    ShippingDepartment shippingDepartment = new ShippingDepartment();
    PurchasingDepartment purchasingDepartment = new PurchasingDepartment(shippingDepartment);
}
