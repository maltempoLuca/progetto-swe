package com.company.store.events.requests;

import com.company.constants.Constants;
import com.company.store.OperationResult;
import com.company.store.Store;
import exceptions.MissingInputException;
import exceptions.UnregisteredUserException;

public enum RequestIdentifier {

    REGISTER_REQUEST {
        @Override
        public OperationResult execute(RequestEvent request) throws MissingInputException {
            String email = request.getUserInput(Constants.USER_EMAIL);
            String psw = request.getUserInput(Constants.USER_PSW);
            OperationResult result = Store.getInstance().registerUser(email, psw);
            if (result.isSuccessful()) {
                Store.getInstance().addUserCart(email);
                Store.getInstance().addUserServices(email);
            }
            return result;
        }
    },

    LOGIN_REQUEST {
        @Override
        public OperationResult execute(RequestEvent request) throws MissingInputException {
            String email = request.getUserInput(Constants.USER_EMAIL);
            String psw = request.getUserInput(Constants.USER_PSW);
            OperationResult result = Store.getInstance().loginUser(email, psw);
            return result;
        }
    },

    LOGOUT_REQUEST {
        @Override
        public OperationResult execute(RequestEvent request) throws MissingInputException {
            OperationResult result = Store.getInstance().logoutUser(request.getUserId());
            return result;
        }
    },

    CANCEL_REQUEST {
        @Override
        public OperationResult execute(RequestEvent request) throws MissingInputException {
            OperationResult result = Store.getInstance().requestCancel(request.getUserId(),
                    request.getUserInput(Constants.ID_SPEDIZIONE));
            return result;
        }
    },

    CHANGE_ADDRESS_REQUEST {
        @Override
        public OperationResult execute(RequestEvent request) throws MissingInputException {
            OperationResult result = Store.getInstance().requestAddressChange(request.getUserId(),
                    request.getUserInput(Constants.ID_SPEDIZIONE),
                    request.getUserInput(Constants.DESTINATION_ADDRESS));
            return result;
        }
    },

    RETURN_REQUEST {
        @Override
        public OperationResult execute(RequestEvent request) throws MissingInputException {
            return Store.getInstance().requestReturn(request.getUserId(),
                    request.getUserInput(Constants.ID_SPEDIZIONE));
        }
    },

    PURCHASE_REQUEST {
        @Override
        public OperationResult execute(RequestEvent request) throws MissingInputException {
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
        public OperationResult execute(RequestEvent request) throws MissingInputException {
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
        public OperationResult execute(RequestEvent request) throws MissingInputException {
            return Store.getInstance().getCatalog(request.getUserId());
        }
    };

    abstract public OperationResult execute(RequestEvent request) throws MissingInputException ;
}
