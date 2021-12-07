package com.company.store.shipping;

public class ShipmentState {

    public ShipmentState(String currentState, ShipmentState nextState) {
        this.currentState = currentState;
        this.nextState = nextState;
    }

    public ShipmentState getNextState() {
        return nextState;
    }

    public String getCurrentState() {
        return currentState;
    }

    public boolean equals(ShipmentState other) {

        return this.currentState.equals(other.currentState);
    }

    private final String currentState;
    private final ShipmentState nextState;
}
