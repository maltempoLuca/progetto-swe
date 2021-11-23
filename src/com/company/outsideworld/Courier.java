package com.company.outsideworld;

import com.company.store.ShipmentService;

import java.util.Random;

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
            shipmentService.updateShipmentState(); // adesso è SENT
            Thread.sleep(generateRandom(500, 1500 + tmpPriority * 500));
            shipmentService.updateShipmentState(); // adesso è IN_TRANSIT
            Thread.sleep(generateRandom(1000, 2000 + tmpPriority * 500));
            shipmentService.updateShipmentState();  // adesso è OUT_FOR_DELIVERY
            Thread.sleep(generateRandom(500, 1000 + tmpPriority * 500));
            shipmentService.updateShipmentState();  // adesso è DELIVERED
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

    private boolean working;
    private ShipmentService shipmentService;
    private final static Random random = new Random();
}
