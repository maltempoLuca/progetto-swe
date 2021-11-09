package com.company.constants;

public class ShipmentState {

    ShipmentState(String currentState, ShipmentState nextState) {
        this.currentState = currentState;
        this.nextState = nextState;
    }

    public ShipmentState getNextState() {
        return nextState;
    }

    public String getCurrentState() {
        return currentState;
    }

    private final String currentState;
    private final ShipmentState nextState;
}
