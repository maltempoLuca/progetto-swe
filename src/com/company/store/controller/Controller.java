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
import com.company.store.events.view.ViewEvent;
import com.company.store.events.view.ViewEventListener;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.Semaphore;

public class Controller implements RequestListener, ShipmentEventListener, ViewEventListener {

    public Controller() {
        userViews.put("Couriers", new UserView("COURIERS"));
    }

    @Override
    public void handleRequest(RequestEvent request) {
        String email = request.getUserId();
        updateLog(email, request);
        OperationResult result = request.execute();
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

    private void updateView(ViewEvent event) {
        String userEmail = event.getUserEmail();
        ensureView(userEmail);
        UserView viewToUpdate = userViews.get(userEmail);
        viewToUpdate.addOptional(event.getContent());
    }

    private void updateLog(String viewId, Loggable loggable) {
        ensureView(viewId);
        UserView viewToUpdate = userViews.get(viewId);
        viewToUpdate.addLogEntry(loggable);
    }

    private final Map<String, UserView> userViews = new LinkedHashMap<>();

    @Override
    public void handleViewEvent(ViewEvent event) {
        updateView(event);
    }
}
