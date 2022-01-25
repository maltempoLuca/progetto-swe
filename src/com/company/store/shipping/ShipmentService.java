package com.company.store.shipping;

import com.company.constants.Constants;
import com.company.store.OperationResult;
import com.company.store.shipping.strategy.addressbehavior.UserAddressChanger;
import com.company.store.shipping.strategy.addressbehavior.AddressBehavior;
import com.company.store.shipping.strategy.addressbehavior.UserAddressDenier;
import com.company.store.shipping.strategy.cancelbehavior.CancelAllower;
import com.company.store.shipping.strategy.cancelbehavior.CancelBehavior;
import com.company.store.shipping.strategy.cancelbehavior.CancelDenier;
import com.company.store.events.shipmentevents.ShipEventIdentifier;
import com.company.store.events.shipmentevents.ShipmentEvent;
import com.company.store.events.shipmentevents.ShipmentEventManager;
import com.company.store.shipping.strategy.returnbehavior.ReturnBehavior;
import com.company.store.shipping.strategy.returnbehavior.ReturnDenier;

public abstract class ShipmentService {

    ShipmentService(int priority, Shipment shipment, String userEmail) {
        this.priority = priority;
        this.shipment = shipment;
        this.userEmail = userEmail;
    }

    public final synchronized void updateShipmentState() {
        //if current shipment state has a successor change state to successor
        //fire an event with UPDATED id and a deep copy of shipment
        //update service Strategies

        if (shipment.getState().getNextState() != null) {
            shipment.setState(shipment.getState().getNextState());
            ShipmentEvent shipmentEvent = new ShipmentEvent(ShipEventIdentifier.UPDATED, new Shipment(shipment), userEmail);
            ShipmentEventManager.getInstance().notify(shipmentEvent);
            updateBehaviors();
        }
    }

    synchronized final void updateBehaviors() {
        changeAddressBehavior();
        changeCancelBehavior();
        changeReturnBehavior();
    }

    synchronized final OperationResult changeAddress(String newAddress) {
        OperationResult operationResult = addressBehavior.changeAddress(shipment, userEmail, newAddress);
        if (operationResult.isSuccessful())
            updateBehaviors();
        return operationResult;
    }

    synchronized final OperationResult createReturn() {
        OperationResult operationResult = returnBehavior.createReturn(shipment, userEmail);
        if (operationResult.isSuccessful())
            updateBehaviors();
        return operationResult;
    }

    synchronized final OperationResult cancelShipment() {
        OperationResult operationResult = cancelBehavior.cancelShipment(shipment, userEmail);
        if (operationResult.isSuccessful())
            updateBehaviors();
        return operationResult;
    }

    final void changeAddressBehaviorDefault() {
        //destination address cannot be changed if another address change request is yet to be notified to the courier
        //once the courier has been notified of the request a new request may be submitted
        //destination address cannot be changed if shipment is cancelled

        if (getShipment().getState().getCurrentState().equals(Constants.REQUEST_RECEIVED)
                || getShipment().getState().equals(Constants.CANCELLED))
            setAddressBehavior(UserAddressDenier.getInstance());
        else if (getShipment().getState().getCurrentState().equals(Constants.ADDRESS_CHANGED)) {
            setAddressBehavior(UserAddressChanger.getInstance());
        }
    }

    final void changeCancelBehaviorDefault() {
        //cannot cancel an already canceled shipment

        if (getShipment().getState().equals(Constants.CANCELLED)) {
            setCancelBehavior(CancelDenier.getInstance());
        }
    }

    final void changeReturnBehaviorDefault() {
        //cannot return when return request has already been confirmed

        if (getShipment().getState().equals(Constants.RETURN_CONFIRMED)) {
            setReturnBehavior(ReturnDenier.getInstance());
        }
    }

    abstract void changeAddressBehavior();

    abstract void changeCancelBehavior();

    abstract void changeReturnBehavior();

    public synchronized final Shipment getShipment() {
        return shipment;
    }

    public final void setAddressBehavior(AddressBehavior addressBehavior) {
        this.addressBehavior = addressBehavior;
    }

    public final void setCancelBehavior(CancelBehavior cancelBehavior) {
        this.cancelBehavior = cancelBehavior;
    }

    public final void setReturnBehavior(ReturnBehavior returnBehavior) {
        this.returnBehavior = returnBehavior;
    }

    public final int getPriority() {
        return priority;
    }

    public final String getUserEmail() {
        return userEmail;
    }

    private final int priority;
    private final Shipment shipment;
    private final String userEmail;
    private AddressBehavior addressBehavior = UserAddressChanger.getInstance();
    private CancelBehavior cancelBehavior = CancelAllower.getInstance();
    private ReturnBehavior returnBehavior = ReturnDenier.getInstance();
}
