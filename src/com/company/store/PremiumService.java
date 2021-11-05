package com.company.store;
import com.company.constants.Constants;

public class PremiumService extends ShipmentService {
    public PremiumService(Shipment shipment) {
        super(Constants.HIGH_PRIORITY, shipment);
    }

    @Override
    void changeAddressBehavior() {
        if (getShipment().getState() == Constants.IN_DELIVERY)
            setAddressBehavior(UserAddressDenier.getInstance());
    }

    @Override
    void changeCancelBehavior() {
        if (getShipment().getState() == Constants.IN_DELIVERY)
            setCancelBehavior(CancelDenier.getInstance());
    }

    @Override
    void changeReturnBehavior() {
        if (getShipment().getState() == Constants.DELIVERED)
            setReturnBehavior(ReturnAllower.getInstance());
    }
}
