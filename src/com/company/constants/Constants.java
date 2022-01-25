package com.company.constants;

import com.company.store.shipping.ShipmentState;

public class Constants {

    private Constants() {}

    //shipment priorities
    public static final int HIGH_PRIORITY = 1;
    public static final int LOW_PRIORITY = 2;

    //state names
    public static final String REQUEST_RECEIVED = "Request received";
    public static final String ADDRESS_CHANGED = "Address Changed";

    //shipment states
    public static final ShipmentState DELIVERED = new ShipmentState("Delivered", null);
    public static final ShipmentState OUT_FOR_DELIVERY = new ShipmentState("Out for delivery", DELIVERED);
    public static final ShipmentState IN_TRANSIT = new ShipmentState("In transit", OUT_FOR_DELIVERY);
    public static final ShipmentState SENT = new ShipmentState("Sent", IN_TRANSIT);
    public static final ShipmentState CREATED = new ShipmentState("Created", SENT);
    public static final ShipmentState CANCELLED = new ShipmentState("Cancelled", null);

    //return states
    public static final ShipmentState RETURN_DELIVERED = new ShipmentState("Return delivered", null);
    public static final ShipmentState PICKED_UP = new ShipmentState("Picked up", RETURN_DELIVERED);
    public static final ShipmentState RETURN_CREATED = new ShipmentState("Return created", PICKED_UP);
    public static final ShipmentState RETURN_CONFIRMED = new ShipmentState("Return confirmed", null);

    //type of services
    public static final String STANDARD = "Standard";
    public static final String PREMIUM = "Premium";
    public static final String RETURN = "Return";

    //type of errors
    public static final String SUCCESS = "SUCCESS";

    //event data
    public static final String INTERNAL_ADDRESS_REASON = "as you cannot change a return's destination address";

    public static final String REGISTRATION_SUCCESS = "Signin done.";
    public static final String LOGIN_SUCCESS = "Login done.";
    public static final String LOGOUT_SUCCESS = "Logout done.";
    public static final String SHORT_PSW = "Password has to be at least 6 characters long.";
    public static final String ONLY_LETTERS_PSW = "The password must contains numbers.";
    public static final String ONLY_NUMBERS_PSW = "The password must contain letters.";
    public static final String WRONG_PSW = "Wrong Password, try again.";
    public static final String WRONG_EMAIL = "This email doesn't correspond to any user." + "\n"
            + "Please enter the email address used during registration.";
    public static final String INVALID_EMAIL = "The email must contain '@'.";
    public static final String EMAIL_ALREADY_USED = "The email you have entered is already in use, " +
            "try logging in your account.";
    public static final String ALREADY_LOGGED_OUT = "This User is already logged out from the system.";
    public static final String LOGGED_OUT = "This User is logged out from the system.";
    public static final String INTERRUPTED_EXCEPTION = "Interrupted exception";

    //info types
    public static final String SHIPMENT_ID = "SHIPMENT_ID";
    public static final String USER_EMAIL = "USER_EMAIL";
    public static final String USER_PSW = "USER_PSW";
    public static final String DESTINATION_ADDRESS = "DESTINATION_ADDRESS";
    public static final String SHIPMENT_SERVICE = "SHIPMENT_SERVICE";
    public static final String ITEM_ID = "ITEM_ID";
    public static final String QUANTITY = "QUANTITY";
    public static final String INVALID_QUANTITY = "INVALID_QUANTITY";
    public static final String RECEIVER = "RECEIVER";
    public static final String SHIPMENTS = "SHIPMENTS";

    //unloged user id
    public static final String UNLOGGED_USER = "unlogged user";

    //store shipment info
    public static final String STORE_NAME = "Java Store";
    public static final String STORE_ADDRESS = "Viale Giovanni Battista Morgagni, 40, Firenze";

    //separator
    public static final String SEPARATOR = "--------------------------------------------------------------------";

    //general constants
    public static final String DELETE_ALL = "\033[H\033[2J";
    public static final String SUCCESSFULL_OPERATION_MESSAGE = "Operation successful with message: ";
    public static final String FAILED_OPERATION_MESSAGE = "Operation failed with message: ";

    //testing variables for main
    public static final String LUCA_NAME = "Luca";
    public static final String LUCA_SURNAME = "Maltempo";
    public static final String LUCA_FULL_NAME = "Luca Maltempo";
    public static final String LUCA_EMAIL = "luchino@pippo.com";
    public static final String LUCA_PASSWORD = "lucaPassowrd1";
    public static final String LUCA_ITEM = "06060";
    public static final String LUCA_QUANTITY = "1";
    public static final String LUCA_ADDRESS = "Indirizzo di Casa Luca";
    public static final String LUCA_SHIPMENT = "#000001";
    public static final String LUCA_NEW_ADDRESS_1 = "nuovoIndirizzo";
    public static final String LUCA_NEW_ADDRESS_2 = "indirizzoNuovo";
    public static final String SAM_NAME = "Samuele";
    public static final String SAM_SURNAME = "Ruotolo";
    public static final String SAM_FULL_NAME = "Samuele Ruotolo";
    public static final String SAM_EMAIL = "sam@pippo.com";
    public static final String SAM_PASSWORD = "samPassword1";
    public static final String SAM_ITEM = "01998";
    public static final String SAM_QUANTITY = "2";
    public static final String SAM_ADDRESS = "Indirizzo di Casa Sam";
    public static final String SAM_SHIPMENT = "#000002";
    public static final String PIE_NAME = "Pietro";
    public static final String PIE_SURNAME = "Siliani";
    public static final String PIE_FULL_NAME = "Pietro Siliani";
    public static final String PIE_EMAIL = "pie@pippo.com";
    public static final String PIE_PASSWORD = "piePassword1";
    public static final String PIE_ITEM = "06060";
    public static final String PIE_QUANTITY = "1";
    public static final String PIE_ADDRESS = "Indirizzo di Casa Pietro";
    public static final String PIE_SHIPMENT = "#000003";

    //ANSI
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK_UNDERLINED = "\033[4;30m";
    public static final String ANSI_BLACK_BOLD = "\033[1;30m";
    public static final String ANSI_RED = "\u001B[31m";

}