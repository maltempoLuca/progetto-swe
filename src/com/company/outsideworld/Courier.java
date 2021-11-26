package com.company.outsideworld;

import com.company.constants.Constants;
import com.company.constants.ShipmentState;
import com.company.store.ShipmentService;

import java.util.*;

public class Courier implements Runnable {
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
            shipmentService.updateShipmentState(); //DIVENTA SENT
            ShipmentState currentState = shipmentService.getShipment().getState();
            while (currentState.getNextState() != null) {
                Thread.sleep(generateRandom(shipmentTimesMap.get(currentState).minTime,
                        shipmentTimesMap.get(currentState).maxTime + tmpPriority * 500));
                shipmentService.updateShipmentState();
                currentState = shipmentService.getShipment().getState();
            }
            shipmentService = null;
            working = false;
            System.out.println("Ho finito.");
            // come gestire possibile interruzione del Thread  (caso di interrupt()) ?
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // come si fa a notificare l'avvenuta consegna??? esiste un listener dello stato di shipment service?
    }

    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    public boolean isWorking() {
        return working;
    }

    private int generateRandom(int min, int max) {
        return random.nextInt(max - min + 1) + min;
    }

    private record timeStruct(int minTime, int maxTime) {

    }

    private boolean working;
    private ShipmentService shipmentService;
    private final static Map<ShipmentState, timeStruct> shipmentTimesMap = Map.of(
            Constants.SENT, new timeStruct(500, 1500),
            Constants.IN_TRANSIT, new timeStruct(1000, 2000),
            Constants.OUT_FOR_DELIVERY, new timeStruct(500, 1000),
            Constants.CANCELLED, new timeStruct(100, 500)
    );
    private final static Random random = new Random();

}
