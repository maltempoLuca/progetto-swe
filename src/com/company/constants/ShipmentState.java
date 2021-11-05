package com.company.constants;

public class ShipmentState {

    ShipmentState(String currentState, ShipmentState nextState) {
        this.currentState = currentState;
        this.nextState = nextState;
    }

    public ShipmentState getNextState() {
        return nextState;
    }

    private static String currentState;
    private ShipmentState nextState;
}
