package com.company.constants;

public class Constants {
    //TODO: make class uninstantiable (add private default constructor)

    //shipment priorities
    public static final int HIGH_PRIORITY = 1;
    public static final int LOW_PRIORITY = 2;

    //state names
    public static final String REQUEST_RECEIVED = "Request received";

    //shipment states
    public static final ShipmentState DELIVERED = new ShipmentState("Delivered", null);
    public static final ShipmentState OUT_FOR_DELIVERY = new ShipmentState("Out for delivery", DELIVERED);
    public static final ShipmentState IN_TRANSIT = new ShipmentState("In transit", OUT_FOR_DELIVERY);
    public static final ShipmentState SENT = new ShipmentState("Sent", IN_TRANSIT);
    public static final ShipmentState CREATED = new ShipmentState("Created", SENT);

    //return states
    public static final ShipmentState RETURN_DELIVERED = new ShipmentState("Return delivered", null);
    public static final ShipmentState PICKED_UP = new ShipmentState("Picked up", RETURN_DELIVERED);
    public static final ShipmentState RETURN_CREATED = new ShipmentState("Return created", PICKED_UP);

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

    //info types
    public static final String USEREMAIL = "USEREMAIL";
    public static final String DESTINATION_ADDRESS = "DESTINATION_ADDRESS";
    public static final String SHIPMENT_SERVICE = "SHIPMENT_SERVICE";
    public static final String CONTENTS = "CONTENTS";
    public static final String PRICE = "PRICE";

}