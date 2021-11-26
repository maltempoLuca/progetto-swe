package com.company.store;

public class Store {

    //TODO: crea store con getInstance()?
    private Store() {

    }

    public static Store getInstance() {
        if (instance == null)
            instance = new Store();
        return instance;
    }

    public OperationResult requestCancel() {
        return null;
    }

    public OperationResult requestAddressChange() {
        return null;
    }

    public OperationResult requestReturn() {
        return null;
    }

    public OperationResult requestPurchase() {
        return null;
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

    private static Store instance = null;


}
