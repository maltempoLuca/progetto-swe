package com.company.store;

import com.company.constants.Constants;
import com.company.constants.ShipmentState;

public abstract class ShipmentService {

    ShipmentService(int priority, Shipment shipment) {
        this.priority = priority;
        this.shipment = shipment;
    }

    public void updateShipmentState() throws NullPointerException {
        if (shipment.getState() == null)
            throw new NullPointerException("La spedizione è già stata consegnata");
        shipment.setState(shipment.getState().getNextState());
        changeAddressBehavior();
        changeCancelBehavior();
        changeReturnBehavior();
    }

    void changeAddress(String newAddress) {
        if (addressBehavior.changeAddress(shipment, newAddress))
            shipment.setState(new ShipmentState(Constants.REQUEST_RECEIVED, shipment.getState()));
    }

    void createReturn() {
        returnBehavior.createReturn(shipment);
    }

    void cancelShipment() {
        cancelBehavior.cancelShipment(shipment);
    }

    void requestCourier() {

    }

    abstract void changeAddressBehavior();

    abstract void changeCancelBehavior();

    abstract void changeReturnBehavior();

    public Shipment getShipment() {
        return shipment;
    }

    public void setAddressBehavior(AddressBehavior addressBehavior) {
        this.addressBehavior = addressBehavior;
    }

    public AddressBehavior getAddressBehavior() {
        return addressBehavior;
    }

    public void setCancelBehavior(CancelBehavior cancelBehavior) {
        this.cancelBehavior = cancelBehavior;
    }

    public CancelBehavior getCancelBehavior() {
        return cancelBehavior;
    }

    public ReturnBehavior getReturnBehavior() {
        return returnBehavior;
    }

    public void setReturnBehavior(ReturnBehavior returnBehavior) {
        this.returnBehavior = returnBehavior;
    }

    private final int priority;
    private final Shipment shipment;
    private AddressBehavior addressBehavior = UserAddressChanger.getInstance();
    private CancelBehavior cancelBehavior = CancelAllower.getInstance();
    private ReturnBehavior returnBehavior = ReturnDenier.getInstance();
}
