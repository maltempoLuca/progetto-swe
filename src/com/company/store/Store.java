package com.company.store;

import com.company.constants.Constants;
import com.company.store.purchase.PurchasingDepartment;

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

    public OperationResult requestPurchase(String email) {
        OperationResult operationResult = new OperationResult(Constants.LOGGED_OUT, false);
        if (UserDepartment.getInstance().isLogged(email))
            operationResult = PurchasingDepartment.getInstance().purchase(email);
        return operationResult;
    }

    public OperationResult addToCartRequest(String email, String itemId, int quantity) {
        OperationResult operationResult = new OperationResult(Constants.LOGGED_OUT, false);
        if (UserDepartment.getInstance().isLogged(email))
            operationResult = PurchasingDepartment.getInstance().addToCart(itemId, quantity, email);

        return operationResult;
    }


    private static Store instance = null;

}
