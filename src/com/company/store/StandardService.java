package com.company.store;
import com.company.constants.Constants;

public class StandardService extends ShipmentService {

    public StandardService(Shipment shipment) {
        super(Constants.LOW_PRIORITY, shipment);
    } //METTI PACKAGE

    @Override
    void changeAddressBehavior() {
        if (getShipment().getState() == Constants.SENT)
            setAddressBehavior(UserAddressDenier.getInstance());
    }

    @Override
    void changeCancelBehavior() {
        if (getShipment().getState() == Constants.SENT)
            setCancelBehavior(CancelDenier.getInstance());
    }

    @Override
    void changeReturnBehavior() {
        if (getShipment().getState() == Constants.DELIVERED)
            setReturnBehavior(ReturnAllower.getInstance());
    }
}
