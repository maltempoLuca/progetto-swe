package com.company.store.shipping;

import com.company.constants.Constants;
import com.company.store.shipping.strategy.cancelbehavior.CancelDenier;
import com.company.store.shipping.strategy.returnbehavior.ReturnAllower;
import com.company.store.shipping.strategy.addressbehavior.UserAddressDenier;

public final class PremiumService extends ShipmentService {
    public PremiumService(Shipment shipment, String userEmail) {
        super(Constants.HIGH_PRIORITY, shipment, userEmail);
    }

    @Override
    void changeAddressBehavior() {
        if (getShipment().getState().equals(Constants.OUT_FOR_DELIVERY))
            setAddressBehavior(UserAddressDenier.getInstance());
        super.changeAddressBehavior();
    }

    @Override
    void changeCancelBehavior() {
        if (getShipment().getState().equals(Constants.OUT_FOR_DELIVERY))
            setCancelBehavior(CancelDenier.getInstance());
        super.changeCancelBehavior();
    }

    @Override
    void changeReturnBehavior() {
        if (getShipment().getState().equals(Constants.DELIVERED))
            setReturnBehavior(ReturnAllower.getInstance());
    }
}
