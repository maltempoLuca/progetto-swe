package com.company.store;

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

    void changeAddress(String newAddress) throws ChangeAddressException {
        addressBehavior.changeAddress(shipment, newAddress);
    }

    void createReturn() throws ReturnException {
        returnBehavior.createReturn(shipment);
    }

    void cancelShipment() throws CancelDenierException{
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
