package com.company.store;
import com.company.constants.Constants;
import com.company.strategy.AddressBehavior;
import java.util.List;

public abstract class ShipmentService {

    ShipmentService(int priority, Shipment shipment) {
        this.priority = priority;
        this.shipment = shipment;
    }

    public void updateShipmentState() {

    }

    void changeAddress() {

    }

    void createReturn() {

    }

    void requestCourier() {

    }

    void setAddressBehavior() {
        //TODO: define all cases or change idea
        if (shipment.getState().equals(Constants.INITIAL_STATE))
            addressBehavior = UserAddressDenier.getInstance();
        if (shipment.getState().equals(Constants.ON_GOING_STATE))
            addressBehavior = UserAddressChanger.getInstance();
    }

    private final int priority;
    private final Shipment shipment;
    private AddressBehavior addressBehavior;
}
