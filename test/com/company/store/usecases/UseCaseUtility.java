package com.company.store.usecases;

import com.company.exceptions.StoreInitializationException;
import com.company.store.Buttons;
import com.company.store.Store;
import com.company.store.events.requestevents.RequestManager;
import com.company.store.events.shipmentevents.ShipmentEventManager;
import com.company.store.events.viewevents.ViewEventManager;
import com.company.store.purchase.PurchasingDepartment;
import com.company.store.shipping.ShippingDepartment;
import com.company.store.user.UserDepartment;

public final class UseCaseUtility {

    private UseCaseUtility() {}

    public static void init(UserDepartment userDepartment, ShippingDepartment shippingDepartment,
                            PurchasingDepartment purchasingDepartment) throws StoreInitializationException {
        //set store departments
        //then create the singleton instance of Store and register new users
        initStore(userDepartment, shippingDepartment, purchasingDepartment);
        registerUsers();
    }

    public static void cleanup() {
        //logout registered users and delete Singleton instances
        Buttons.getInstance().logoutUser(UseCaseConstants.ANOTHER_USER_EMAIL);
        Buttons.getInstance().logoutUser(UseCaseConstants.USER_EMAIL);
        Store.clearInstance();
        RequestManager.clearInstance();
        ShipmentEventManager.clearInstance();
        ViewEventManager.clearInstance();
    }

    private static void initStore(UserDepartment userDepartment, ShippingDepartment shippingDepartment, PurchasingDepartment purchasingDepartment) {
        Store.setUserDepartment(userDepartment);
        Store.setShippingDepartment(shippingDepartment);
        Store.setPurchasingDepartment(purchasingDepartment);
    }

    private static void registerUsers() throws StoreInitializationException {
        //register and login new users, then initialize their carts and service lists
        Store.getInstance().requestRegistration(UseCaseConstants.USER_EMAIL, UseCaseConstants.USER_PASSWORD);
        Store.getInstance().requestLogin(UseCaseConstants.USER_EMAIL, UseCaseConstants.USER_PASSWORD);
        Store.getInstance().addUserCart(UseCaseConstants.USER_EMAIL);
        Store.getInstance().addUserServices(UseCaseConstants.USER_EMAIL);
        Store.getInstance().requestRegistration(UseCaseConstants.ANOTHER_USER_EMAIL, UseCaseConstants.ANOTHER_USER_PASSWORD);
        Store.getInstance().requestLogin(UseCaseConstants.ANOTHER_USER_EMAIL, UseCaseConstants.ANOTHER_USER_PASSWORD);
        Store.getInstance().addUserCart(UseCaseConstants.ANOTHER_USER_EMAIL);
        Store.getInstance().addUserServices(UseCaseConstants.ANOTHER_USER_EMAIL);
    }
}
