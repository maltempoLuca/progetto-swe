package com.company.store.events.requests;

import com.company.constants.Constants;
import com.company.store.OperationResult;
import com.company.store.Store;

public enum RequestIdentifier {

    LOGIN_REQUEST
            /*{
        @Override
        public OperationResult execute(RequestEvent request) {
            String email = request.getUserInput(Constants.USER_EMAIL);
            //updateLog(email, request);
            String psw = request.getUserInput(Constants.USER_PSW);
            return Store.getInstance().loginUser(email, psw);
        };
    };*/

    , REGISTER_REQUEST, LOGOUT_REQUEST, CANCEL_REQUEST, CHANGE_ADDRESS_REQUEST,
    RETURN_REQUEST, PURCHASE_REQUEST, ADD_TO_CART_REQUEST;
    //abstract public OperationResult execute(RequestEvent request);
}
