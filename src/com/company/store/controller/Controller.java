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

    public Controller() {
        userViews.put("Couriers", new UserView("COURIERS"));
    }

    @Override
    public void handleRequest(RequestEvent request) {
        RequestIdentifier requestId = request.getId();
        String email = request.getUserId();
        OperationResult result = null;
        updateLog(email, request);
        //result = request.execute();

        switch (requestId) {
            case REGISTER_REQUEST: {
                email = request.getUserInput(Constants.USER_EMAIL);
                updateLog(email, request);
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
                updateLog(email, request);
                String psw = request.getUserInput(Constants.USER_PSW);
                result = Store.getInstance().loginUser(email, psw);
                break;
            }



            case LOGOUT_REQUEST: {
                updateLog(email, request);
                result = Store.getInstance().logoutUser(request.getUserId());
                break;
            }

            case CANCEL_REQUEST: {
                updateLog(email, request);
                result = Store.getInstance().requestCancel(request.getUserId(),
                        request.getUserInput(Constants.ID_SPEDIZIONE));
                break;
            }

            case CHANGE_ADDRESS_REQUEST: {
                updateLog(email, request);
                result = Store.getInstance().requestAddressChange(request.getUserId(),
                        request.getUserInput(Constants.ID_SPEDIZIONE),
                        request.getUserInput(Constants.DESTINATION_ADDRESS));
                break;
            }

            case RETURN_REQUEST: {
                updateLog(email, request);
                result = Store.getInstance().requestReturn(request.getUserId(),
                        request.getUserInput(Constants.ID_SPEDIZIONE));
                break;
            }

            case PURCHASE_REQUEST: {
                updateLog(email, request);
                result = Store.getInstance().requestPurchase(request.getUserId()); //servizio, destinazione, destinatario
                break;
            }

            case ADD_TO_CART_REQUEST: {
                updateLog(email, request);
                try {
                    result = Store.getInstance().addToCartRequest(request.getUserId(),
                            request.getUserInput(Constants.ITEM_ID),
                            request.parseInput(Constants.QUANTITY));
                } catch (NumberFormatException e) {
                    result = new OperationResult(Constants.INVALID_QUANTITY, false);
                }
            }
        }
        updateLog(email, result);
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
        String email = event.getUserEmail();
        updateLog("Couriers", event);
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
        for (UserView view : userViews.values()) {
            view.draw();
        }
    }

    private void ensureView(String viewId) {
        //check if there is a view corresponding to specified user, if not create it
        if (!userViews.containsKey(viewId)) {
            userViews.put(viewId, new UserView(viewId.toUpperCase()));
        }
    }

    private void updateView(ShipmentEvent event) {
        //finds user view corresponding to the user who owns the shipment, then updates that view
        Shipment shipment = event.getShipment();
        String viewId = event.getUserEmail();
        ensureView(viewId);
        UserView viewToUpdate = userViews.get(viewId);
        viewToUpdate.updateShipment(shipment);
    }

    private void updateLog(String viewId, Loggable loggable) {
        ensureView(viewId);
        UserView viewToUpdate = userViews.get(viewId);
        viewToUpdate.addLogEntry(loggable);
    }

    private final Map<String, UserView> userViews = new LinkedHashMap<>();
}
