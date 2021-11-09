package com.company.constants;

public class Constants {

    //shipment priorities
    public static final int HIGH_PRIORITY = 1;
    public static final int LOW_PRIORITY = 2;

    //shipment states
    public static final ShipmentState DELIVERED = new ShipmentState("Delivered", null);
    public static final ShipmentState IN_DELIVERY = new ShipmentState("In delivery", DELIVERED);
    public static final ShipmentState IN_TRANSIT = new ShipmentState("In transit", IN_DELIVERY);
    public static final ShipmentState SENT = new ShipmentState("Sent", IN_TRANSIT);
    public static final ShipmentState CREATED = new ShipmentState("Created", SENT);

    //return states
    public static final ShipmentState RETURN_DELIVERED = new ShipmentState("Return delivered", null);
    public static final ShipmentState PICKED_UP = new ShipmentState("Picked up", RETURN_DELIVERED);
    public static final ShipmentState RETURN_CREATED = new ShipmentState("Return created", PICKED_UP);

    //change address states
    public static final ShipmentState ADDRESS_UPDATED = new ShipmentState("Address updated", SENT);
    public static final ShipmentState REQUEST_RECEIVED = new ShipmentState("Request received", ADDRESS_UPDATED);


    //type of services
    public static final String STANDARD = "Standard";
    public static final String PREMIUM = "Premium";

    //type of errors
    public static final String SUCCESS = "SUCCESS";
    public static final String SHORT_PSW = "Password has to be at least 6 characters long.";
    public static final String ONLY_LETTERS_PSW = "The password must contains letters.";
    public static final String ONLY_NUMBERS_PSW = "The password must contain numbers.";
    public static final String WRONG_PSW = "Wrong Password, try again.";
    public static final String WRONG_EMAIL = "This email doesn't correspond to any user." + "\n"
            + "Please enter the email address used during the set up of your account.";
    public static final String INVALID_EMAIL = "The email must contain '@'.";
    public static final String EMAIL_ALREADY_USED = "The email you have entered is already in use, try logging in your account.";

    //button texts
    public static final String BACK_TEXT = "BACK";
    public static final String TO_CATALOG_TEXT = "See Catalog";
    public static final String TO_LOGIN_NEXT = "Login";
    public static final String TO_REGISTER_NEXT = "Register";

    //view top texts
    public static final String HOME_TOP_TEXT = "Welcome!\n This is the Home";
    public static final String CATLOG_TOP_TEXT = "Here's the Catalog:";
    public static final String REGISTER_TOP_TEXT = "Follow the instruction for signing in :D";
    public static final String LOGIN_TOP_TEXT = "Follow the instruction for logging in :D";

}