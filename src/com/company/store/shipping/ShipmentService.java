package com.company.store.shipping;

import com.company.constants.Constants;
import com.company.store.events.OperationResult;
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

import java.util.concurrent.Semaphore;

public abstract class ShipmentService { //TODO: costruttore da mettere a package.

    ShipmentService(int priority, Shipment shipment, String userEmail) {
        this.priority = priority;
        this.shipment = shipment;
        this.userEmail = userEmail;
    }

    abstract ShipmentService copy();

    public void updateShipmentState() {
        try {
            shipmentMutex.acquire();
            if (shipment.getState().getNextState() != null) {
                shipment.setState(shipment.getState().getNextState());
                ShipmentEvent shipmentEvent = new ShipmentEvent(ShipEventIdentifier.UPDATED, new Shipment(shipment), userEmail);
                ShipmentEventManager.getInstance().notify(shipmentEvent);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            shipmentMutex.release();
        }
        updateBehaviors();
    }

    public void updateBehaviors() {
        changeAddressBehavior();
        changeCancelBehavior();
        changeReturnBehavior();
    }

    OperationResult changeAddress(String newAddress) {
        OperationResult operationResult = new OperationResult("Interrupted Exception", false);
        try {
            shipmentMutex.acquire();
            operationResult = addressBehavior.changeAddress(shipment, userEmail, newAddress);
        } catch (InterruptedException e) {
            System.out.println("Eccezione pippo");
            e.printStackTrace();
        } finally {
            shipmentMutex.release();
        }
        if (operationResult.isSuccessful())
            updateBehaviors();
        return operationResult;
    }

    OperationResult createReturn() {
        OperationResult operationResult = new OperationResult("Interrupted Exception", false);
        try {
            shipmentMutex.acquire();
            operationResult = returnBehavior.createReturn(shipment, userEmail);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            shipmentMutex.release();
        }
        if (operationResult.isSuccessful())
            updateBehaviors();
        return operationResult;
    }

    OperationResult cancelShipment() {
        OperationResult operationResult = new OperationResult("Interrupted Exception", false);
        try {
            shipmentMutex.acquire();
            operationResult = cancelBehavior.cancelShipment(shipment, userEmail);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            shipmentMutex.release();
        }
        if (operationResult.isSuccessful())
            updateBehaviors();
        return operationResult;
    }


    void changeAddressBehavior() {
        if (getShipment().getState().getCurrentState().equals(Constants.REQUEST_RECEIVED) || getShipment().getState().equals(Constants.CANCELLED))
            setAddressBehavior(UserAddressDenier.getInstance());
        else if (getShipment().getState().getCurrentState().equals(Constants.ADDRESS_CHANGED)) {
            setAddressBehavior(UserAddressChanger.getInstance());
        }
    }

    void changeCancelBehavior() {
        if (getShipment().getState().equals(Constants.CANCELLED)) {
            setCancelBehavior(CancelDenier.getInstance());
        }
    }

    abstract void changeReturnBehavior();

    public Shipment getShipment() {
        try {
            shipmentMutex.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            shipmentMutex.release();
        }
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

    public String getUserEmail() {
        return userEmail;
    }

    private final int priority;
    private final Shipment shipment;
    private Semaphore shipmentMutex = new Semaphore(1);
    private final String userEmail;
    private AddressBehavior addressBehavior = UserAddressChanger.getInstance();
    private CancelBehavior cancelBehavior = CancelAllower.getInstance();
    private ReturnBehavior returnBehavior = ReturnDenier.getInstance();
}
