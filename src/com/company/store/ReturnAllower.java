package com.company.store;

import com.company.constants.Constants;

public class ReturnAllower implements ReturnBehavior {

    private ReturnAllower() {

    }

    public static ReturnAllower getInstance() {
        if (instance == null)
            instance = new ReturnAllower();
        return instance;
    }

    @Override
    public boolean createReturn(Shipment shipment) {
        String tempSender = shipment.getSender();
        shipment.setSender(shipment.getReceiver());
        shipment.setReceiver(tempSender);

        String tempSenderAddress = shipment.getSenderAddress();
        shipment.setSenderAddress(shipment.getDestinationAddress());
        shipment.setDestinationAddress(tempSenderAddress);

        shipment.setState(Constants.RETURN_CREATED);
        return true;
    }

    private static ReturnAllower instance = null;
}
