package com.company.store.controller;

import com.company.constants.Constants;
import com.company.exceptions.StoreInitializationException;
import com.company.store.OperationResult;
import com.company.store.events.requestevents.RequestManager;
import com.company.store.events.shipmentevents.ShipEventIdentifier;
import com.company.store.events.shipmentevents.ShipmentEventManager;
import com.company.store.events.viewevents.ViewEventIdentifier;
import com.company.store.events.viewevents.ViewEventManager;
import com.company.store.shipping.Shipment;
import com.company.store.events.requestevents.RequestEvent;
import com.company.store.events.requestevents.RequestListener;
import com.company.store.events.shipmentevents.ShipmentEvent;
import com.company.store.events.shipmentevents.ShipmentEventListener;
import com.company.store.events.viewevents.ViewEvent;
import com.company.store.events.viewevents.ViewEventListener;
import com.company.store.view.UserView;

import java.util.LinkedHashMap;
import java.util.Map;

public final class Controller implements RequestListener, ShipmentEventListener, ViewEventListener {

    public Controller() {
        RequestManager.getInstance().subscribe(this, StoreRequest.REGISTER_REQUEST,
                StoreRequest.LOGIN_REQUEST, StoreRequest.LOGOUT_REQUEST,
                StoreRequest.ADD_TO_CART_REQUEST, StoreRequest.CANCEL_REQUEST, StoreRequest.PURCHASE_REQUEST,
                StoreRequest.CHANGE_ADDRESS_REQUEST, StoreRequest.RETURN_REQUEST, StoreRequest.VIEW_CATALOGUE_REQUEST);
        ShipmentEventManager.getInstance().subscribe(this, ShipEventIdentifier.CANCELED,
                ShipEventIdentifier.CREATED, ShipEventIdentifier.UPDATED, ShipEventIdentifier.RETURNED);
        ViewEventManager.getInstance().subscribe(this, ViewEventIdentifier.CATALOG);
    }

    @Override
    public void handleRequest(RequestEvent event) {
        //receives RequestEvent, calls execute of encapsulated StoreRequest object
        //implementation varies based on Strategy
        //register result in log and then refresh the view

        try {
            String email = event.getUserId();
            updateLog(email, event);
            OperationResult result = event.execute();
            updateLog(email, result);
            refreshViews();
        } catch (StoreInitializationException e) {
            System.out.println(Constants.ANSI_RED + e.getMessage());
        }
    }

    @Override
    public void handleShipmentEvent(ShipmentEvent event) {
        updateView(event);
        refreshViews();
    }

    @Override
    public void handleViewEvent(ViewEvent event) {
        updateView(event);
    }

    private synchronized void clearViews() {
        //delete all prints from console

        System.out.print(Constants.DELETE_ALL);
        System.out.flush();
    }

    private synchronized void refreshViews() {
        clearViews();
        renderViews();
    }

    private synchronized void renderViews() {
        for (UserView view : userViews.values()) {
            view.draw();
        }
    }

    private synchronized void ensureView(String viewId) {
        //check if there is a view corresponding to specified user, if not create it

        if (!userViews.containsKey(viewId)) {
            userViews.put(viewId, new UserView(viewId.toUpperCase()));
        }
    }

    private synchronized void updateView(ShipmentEvent event) {
        //finds user view corresponding to the user who owns the shipment, then updates that view

        Shipment shipment = event.getShipment();
        String viewId = event.getUserEmail();
        ensureView(viewId);
        UserView viewToUpdate = userViews.get(viewId);
        viewToUpdate.updateShipment(shipment);
    }

    private synchronized void updateView(ViewEvent event) {
        //finds user view corresponding to the user who owns the shipment, then updates that view
        //content of event will be registered as optional, it will be printed once and then deleted

        String userEmail = event.getUserEmail();
        ensureView(userEmail);
        UserView viewToUpdate = userViews.get(userEmail);
        viewToUpdate.addOptional(event.getContent());
    }

    private synchronized void updateLog(String viewId, Loggable loggable) {
        //finds user view corresponding to the user who owns the shipment, then updates its log

        ensureView(viewId);
        UserView viewToUpdate = userViews.get(viewId);
        viewToUpdate.addLogEntry(loggable);
    }

    private final Map<String, UserView> userViews = new LinkedHashMap<>();
}
