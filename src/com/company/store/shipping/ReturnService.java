package com.company.store.shipping;

import com.company.constants.Constants;
import com.company.store.shipping.strategy.addressbehavior.InternalAddressChanger;
import com.company.store.shipping.strategy.returnbehavior.DoubleReturnBehavior;
import com.company.store.shipping.strategy.cancelbehavior.CancelReturn;

public final class ReturnService extends ShipmentService {

    public ReturnService(Shipment shipment, String userEmail) {
        super(Constants.LOW_PRIORITY, shipment, userEmail);
        shipment.setState(Constants.RETURN_CREATED);
        this.updateBehaviors();
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
            setReturnBehavior(DoubleReturnBehavior.getInstance());
    }
}
