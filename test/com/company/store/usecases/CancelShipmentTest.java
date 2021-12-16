package com.company.store.usecases;

import com.company.constants.Constants;
import com.company.store.OperationResult;
import com.company.store.Store;
import com.company.store.shipping.ShippingDepartment;
import com.company.store.testagencies.InstantDeliveryAgency;
import com.company.store.testagencies.ManualCourier;
import com.company.store.testagencies.ManualCourierAgency;
import org.junit.*;

public final class CancelShipmentTest {
    ManualCourier courier = new ManualCourier();
    InstantDeliveryAgency instantAgency = new InstantDeliveryAgency();
    ManualCourierAgency manualAgency = new ManualCourierAgency(courier);

    @Before
    public void registerUser() {
        UseCaseUtility.registerUser();
    }

    @Test
    public void successfulCancel() {
        int quantity = 1;
        String shipmentId = "#000001";
        ShippingDepartment.getInstance().setCourierAgency(manualAgency);
        String typeOfService = Constants.STANDARD;
        BuyProductsTest.successfulPurchase(quantity, typeOfService);
        OperationResult result = Store.getInstance().requestCancel(UseCaseConstants.USER_EMAIL, shipmentId);
        Assert.assertTrue(result.isSuccessful());

    }

    @Test
    public void doubleCancelTest() {
        int quantity = 1;
        String shipmentId = "#000001";
        ShippingDepartment.getInstance().setCourierAgency(manualAgency);
        String typeOfService = Constants.STANDARD;
        BuyProductsTest.successfulPurchase(quantity, typeOfService);
        OperationResult firstResult = Store.getInstance().requestCancel(UseCaseConstants.USER_EMAIL, shipmentId);
        OperationResult secondResult = Store.getInstance().requestCancel(UseCaseConstants.USER_EMAIL, shipmentId);

        Assert.assertTrue(firstResult.isSuccessful());
        Assert.assertFalse(secondResult.isSuccessful());
    }

    @Test
    public void cancelFailTest() {
        int quantity = 2;
        ShippingDepartment.getInstance().setCourierAgency(instantAgency);
        String typeOfService = Constants.STANDARD;
        BuyProductsTest.successfulPurchase(quantity, typeOfService);
        OperationResult result = Store.getInstance().requestCancel(UseCaseConstants.USER_EMAIL, UseCaseConstants.FIRST_SHIPMENT_ID);

        Assert.assertFalse(result.isSuccessful());
    }

    @Test
    public void cancelReturnTest() {
        int quantity = 2;
        ShippingDepartment.getInstance().setCourierAgency(instantAgency);
        String typeOfService = Constants.STANDARD;
        BuyProductsTest.successfulPurchase(quantity, typeOfService);
        ShippingDepartment.getInstance().setCourierAgency(manualAgency);
        Store.getInstance().requestReturn(UseCaseConstants.USER_EMAIL, UseCaseConstants.FIRST_SHIPMENT_ID);
        OperationResult result = Store.getInstance().requestCancel(UseCaseConstants.USER_EMAIL, UseCaseConstants.SECOND_SHIPMENT_ID);

        Assert.assertFalse(result.isSuccessful());
        Assert.assertEquals("Shipment: " + UseCaseConstants.SECOND_SHIPMENT_ID + " could not be canceled because it is a Return!", result.getMessage());
    }

    @Test
    public void cancelOtherTest() {
        int quantity = 2;
        ShippingDepartment.getInstance().setCourierAgency(instantAgency);
        String typeOfService = Constants.STANDARD;
        BuyProductsTest.successfulPurchase(quantity, typeOfService);
        OperationResult result = Store.getInstance().requestCancel(UseCaseConstants.ANOTHER_USER_EMAIL, UseCaseConstants.FIRST_SHIPMENT_ID);

        Assert.assertFalse(result.isSuccessful());
    }

    @Test
    public void missingShipmentTest() {
        OperationResult result = Store.getInstance().requestCancel(UseCaseConstants.USER_EMAIL, UseCaseConstants.FIRST_SHIPMENT_ID);
        Assert.assertFalse(result.isSuccessful());
    }

    @Test
    public void missingUserTest() {
        int quantity = 2;
        ShippingDepartment.getInstance().setCourierAgency(instantAgency);
        String typeOfService = Constants.STANDARD;
        BuyProductsTest.successfulPurchase(quantity, typeOfService);
        OperationResult result = Store.getInstance().requestCancel(UseCaseConstants.UNREG_USER_EMAIL, UseCaseConstants.FIRST_SHIPMENT_ID);

        Assert.assertFalse(result.isSuccessful());
    }

    @After
    public void clearInstances() {
        UseCaseUtility.clearInstances();
    }
}
