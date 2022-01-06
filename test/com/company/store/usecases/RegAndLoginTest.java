package com.company.store.usecases;

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

public final class RegAndLoginTest {
    @Before
    public void init() throws StoreInitializationException {
        UseCaseUtility.init(userDepartment, shippingDepartment, purchasingDepartment);
    }

    @Test
    public void registrationSuccessTest() throws StoreInitializationException {
        OperationResult result = Store.getInstance().requestRegistration(testUserEmail, testUserPassword);
        Assert.assertTrue(result.isSuccessful());
    }

    @Test
    public void registrationFailTest() throws StoreInitializationException {
        OperationResult result = Store.getInstance().requestRegistration(invalidUserEmail, testUserPassword);
        Assert.assertFalse(result.isSuccessful());
    }

    @Test
    public void loginSuccessTest() throws StoreInitializationException {
        registrationSuccessTest();
        OperationResult result = Store.getInstance().requestLogin(testUserEmail, testUserPassword);
        Assert.assertTrue(result.isSuccessful());
    }

    @Test
    public void loginFailTest() throws StoreInitializationException {
        registrationSuccessTest();
        OperationResult result = Store.getInstance().requestLogin(invalidUserEmail, testUserPassword);
        Assert.assertFalse(result.isSuccessful());
    }

    @After
    public void cleanup() {
        UseCaseUtility.cleanup();
    }

    String invalidUserEmail = "invalid email";
    String testUserEmail = "testuser@email.com";
    String testUserPassword = "testPassword0";
    UserDepartment userDepartment = new UserDepartment();
    ShippingDepartment shippingDepartment = new ShippingDepartment();
    PurchasingDepartment purchasingDepartment = new PurchasingDepartment(shippingDepartment);
}
