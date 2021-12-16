package com.company.store.usecases;

import com.company.store.Buttons;
import com.company.store.Store;
import com.company.store.shipping.ShippingDepartment;
import com.company.store.user.UserDepartment;

public final class UseCaseUtility {

    public static void registerUser() {
        Store.getInstance().requestRegistration(UseCaseConstants.USER_EMAIL, UseCaseConstants.USER_PASSWORD);
        Store.getInstance().requestLogin(UseCaseConstants.USER_EMAIL, UseCaseConstants.USER_PASSWORD);
        Store.getInstance().addUserCart(UseCaseConstants.USER_EMAIL);
        Store.getInstance().addUserServices(UseCaseConstants.USER_EMAIL);
        Store.getInstance().requestRegistration(UseCaseConstants.ANOTHER_USER_EMAIL, UseCaseConstants.ANOTHER_USER_PASSWORD);
        Store.getInstance().requestLogin(UseCaseConstants.ANOTHER_USER_EMAIL, UseCaseConstants.ANOTHER_USER_PASSWORD);
        Store.getInstance().addUserCart(UseCaseConstants.ANOTHER_USER_EMAIL);
        Store.getInstance().addUserServices(UseCaseConstants.ANOTHER_USER_EMAIL);
    }

    public static void clearInstances() {
        Buttons.getInstance().logoutUser(UseCaseConstants.ANOTHER_USER_EMAIL);
        Buttons.getInstance().logoutUser(UseCaseConstants.USER_EMAIL);
        Store.clearInstance();
        ShippingDepartment.clearInstance();
        ShippingDepartment.clearInstance();
        UserDepartment.clearInstance();
    }
}
