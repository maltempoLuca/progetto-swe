package com.company.store.shipping;

import com.company.constants.Constants;
import com.company.store.shipping.strategy.addressbehavior.UserAddressDenier;
import com.company.store.shipping.strategy.cancelbehavior.CancelDenier;
import com.company.store.shipping.strategy.returnbehavior.ReturnAllower;

public final class StandardService extends ShipmentService {

    public StandardService(Shipment shipment, String userEmail) {
        super(Constants.LOW_PRIORITY, shipment, userEmail);
    }

    @Override
    void changeAddressBehavior() {
        if (getShipment().getState().equals(Constants.SENT))
            setAddressBehavior(UserAddressDenier.getInstance());
        super.changeAddressBehavior();
    }

    @Override
    void changeCancelBehavior() {
        if (getShipment().getState().equals(Constants.SENT))
            setCancelBehavior(CancelDenier.getInstance());

        super.changeCancelBehavior();
    }

    @Override
    void changeReturnBehavior() {
        if (getShipment().getState().equals(Constants.DELIVERED)) {
            setReturnBehavior(ReturnAllower.getInstance());
        }
    }
}
