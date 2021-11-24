package com.company.store;

import com.company.constants.Constants;
import com.company.constants.ShipmentState;

public abstract class ShipmentService { //TODO: costruttore da mettere a package.

    ShipmentService(int priority, Shipment shipment) {
        this.priority = priority;
        this.shipment = shipment;
    }

    abstract ShipmentService copy();

    public void updateShipmentState() throws NullPointerException { //qui SEMAFORI
        if (shipment.getState().getNextState() == null)
            throw new NullPointerException("La spedizione è già stata consegnata");
        shipment.setState(shipment.getState().getNextState());
        changeAddressBehavior();
        changeCancelBehavior();
        changeReturnBehavior();
    }

    void changeAddress(String newAddress) { // qui
        if (addressBehavior.changeAddress(shipment, newAddress))
            shipment.setState(new ShipmentState(Constants.REQUEST_RECEIVED, shipment.getState()));
    }

    void createReturn() {
        returnBehavior.createReturn(shipment);
    } // qui

    void cancelShipment() {
        cancelBehavior.cancelShipment(shipment);
    } // qui

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

    public int getPriority() {
        return priority;
    }

    private final int priority;
    private final Shipment shipment;
    private AddressBehavior addressBehavior = UserAddressChanger.getInstance();
    private CancelBehavior cancelBehavior = CancelAllower.getInstance();
    private ReturnBehavior returnBehavior = ReturnDenier.getInstance();
}
