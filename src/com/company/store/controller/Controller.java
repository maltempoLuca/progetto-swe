package com.company.store.controller;

import com.company.constants.Constants;
import com.company.store.OperationResult;
import com.company.store.Store;
import com.company.store.events.requests.RequestEvent;
import com.company.store.events.requests.RequestIdentifier;
import com.company.store.events.requests.RequestListener;
import com.company.store.events.shipments.ShipEventIdentifier;
import com.company.store.events.shipments.ShipmentEvent;
import com.company.store.events.shipments.ShipmentEventListener;

import java.util.HashMap;
import java.util.Map;

public class Controller implements RequestListener, ShipmentEventListener {

    @Override
    public void handleRequest(RequestEvent request) {
        RequestIdentifier requestId = request.getId();
        OperationResult result = null;
        switch (requestId) {
            case REGISTER_REQUEST: {
                String email = request.getUserInput(Constants.USER_EMAIL);
                String psw = request.getUserInput(Constants.USER_PSW);
                result = Store.getInstance().registerUser(email, psw);
                break;
            }

            case LOGIN_REQUEST: {
                String email = request.getUserInput(Constants.USER_EMAIL);
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
        System.out.println(result.getMessage());
    }

    @Override
    public void handleEvent(ShipmentEvent event) {
        ShipEventIdentifier id = event.getId();
        switch (id) {
            case UPDATED:
                break;
            case CREATED:
                break;
            case CANCELED:
                break;
            case RETURNED:
                break;
        }
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
        for (HistoryView view : views.values()) {
            view.render();
        }
    }

    private final Map<String, HistoryView> views = new HashMap<>();
}
