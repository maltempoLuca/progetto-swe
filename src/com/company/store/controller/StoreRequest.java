package com.company.store.controller;

import com.company.constants.Constants;
import com.company.exceptions.MissingInputException;
import com.company.exceptions.StoreInitializationException;
import com.company.store.OperationResult;
import com.company.store.Store;
import com.company.store.events.requestevents.RequestEvent;

public enum StoreRequest {

    REGISTER_REQUEST {
        @Override
        public OperationResult execute(RequestEvent request) throws MissingInputException, StoreInitializationException {
            String email = request.getUserInput(Constants.USER_EMAIL);
            String psw = request.getUserInput(Constants.USER_PSW);
            OperationResult result = Store.getInstance().requestRegistration(email, psw);
            if (result.isSuccessful()) {
                Store.getInstance().addUserCart(email);
                Store.getInstance().addUserServices(email);
            }
            return result;
        }
    },

    LOGIN_REQUEST {
        @Override
        public OperationResult execute(RequestEvent request) throws MissingInputException, StoreInitializationException {
            String email = request.getUserInput(Constants.USER_EMAIL);
            String psw = request.getUserInput(Constants.USER_PSW);
            OperationResult result = Store.getInstance().requestLogin(email, psw);
            return result;
        }
    },

    LOGOUT_REQUEST {
        @Override
        public OperationResult execute(RequestEvent request) throws StoreInitializationException {
            OperationResult result = Store.getInstance().requestLogout(request.getUserId());
            return result;
        }
    },

    CANCEL_REQUEST {
        @Override
        public OperationResult execute(RequestEvent request) throws MissingInputException, StoreInitializationException {
            OperationResult result = Store.getInstance().requestCancel(request.getUserId(),
                    request.getUserInput(Constants.SHIPMENT_ID));
            return result;
        }
    },

    CHANGE_ADDRESS_REQUEST {
        @Override
        public OperationResult execute(RequestEvent request) throws MissingInputException, StoreInitializationException {
            OperationResult result = Store.getInstance().requestAddressChange(request.getUserId(),
                    request.getUserInput(Constants.SHIPMENT_ID),
                    request.getUserInput(Constants.DESTINATION_ADDRESS));
            return result;
        }
    },

    RETURN_REQUEST {
        @Override
        public OperationResult execute(RequestEvent request) throws MissingInputException, StoreInitializationException {
            return Store.getInstance().requestReturn(request.getUserId(),
                    request.getUserInput(Constants.SHIPMENT_ID));
        }
    },

    PURCHASE_REQUEST {
        @Override
        public OperationResult execute(RequestEvent request) throws MissingInputException, StoreInitializationException {
            String userEmail = request.getUserId();
            String typeOfService = request.getUserInput(Constants.SHIPMENT_SERVICE);
            String destinationAddress = request.getUserInput(Constants.DESTINATION_ADDRESS);
            String receiver = request.getUserInput(Constants.RECEIVER);

            OperationResult result = Store.getInstance().requestPurchase(typeOfService, userEmail,
                    destinationAddress, receiver);
            return result;
        }
    },

    ADD_TO_CART_REQUEST {
        @Override
        public OperationResult execute(RequestEvent request) throws MissingInputException, StoreInitializationException {
            OperationResult result;
            try {
                result = Store.getInstance().addToCartRequest(request.getUserId(),
                        request.getUserInput(Constants.ITEM_ID),
                        request.parseInput(Constants.QUANTITY));
            } catch (NumberFormatException e) {
                result = new OperationResult(Constants.INVALID_QUANTITY, false);
            }
            return result;
        }
    },

    VIEW_CATALOGUE_REQUEST {
        @Override
        public OperationResult execute(RequestEvent request) throws StoreInitializationException {
            return Store.getInstance().requestCatalog(request.getUserId());
        }
    };

    abstract public OperationResult execute(RequestEvent request) throws MissingInputException, StoreInitializationException;
}
