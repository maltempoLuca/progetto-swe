package com.company.store.controller;

import com.company.constants.Constants;
import com.company.store.OperationResult;
import com.company.store.Store;
import com.company.store.UserDepartment;
import com.company.listener.EventMessage;
import com.company.store.eventsys.events.EventIdentifier;
import com.company.listener.Event;
import com.company.listener.EventListener;
import com.company.store.eventsys.events.StoreMessage;
import com.company.store.eventsys.events.requests.RequestEvent;
import com.company.store.eventsys.events.requests.RequestIdentifier;
import com.company.store.eventsys.events.requests.RequestListener;
import com.company.store.eventsys.events.shipments.ShipmentEvent;
import com.company.store.eventsys.events.shipments.ShipmentEventListener;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

public class Controller implements RequestListener, ShipmentEventListener {

//    public void handleEvent(Event event) {
//        String logEntry;
//
//        EventIdentifier eventIdentifier = event.getIdentifier();
//        EventMessage message = event.getMessage();
//
//        switch (eventIdentifier) {
//            case CHANGE_ADDRESS_ACCEPTED:
//                break;
//
//            case CHANGE_ADDRESS_REFUSED:
//                break;
//
//            case CANCEL_SUCCESS:
//                break;
//
//            case CANCEL_REFUSED:
//                break;
//
//            case RETURN_ACCEPTED:
//                break;
//
//            case RETURN_REFUSED:
//                break;
//
//            case PURCHASE_COMPLETED:
//                logEntry = buildLogEntry("L'utente " + message.getTextInfo(Constants.USER_EMAIL) + " ha eseguito un ordine");
//                break;
//            // le graffe servono affinchè ogni case abbia un suo scope,
//            // altrimenti non potresti dichiarare variabili con stesso nome in case diversi
//            case REGISTER_REQUEST: {
//                String email = message.getTextInfo(Constants.USER_EMAIL);
//                String psw = message.getTextInfo(Constants.USER_PSW);
//                Store.getInstance().registerUser(email, psw);
//                break;
//            }
//
//            case LOGIN_REQUEST: {
//                String email = message.getTextInfo(Constants.USER_EMAIL);
//                String psw = message.getTextInfo(Constants.USER_PSW);
//                Store.getInstance().loginUser(email, psw);
//                break;
//            }
//
//            case LOGOUT_REQUEST: {
//                String email = message.getTextInfo(Constants.USER_EMAIL);
//                Store.getInstance().logoutUser(email);
//                break;
//            }
//
//            case OPERATION_COMPLETED: {
//                String result = message.getTextInfo(Constants.OPERATION_RESULT);
//                System.out.println(result);
//                break;
//            }
//        }
//    }

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
        for (HistoryView view : views.values()) {
            view.render();
        }
    }

    private String timeToString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        LocalTime time = LocalTime.now();
        String formattedTime = "[" + time.format(formatter) + "]";
        return formattedTime;
    }

    private String buildLogEntry(String message) {
        return timeToString() + ": " + message;
    }

    private final Map<String, HistoryView> views = new HashMap<>();

    @Override
    public void handleRequest(RequestEvent request) {
        OperationResult result = null;
        switch (request.getId()) {
            case REGISTER_REQUEST: {
                String email = request.getUserInput(Constants.USER_EMAIL);
                String psw = request.getUserInput(Constants.USER_PSW);
                result = Store.getInstance().registerUser(email, psw);
                break;
            }

        }
        System.out.println(result.getMessage());


    }

    @Override
    public void handleEvent(ShipmentEvent event) {
        switch (event.getId()) {
            case CANCELED: {
                break;
            }
            case CREATED: {
                break;
            }
            case UPDATED: {
                break;
            }
            case RETURNED: {
                break;
            }

        }

    }
}
