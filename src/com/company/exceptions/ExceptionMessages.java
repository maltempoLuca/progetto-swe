package com.company.exceptions;

public final class ExceptionMessages {
    //Messages used for some new exceptions
    
    //Invalid parameter messages
    public static final String NULL_INVALID_PARAMETER = "Null parameter for ShipmentService creation";
    public static final String SERVICE_INVALID_PARAMETER = "Invalid type of service for ShipmentService creation: ";

    //Missing input messages
    public static final String KEY_MISSING_INPUT = "Missing input of type: ";
    public static final String IN_REQUEST_MISSING_INPUT = " in request ";

    //Unregistered user messages
    public static final String UNREG_USER = "no such user found";

    //Missing agency messages
    public static final String MISSING_AGENCY = "No agency interface has been set";

    //Missing department
    public static final String MISSING_DEPARTMENT = "At least one department is null, Store not properly initialized";
}
