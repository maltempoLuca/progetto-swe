package com.company.store.controller;

import com.company.constants.Constants;
import com.company.store.OperationResult;
import com.company.store.Shipment;
import com.company.store.Store;
import com.company.store.events.requests.RequestEvent;
import com.company.store.events.requests.RequestIdentifier;
import com.company.store.events.requests.RequestListener;
import com.company.store.events.shipments.ShipmentEvent;
import com.company.store.events.shipments.ShipmentEventListener;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.Semaphore;

public class Controller implements RequestListener, ShipmentEventListener {

    @Override
    public void handleRequest(RequestEvent request) {
        updateLog(request);
        RequestIdentifier requestId = request.getId();
        String email = request.getUserId();
        OperationResult result = null;
        switch (requestId) {
            case REGISTER_REQUEST: {
                email = request.getUserInput(Constants.USER_EMAIL);
                String psw = request.getUserInput(Constants.USER_PSW);
                result = Store.getInstance().registerUser(email, psw);
                if (result.isSuccessful()) {
                    Store.getInstance().addUserCart(email);
                    Store.getInstance().addUserServices(email);
                }
                break;
            }

            case LOGIN_REQUEST: {
                email = request.getUserInput(Constants.USER_EMAIL);
                String psw = request.getUserInput(Constants.USER_PSW);
                result = Store.getInstance().loginUser(email, psw);
                break;
            }

            case LOGOUT_REQUEST: {
                result = Store.getInstance().logoutUser(request.getUserId());
                break;
            }

            case CANCEL_REQUEST: {
                result = Store.getInstance().requestCancel(request.getUserId(),
                        request.getUserInput(Constants.ID_SPEDIZIONE));
                break;
            }

            case CHANGE_ADDRESS_REQUEST: {
                result = Store.getInstance().requestAddressChange(request.getUserId(),
                        request.getUserInput(Constants.ID_SPEDIZIONE),
                        request.getUserInput(Constants.DESTINATION_ADDRESS));
                break;
            }

            case RETURN_REQUEST: {
                result = Store.getInstance().requestReturn(request.getUserId(),
                        request.getUserInput(Constants.ID_SPEDIZIONE));
                break;
            }

            case PURCHASE_REQUEST: {
                result = Store.getInstance().requestPurchase(request.getUserId());
                break;
            }

            case ADD_TO_CART_REQUEST: {
                try {
                    result = Store.getInstance().addToCartRequest(request.getUserId(),
                            request.getUserInput(Constants.ITEM_ID),
                            request.parseInput(Constants.QUANTITY));
                } catch (NumberFormatException e) {
                    result = new OperationResult(Constants.INVALID_QUANTITY, false);
                }
            }
        }
        updateView(email, result.getMessage());
        updateLog(result);
        refreshViews();
    }

    @Override
    public void handleEvent(ShipmentEvent event) {
        //ShipEventIdentifier id = event.getId();
        /*
        switch(id) {
            case UPDATED:
                break;
            case CREATED:
                break;
            case CANCELED:
                break;
            case RETURNED:
                break;
            default:
                break;
         */
        updateView(event);
        refreshViews();
    }

    private void clearViews() {
        //delete all prints from console

        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    private void refreshViews() {
        clearViews();
        renderViews();
    }

    private void renderViews() {
        logView.draw();
        for (UserView view : userViews.values()) {
            view.draw();
        }
    }

    private void ensureView(String userEmail) {
        //check if there is a view corresponding to specified user, if not create it
        if (!userViews.containsKey(userEmail)) {
            userViews.put(userEmail, new UserView(userEmail.toUpperCase()));
        }
    }

    private void updateView(String userEmail, String message) {
        //adds specified message to user view
        ensureView(userEmail);
        UserView viewToUpdate = userViews.get(userEmail);
        viewToUpdate.addMessage(message);
    }

    private void updateView(ShipmentEvent event) {
        //finds user view corresponding to the user who owns the shipment, then updates that view
        Shipment shipment = event.getShipment();
        String userEmail = event.getUserEmail();
        ensureView(userEmail);
        UserView viewToUpdate = userViews.get(userEmail);
        viewToUpdate.updateShipment(shipment);
    }

    private void updateLog(Loggable loggable) {
        logView.addLogEntry(loggable);
    }

    private final LogView logView = new LogView();
    private final Map<String, UserView> userViews = new LinkedHashMap<>();
}
