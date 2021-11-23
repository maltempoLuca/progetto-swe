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
        //params[1] = shipment
        //TODO: how to throw exception?
        String service = (String)params[0];
        Shipment shipment = (Shipment)params[1];
        if (service.equals(Constants.STANDARD))
            return new StandardService(shipment);
        if(service.equals(Constants.PREMIUM))
            return new PremiumService(shipment);
        if(service.equals(Constants.RETURN))
            return new ReturnService(shipment);
        return null;
    }

    private static ShipmentFactory instance = null;
}