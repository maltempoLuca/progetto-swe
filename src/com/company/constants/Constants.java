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

    //button texts
    public static final String BACK_TEXT = "BACK";
    public static final String TO_CATALOG_TEXT = "See Catalog";

    //view top texts
    public static final String HOME_TOP_TEXT = "Welcome!\n This is the Home";
    public static final String CATLOG_TOP_TEXT = "Here's the Catalog:";
}