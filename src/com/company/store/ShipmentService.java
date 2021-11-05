package com.company.store;
import com.company.strategy.AddressBehavior;
import com.company.strategy.CancelBehavior;
import com.company.strategy.ReturnBehavior;

public abstract class ShipmentService {

    ShipmentService(int priority, Shipment shipment) {
        this.priority = priority;
        this.shipment = shipment;
    }

    public void updateShipmentState() {
        shipment.setState(shipment.getState().getNextState());
        changeAddressBehavior();
        changeCancelBehavior();
        changeReturnBehavior();
    }

    void changeAddress() {

    }

    void createReturn() {

    }

    void requestCourier() {

    }

    abstract void changeAddressBehavior();

    abstract void changeCancelBehavior();

    abstract void changeReturnBehavior();

    //TODO: implement method
    void cancelShipment() {

    }

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
