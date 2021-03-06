package com.company.store;

import com.company.constants.Constants;
import com.company.store.events.requestevents.RequestEvent;
import com.company.store.controller.StoreRequest;
import com.company.store.events.requestevents.RequestManager;

public final class Buttons {
    //used to simulate the navigation in the store graphic interface

    private Buttons() {
    }

    public static Buttons getInstance() {
        if (instance == null)
            instance = new Buttons();
        return instance;
    }

    public void registerUser(String email, String password) {
        RequestEvent request = new RequestEvent(StoreRequest.REGISTER_REQUEST);
        request.addInput(Constants.USER_EMAIL, email).addInput(Constants.USER_PSW, password);
        RequestManager.getInstance().notify(request);
    }

    public void loginUser(String email, String password) {
        RequestEvent request = new RequestEvent(StoreRequest.LOGIN_REQUEST);
        request.addInput(Constants.USER_EMAIL, email).addInput(Constants.USER_PSW, password);
        RequestManager.getInstance().notify(request);
    }

    public void logoutUser(String email) {
        RequestEvent request = new RequestEvent(StoreRequest.LOGOUT_REQUEST, email);
        RequestManager.getInstance().notify(request);
    }

    public void cancelShipment(String email, String shipmentID) {
        RequestEvent request = new RequestEvent(StoreRequest.CANCEL_REQUEST, email);
        request.addInput(Constants.SHIPMENT_ID, shipmentID);
        RequestManager.getInstance().notify(request);
    }

    public void viewCatalogue(String email) {
        RequestEvent request = new RequestEvent(StoreRequest.VIEW_CATALOGUE_REQUEST, email);
        RequestManager.getInstance().notify(request);
    }

    public void changeShipmentAddress(String email, String shipmentID, String newAddress) {
        RequestEvent request = new RequestEvent(StoreRequest.CHANGE_ADDRESS_REQUEST, email);
        request.addInput(Constants.SHIPMENT_ID, shipmentID).addInput(Constants.DESTINATION_ADDRESS, newAddress);
        RequestManager.getInstance().notify(request);
    }

    public void returnShipment(String email, String shipmentID) {
        RequestEvent request = new RequestEvent(StoreRequest.RETURN_REQUEST, email);
        request.addInput(Constants.SHIPMENT_ID, shipmentID);
        RequestManager.getInstance().notify(request);
    }

    public void purchaseItemsFromCart(String typeOfService, String email, String destinationAddress, String receiver) {
        RequestEvent request = new RequestEvent(StoreRequest.PURCHASE_REQUEST, email);
        request.addInput(Constants.SHIPMENT_SERVICE, typeOfService).addInput(Constants.DESTINATION_ADDRESS, destinationAddress).addInput(Constants.RECEIVER, receiver);
        RequestManager.getInstance().notify(request);
    }

    public void addToCart(String email, String itemID, String quantity) {
        RequestEvent request = new RequestEvent(StoreRequest.ADD_TO_CART_REQUEST, email);
        request.addInput(Constants.ITEM_ID, itemID).addInput(Constants.QUANTITY, quantity);
        RequestManager.getInstance().notify(request);
    }

    private static Buttons instance = null;
}
