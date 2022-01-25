package com.company.store;

import com.company.constants.Constants;
import com.company.exceptions.StoreInitializationException;
import com.company.store.purchase.PurchasingDepartment;

import com.company.store.shipping.ShippingDepartment;
import com.company.store.user.UserDepartment;

public final class Store {

    private Store() {

    }

    public static Store getInstance() throws StoreInitializationException {
        if (instance == null) {
            validateDepartments();
            instance = new Store();
        }

        return instance;
    }

    public static void clearInstance() {
        instance = null;
    }

    public OperationResult requestRegistration(String email, String password) {
            return userDepartment.registerUser(email, password);
    }

    public OperationResult requestLogin(String email, String password) {
        return userDepartment.loginUser(email, password);
    }

    public OperationResult requestLogout(String email) {
        return userDepartment.logOut(email);
    }

    public OperationResult requestCancel(String email, String shipmentID) {
        OperationResult operationResult = new OperationResult(Constants.LOGGED_OUT, false);
        if (userDepartment.isLogged(email))
            operationResult = shippingDepartment.cancelService(email, shipmentID);
        return operationResult;
    }

    public OperationResult requestAddressChange(String email, String shipmentID, String newAddress) {
        OperationResult operationResult = new OperationResult(Constants.LOGGED_OUT, false);
        if (userDepartment.isLogged(email))
            operationResult = shippingDepartment.changeAddress(email, shipmentID, newAddress);
        return operationResult;
    }

    public OperationResult requestReturn(String email, String shipmentID) {
        OperationResult operationResult = new OperationResult(Constants.LOGGED_OUT, false);
        if (userDepartment.isLogged(email))
            operationResult = shippingDepartment.requestReturn(email, shipmentID);
        return operationResult;
    }

    public OperationResult requestPurchase(String typeOfService, String email, String destinationAddress, String receiver) {
        OperationResult operationResult = new OperationResult(Constants.LOGGED_OUT, false);
        if (userDepartment.isLogged(email))
            operationResult = purchasingDepartment.purchase(typeOfService, email, destinationAddress, receiver);
        return operationResult;
    }

    public OperationResult addToCartRequest(String email, String itemId, int quantity) {
        OperationResult operationResult = new OperationResult(Constants.LOGGED_OUT, false);
        if (userDepartment.isLogged(email))
            operationResult = purchasingDepartment.addToCart(itemId, quantity, email);

        return operationResult;
    }

    public void addUserCart(String userEmail) {
        purchasingDepartment.addUserCart(userEmail);
    }

    public void addUserServices(String userEmail) {
        shippingDepartment.addUserServices(userEmail);
    }

    public OperationResult requestCatalog(String userEmail) {
        return purchasingDepartment.getCatalog(userEmail);
    }

    private static void validateDepartments() throws StoreInitializationException {
        if (shippingDepartment == null || userDepartment == null || purchasingDepartment == null) {
            throw new StoreInitializationException();
        }
    }

    public static void setShippingDepartment(ShippingDepartment sDep) {
        shippingDepartment = sDep;
    }

    public static void setUserDepartment(UserDepartment uDep) {
        userDepartment = uDep;
    }

    public static void setPurchasingDepartment(PurchasingDepartment pDep) {
        purchasingDepartment = pDep;
    }

    private static Store instance = null;
    private static ShippingDepartment shippingDepartment;
    private static UserDepartment userDepartment;
    private static PurchasingDepartment purchasingDepartment;
}
