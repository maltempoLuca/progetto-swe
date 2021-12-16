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

public final class ChangeAddressTest {
    String newAddress = "new address";
    String anotherNewAddress = "another new address";
    ManualCourier courier = new ManualCourier();
    InstantDeliveryAgency instantAgency = new InstantDeliveryAgency();
    ManualCourierAgency manualAgency = new ManualCourierAgency(courier);

    @Before
    public void registerUser() {
        UseCaseUtility.registerUser();
    }

    @Test
    public void changeAddressSuccessTest() {
        ShippingDepartment.getInstance().setCourierAgency(manualAgency);
        BuyProductsTest.successfulPurchase(1, Constants.STANDARD);
        OperationResult result = Store.getInstance().requestAddressChange(UseCaseConstants.USER_EMAIL, UseCaseConstants.FIRST_SHIPMENT_ID, newAddress);

        Assert.assertTrue(result.isSuccessful());
    }

    @Test
    public void doubleAddressChangeTest() {
        ShippingDepartment.getInstance().setCourierAgency(manualAgency);
        BuyProductsTest.successfulPurchase(1, Constants.STANDARD);
        OperationResult firstResult = Store.getInstance().requestAddressChange(UseCaseConstants.USER_EMAIL, UseCaseConstants.FIRST_SHIPMENT_ID, newAddress);
        OperationResult secondResult = Store.getInstance().requestAddressChange(UseCaseConstants.USER_EMAIL, UseCaseConstants.FIRST_SHIPMENT_ID, anotherNewAddress);

        courier.updateShipment();

        OperationResult thirdResult = Store.getInstance().requestAddressChange(UseCaseConstants.USER_EMAIL, UseCaseConstants.FIRST_SHIPMENT_ID, anotherNewAddress);

        Assert.assertTrue(firstResult.isSuccessful());
        Assert.assertFalse(secondResult.isSuccessful());
        Assert.assertTrue(thirdResult.isSuccessful());
    }

    @Test
    public void addressChangeFailTest() {
        ShippingDepartment.getInstance().setCourierAgency(instantAgency);
        BuyProductsTest.successfulPurchase(1, Constants.STANDARD);
        OperationResult result = Store.getInstance().requestAddressChange(UseCaseConstants.USER_EMAIL, UseCaseConstants.FIRST_SHIPMENT_ID, newAddress);

        Assert.assertFalse(result.isSuccessful());
    }

    @Test
    public void changeCanceledAddressTest() {
        ShippingDepartment.getInstance().setCourierAgency(manualAgency);
        BuyProductsTest.successfulPurchase(1, Constants.STANDARD);
        Store.getInstance().requestCancel(UseCaseConstants.USER_EMAIL, UseCaseConstants.FIRST_SHIPMENT_ID);
        OperationResult result = Store.getInstance().requestAddressChange(UseCaseConstants.USER_EMAIL, UseCaseConstants.FIRST_SHIPMENT_ID, newAddress);

        Assert.assertFalse(result.isSuccessful());
       Assert.assertEquals("Destination address of shipment " + UseCaseConstants.FIRST_SHIPMENT_ID + "cannot be changed as state is " + Constants.CANCELLED.getCurrentState(), result.getMessage());
    }

    @Test
    public void changeReturnAddressTest() {
        ShippingDepartment.getInstance().setCourierAgency(instantAgency);
        BuyProductsTest.successfulPurchase(1, Constants.STANDARD);
        Store.getInstance().requestReturn(UseCaseConstants.USER_EMAIL, UseCaseConstants.FIRST_SHIPMENT_ID);
        OperationResult result = Store.getInstance().requestAddressChange(UseCaseConstants.USER_EMAIL, UseCaseConstants.SECOND_SHIPMENT_ID, newAddress);

        Assert.assertFalse(result.isSuccessful());
        Assert.assertEquals("Destination address of shipment: " + UseCaseConstants.SECOND_SHIPMENT_ID +
                "could not be changed " + Constants.INTERNAL_ADDRESS_REASON, result.getMessage());
    }

    @Test
    public void changeOtherTest() {
        ShippingDepartment.getInstance().setCourierAgency(instantAgency);
        BuyProductsTest.successfulPurchase(1, Constants.STANDARD);
        OperationResult result = Store.getInstance().requestAddressChange(UseCaseConstants.ANOTHER_USER_EMAIL, UseCaseConstants.FIRST_SHIPMENT_ID, newAddress);

        Assert.assertFalse(result.isSuccessful());
    }

    @Test
    public void missingShipmentTest() {
        OperationResult result = Store.getInstance().requestAddressChange(UseCaseConstants.USER_EMAIL, UseCaseConstants.FIRST_SHIPMENT_ID, newAddress);
        Assert.assertFalse(result.isSuccessful());
    }

    @Test
    public void missingUserTest() {
        int quantity = 2;
        ShippingDepartment.getInstance().setCourierAgency(instantAgency);
        String typeOfService = Constants.STANDARD;
        BuyProductsTest.successfulPurchase(quantity, typeOfService);
        OperationResult result = Store.getInstance().requestAddressChange(UseCaseConstants.UNREG_USER_EMAIL, UseCaseConstants.FIRST_SHIPMENT_ID, newAddress);

        Assert.assertFalse(result.isSuccessful());
    }

    @After
    public void clearInstances() {
        UseCaseUtility.clearInstances();
    }
}
