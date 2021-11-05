package com.company.store;

public class ReturnAllower implements ReturnBehavior {

    private ReturnAllower() {

    }

    public static ReturnAllower getInstance() {
        if (instance == null)
            instance = new ReturnAllower();
        return instance;
    }

    //TODO: implement method
    @Override
    public boolean createReturn(Shipment shipment) {
        String tempSender = shipment.getSender();
        return true;
    }

    private static ReturnAllower instance = null;
}
