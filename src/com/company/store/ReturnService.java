package com.company.store;

import com.company.constants.Constants;

public class ReturnService extends ShipmentService {

    ReturnService(Shipment shipment) {
        super(Constants.LOW_PRIORITY, shipment);
    }

    @Override
    ShipmentService copy() {
        return new ReturnService(new Shipment(super.getShipment()));
    }

    @Override
    void changeAddressBehavior() {
        if (getShipment().getState() == Constants.RETURN_CREATED)
            setAddressBehavior(InternalAddressChanger.getInstance());
    }

    @Override
    void changeCancelBehavior() {
        if (getShipment().getState() == Constants.RETURN_CREATED)
            setCancelBehavior(CancelReturn.getInstance());
    }

    @Override
    void changeReturnBehavior() {
        if (getShipment().getState() == Constants.RETURN_CREATED)
            setReturnBehavior(ReturnError.getInstance());
    }
}
