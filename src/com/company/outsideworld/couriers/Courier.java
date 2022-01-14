package com.company.outsideworld.couriers;

import com.company.constants.Constants;
import com.company.store.shipping.ShipmentState;
import com.company.store.shipping.ShipmentService;

import java.util.*;

public final class Courier implements Runnable {
    //This class is a simulation for the couriers that deliver shipment

    public Courier() {
        working = false;
    }

    public void assignShipmentService(ShipmentService shipmentService) {
        this.shipmentService = shipmentService;
        this.working = true;
    }

    @Override
    public void run() {
        int tmpPriority = shipmentService.getPriority();
        try {
            ShipmentState currentState = shipmentService.getShipment().getState();
            while (currentState.getNextState() != null) {
                Thread.sleep(generateRandom(shipmentTimesMap.get(currentState.getCurrentState()).minTime,
                        shipmentTimesMap.get(currentState.getCurrentState()).maxTime + tmpPriority * 500));
                shipmentService.updateShipmentState();
                currentState = shipmentService.getShipment().getState();
            }
            shipmentService = null;
            working = false;
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    public boolean isWorking() {
        return working;
    }

    private int generateRandom(int min, int max) {
        return random.nextInt(max - min + 1) + min;
    }

    //TODO: delete this?
    private record timeStruct(int minTime, int maxTime) {

    }

    private boolean working;
    private ShipmentService shipmentService;
    private final static Map<String, timeStruct> shipmentTimesMap = Map.of(
            Constants.CREATED.getCurrentState(), new timeStruct(1000, 2000),
            Constants.SENT.getCurrentState(), new timeStruct(2000, 3000),
            Constants.IN_TRANSIT.getCurrentState(), new timeStruct(3000, 5000),
            Constants.OUT_FOR_DELIVERY.getCurrentState(), new timeStruct(2000, 3000),
            Constants.CANCELLED.getCurrentState(), new timeStruct(100, 500),

            Constants.RETURN_CREATED.getCurrentState(), new timeStruct(1000, 3000),
            Constants.PICKED_UP.getCurrentState(), new timeStruct(3000, 5000),
            Constants.RETURN_DELIVERED.getCurrentState(), new timeStruct(100, 500),

            Constants.REQUEST_RECEIVED, new timeStruct(100, 500),
            Constants.ADDRESS_CHANGED, new timeStruct(100, 500)
    );
    private final static Random random = new Random();
}
