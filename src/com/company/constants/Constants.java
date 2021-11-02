package com.company.constants;
import java.util.LinkedList;
import java.util.List;

public class Constants {

    //shipment priorities
    public static final int HIGH_PRIORITY = 1;
    public static final int LOW_PRIORITY = 2;

    //shipment states
    //TODO: define all state
    public static final String INITIAL_STATE = "Shipment created";
    public static final String ON_GOING_STATE = "On going";
    public static final List<String> states = List.of(INITIAL_STATE, "On going");

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