package com.company.store;
import com.company.factory.Factory;
import com.company.constants.Constants;

public class ShipmentFactory implements Factory {
    //TODO: classes that receive an object built this way should make defensive copies and then store the object?!

    private ShipmentFactory() {

    }

    public static ShipmentFactory getInstance() {
        if (instance == null)
            instance = new ShipmentFactory();
        return instance;
    }

    @Override
    public ShipmentService factoryMethod(Object ... params) {
        //params[0] = type of service
        //params[1] = user email
        //params[2] = shipment
        //TODO: how to throw exception?
        String service = (String)params[0];
        String userEmail = (String)params[1];
        Shipment shipment = (Shipment)params[2];
        if (service.equals(Constants.STANDARD))
            return new StandardService(shipment, userEmail);
        if(service.equals(Constants.PREMIUM))
            return new PremiumService(shipment, userEmail);
        if(service.equals(Constants.RETURN))
            return new ReturnService(shipment, userEmail);
        return null;
    }

    private static ShipmentFactory instance = null;
}