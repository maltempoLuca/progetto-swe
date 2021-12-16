package com.company.store.usecases;

import com.company.constants.Constants;
import com.company.store.OperationResult;
import com.company.store.Store;
import com.company.store.shipping.ShippingDepartment;
import com.company.store.testagencies.InstantDeliveryAgency;
import com.company.store.testagencies.ManualCourier;
import com.company.store.testagencies.ManualCourierAgency;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public final class ReturnOrderTest {
    ManualCourier courier = new ManualCourier();
    InstantDeliveryAgency instantAgency = new InstantDeliveryAgency();
    ManualCourierAgency manualAgency = new ManualCourierAgency(courier);

    @Before
    public void registerUser() {
        UseCaseUtility.registerUser();
    }

    @Test
    public void returnSuccessTest() {
        int quantity = 2;
        ShippingDepartment.getInstance().setCourierAgency(instantAgency);
        BuyProductsTest.successfulPurchase(quantity, Constants.PREMIUM);
        OperationResult result = Store.getInstance().requestReturn(UseCaseConstants.USER_EMAIL, UseCaseConstants.FIRST_SHIPMENT_ID);

        Assert.assertTrue(result.isSuccessful());
    }

    @Test
    public void returnFailTest() {
        int quantity = 1;
        ShippingDepartment.getInstance().setCourierAgency(manualAgency);
        BuyProductsTest.successfulPurchase(quantity, Constants.PREMIUM);
        OperationResult result = Store.getInstance().requestReturn(UseCaseConstants.USER_EMAIL, UseCaseConstants.FIRST_SHIPMENT_ID);

        Assert.assertFalse(result.isSuccessful());
    }

    @Test
    public void returnCanceledTest() {
        int quantity = 1;
        ShippingDepartment.getInstance().setCourierAgency(manualAgency);
        BuyProductsTest.successfulPurchase(quantity, Constants.PREMIUM);
        Store.getInstance().requestCancel(UseCaseConstants.USER_EMAIL, UseCaseConstants.FIRST_SHIPMENT_ID);
        OperationResult result = Store.getInstance().requestReturn(UseCaseConstants.USER_EMAIL, UseCaseConstants.FIRST_SHIPMENT_ID);

        Assert.assertFalse(result.isSuccessful());
        Assert.assertEquals("Cannot return shipment: " + UseCaseConstants.FIRST_SHIPMENT_ID + " " + Constants.RETURN_REASON, result.getMessage());
    }

    @Test
    public void doubleReturnTest() {
        int quantity = 3;
        ShippingDepartment.getInstance().setCourierAgency(instantAgency);
        BuyProductsTest.successfulPurchase(quantity, Constants.STANDARD);
        ShippingDepartment.getInstance().setCourierAgency(manualAgency);
        OperationResult firstResult = Store.getInstance().requestReturn(UseCaseConstants.USER_EMAIL, UseCaseConstants.FIRST_SHIPMENT_ID);
        OperationResult secondResult = Store.getInstance().requestReturn(UseCaseConstants.USER_EMAIL, UseCaseConstants.SECOND_SHIPMENT_ID);

        Assert.assertTrue(firstResult.isSuccessful());
        Assert.assertFalse(secondResult.isSuccessful());
        Assert.assertEquals("Impossibile effettuare il reso di un reso", secondResult.getMessage());
    }

    @Test
    public void returnOtherTest() {
        int quantity = 1;
        ShippingDepartment.getInstance().setCourierAgency(instantAgency);
        BuyProductsTest.successfulPurchase(quantity, Constants.STANDARD);
        OperationResult result = Store.getInstance().requestReturn(UseCaseConstants.ANOTHER_USER_EMAIL, UseCaseConstants.FIRST_SHIPMENT_ID);

        Assert.assertFalse(result.isSuccessful());
    }

    @Test
    public void missingShipmentTest() {
        OperationResult result = Store.getInstance().requestReturn(UseCaseConstants.USER_EMAIL, UseCaseConstants.FIRST_SHIPMENT_ID);
        Assert.assertFalse(result.isSuccessful());
    }

    @Test
    public void missingUserTest() {
        int quantity = 2;
        ShippingDepartment.getInstance().setCourierAgency(instantAgency);
        String typeOfService = Constants.STANDARD;
        BuyProductsTest.successfulPurchase(quantity, typeOfService);
        OperationResult result = Store.getInstance().requestReturn(UseCaseConstants.UNREG_USER_EMAIL, UseCaseConstants.FIRST_SHIPMENT_ID);

        Assert.assertFalse(result.isSuccessful());
    }

    @After
    public void clearInstances() {
        UseCaseUtility.clearInstances();
    }
}
