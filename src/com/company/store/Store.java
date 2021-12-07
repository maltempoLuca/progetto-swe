package com.company.store;

import com.company.constants.Constants;
import com.company.store.events.OperationResult;
import com.company.store.purchase.PurchasingDepartment;

import com.company.store.shipping.ShippingDepartment;
import com.company.store.user.UserDepartment;

public class Store {

    //TODO: crea store con getInstance()?
    private Store() {

    }

    public static Store getInstance() {
        if (instance == null)
            instance = new Store();
        return instance;
    }

    public OperationResult registerUser(String email, String password) {
        return UserDepartment.getInstance().registerUser(email, password);
    }

    public OperationResult loginUser(String email, String password) {
        return UserDepartment.getInstance().loginUser(email, password);
    }

    public OperationResult logoutUser(String email) {
        return UserDepartment.getInstance().logOut(email);
    }

    public OperationResult requestCancel(String email, String shipmentID) {
        OperationResult operationResult = new OperationResult(Constants.LOGGED_OUT, false);
        if (UserDepartment.getInstance().isLogged(email))
            operationResult = ShippingDepartment.getInstance().deleteService(email, shipmentID);
        return operationResult;
    }

    public OperationResult requestAddressChange(String email, String shipmentID, String newAddress) {
        OperationResult operationResult = new OperationResult(Constants.LOGGED_OUT, false);
        if (UserDepartment.getInstance().isLogged(email))
            operationResult = ShippingDepartment.getInstance().changeAddress(email, shipmentID, newAddress);
        return operationResult;
    }

    public OperationResult requestReturn(String email, String shipmentID) {
        OperationResult operationResult = new OperationResult(Constants.LOGGED_OUT, false);
        if (UserDepartment.getInstance().isLogged(email))
            operationResult = ShippingDepartment.getInstance().requestReturn(email, shipmentID);
        return operationResult;
    }

    public OperationResult requestPurchase(String typeOfService, String email, String destinationAddress, String receiver) {
        OperationResult operationResult = new OperationResult(Constants.LOGGED_OUT, false);
        if (UserDepartment.getInstance().isLogged(email))
            operationResult = PurchasingDepartment.getInstance().purchase(typeOfService, email, destinationAddress, receiver);
        return operationResult;
    }

    public OperationResult addToCartRequest(String email, String itemId, int quantity) {
        OperationResult operationResult = new OperationResult(Constants.LOGGED_OUT, false);
        if (UserDepartment.getInstance().isLogged(email))
            operationResult = PurchasingDepartment.getInstance().addToCart(itemId, quantity, email);

        return operationResult;
    }

    public void addUserCart(String userEmail) {
        PurchasingDepartment.getInstance().addUserCart(userEmail);
    }

    public void addUserServices(String userEmail) {
        ShippingDepartment.getInstance().addUserServices(userEmail);
    }



    private static Store instance = null;

    public OperationResult getCatalog(String userEmail) {
        return PurchasingDepartment.getInstance().getCatalog(userEmail);
    }
}
