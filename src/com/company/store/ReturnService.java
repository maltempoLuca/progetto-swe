package com.company.store;

import com.company.constants.Constants;

public class ReturnService extends ShipmentService {

    public ReturnService(Shipment shipment, String userEmail) {
        super(Constants.LOW_PRIORITY, shipment, userEmail);
        shipment.setState(Constants.RETURN_CREATED); // prova di luca
        this.updateBehaviors();                       // prova di luca
    }

    @Override
    ShipmentService copy() {
        return new ReturnService(new Shipment(getShipment()), getUserEmail());
    }

    @Override
    void changeAddressBehavior() {
        if (getShipment().getState().equals(Constants.RETURN_CREATED))
            setAddressBehavior(InternalAddressChanger.getInstance());
    }

    @Override
    void changeCancelBehavior() {
        if (getShipment().getState().equals(Constants.RETURN_CREATED))
            setCancelBehavior(CancelReturn.getInstance());
    }

    @Override
    void changeReturnBehavior() {
        if (getShipment().getState().equals(Constants.RETURN_CREATED))
            setReturnBehavior(ReturnError.getInstance());
    }
}
