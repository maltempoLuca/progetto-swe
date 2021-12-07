package com.company.store.controller;

import com.company.store.events.OperationResult;
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
        userViews.put("Couriers", new UserView("COURIERS"));
    }

    @Override
    public final void handleRequest(RequestEvent request) {
        String email = request.getUserId();
        updateLog(email, request);
        OperationResult result = request.execute();
        updateLog(email, result);
        refreshViews();
    }

    @Override
    public final void handleEvent(ShipmentEvent event) {
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
