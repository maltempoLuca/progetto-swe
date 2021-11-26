package com.company.store;

import com.company.constants.Constants;
import com.company.constants.ShipmentState;

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
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            shipmentMutex.release();
        }
        changeAddressBehavior();
        changeCancelBehavior();
        changeReturnBehavior();
    }

    OperationResult changeAddress(String newAddress) {
        OperationResult operationResult= new OperationResult("Interrupted Exception", false);
        try {
            shipmentMutex.acquire();
            operationResult = addressBehavior.changeAddress(shipment, userEmail, newAddress);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            shipmentMutex.release();
        }
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
        return operationResult;
    }


    abstract void changeAddressBehavior();

    abstract void changeCancelBehavior();

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
