package com.company.store.factory;

import com.company.factory.Factory;
import com.company.constants.Constants;
import com.company.store.*;
import exceptions.ExceptionMessages;
import exceptions.InvalidParameterException;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ShipmentFactory {

    private ShipmentFactory() {

    }

    public static ShipmentFactory getInstance() {
        if (instance == null)
            instance = new ShipmentFactory();
        return instance;
    }

    public ShipmentService createService(Shipment shipment, String typeOfService, String userEmail) throws InvalidParameterException {
        ShipmentService shipmentService;
        checkParameters(shipment, userEmail, typeOfService);
        switch (typeOfService) {
            case Constants.STANDARD:
                shipmentService = new StandardService(shipment, userEmail);
                break;
            case Constants.PREMIUM:
                shipmentService = new PremiumService(shipment, userEmail);
                break;
            case Constants.RETURN:
                shipmentService = new ReturnService(shipment, userEmail);
                break;
            default:
                throw new InvalidParameterException(ExceptionMessages.SERVICE_INVALID_PARAMETER + typeOfService);
        }
        return shipmentService;
    }

    private void checkParameters(Object... parameters) throws InvalidParameterException {
        boolean valid = true;
        int index = 0;

        while (valid && index < parameters.length) {
            if (parameters[index] == null)
                valid = false;
            index++;
        }

        if(!valid)
            throw new InvalidParameterException(ExceptionMessages.NULL_INVALID_PARAMETER);

    }

    private static ShipmentFactory instance = null;
}