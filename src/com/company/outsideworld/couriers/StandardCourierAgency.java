package com.company.outsideworld.couriers;

import com.company.store.shipping.ShipmentService;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.Semaphore;


public final class StandardCourierAgency extends Thread implements CourierAgency {
    //Concrete courier agency

    public StandardCourierAgency() {
        for (int i = 0; i < 10; i++) {
            Courier courier = new Courier();
            couriers.add(courier);
            couriersThread.add(null);
        }
    }

    @Override
    public void run() {
        try {
            while (!programFinished || !emptyShipments()) {
                handleCouriers();
            }

            waitCouriers();

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void handleCouriers() throws InterruptedException {
        //check if there is a free courier to assign the shipment

        try {
            couriersWriters.acquire();
            for (int i = 0; i < couriers.size(); i++) {
                Courier currentCourier = couriers.get(i);
                if (!emptyShipments() && !currentCourier.isWorking()) {
                    try {
                        acquire_nShipmentServiceReaders();
                        currentCourier.assignShipmentService(shipmentServices.poll());
                    } finally {
                        release_nShipmentServiceReaders();
                    }
                    Thread courierThread = new Thread(currentCourier);
                    couriersThread.set(i, courierThread);
                    courierThread.start();
                }
            }
        } finally {
            couriersWriters.release();
        }
    }

    public void requestCourier(ShipmentService shipmentService) {
        //if the program isn't finished, add a shipmentService to the list of shipmentService of the agency

        try {
            shipmentServicesWriters.acquire();
            if (!programFinished)
                shipmentServices.add(shipmentService);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            shipmentServicesWriters.release();
        }
    }

    public void setProgramFinished() {
        programFinished = true;
    }

    public void waitCouriers() throws InterruptedException {
        try {
            acquire_nCouriers();
            for (Thread t : couriersThread)
                if (t != null)
                    t.join();
        } finally {
            release_nCouriers();
        }
    }

    private void acquire_nShipmentServiceReaders() throws InterruptedException {
        shipmentServicesReaders.acquire();
        nShipmentServiceReaders++;
        if (nShipmentServiceReaders == 1)
            shipmentServicesWriters.acquire();
        shipmentServicesReaders.release();
    }

    private void release_nShipmentServiceReaders() throws InterruptedException {
        shipmentServicesReaders.acquire();
        nShipmentServiceReaders--;
        if (nShipmentServiceReaders == 0)
            shipmentServicesWriters.release();
        shipmentServicesReaders.release();
    }

    private void acquire_nCouriers() throws InterruptedException {
        couriersReaders.acquire();
        nCouriersReaders++;
        if (nCouriersReaders == 1) {
            couriersWriters.acquire();
        }
        couriersReaders.release();
    }

    private void release_nCouriers() throws InterruptedException {
        couriersReaders.acquire();
        nCouriersReaders--;
        if (nCouriersReaders == 0) {
            couriersWriters.release();
        }
        couriersReaders.release();
    }

    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    private boolean emptyShipments() throws InterruptedException {
        boolean result;
        acquire_nShipmentServiceReaders();
        result = shipmentServices.isEmpty();
        release_nShipmentServiceReaders();
        return result;
    }

    private boolean programFinished = false;

    private final Semaphore shipmentServicesReaders = new Semaphore(1);
    private final Semaphore shipmentServicesWriters = new Semaphore(1);
    private int nShipmentServiceReaders = 0;

    private final Semaphore couriersReaders = new Semaphore(1);
    private final Semaphore couriersWriters = new Semaphore(1);
    private int nCouriersReaders = 0;

    private final ArrayList<Courier> couriers = new ArrayList<>();
    private final ArrayList<Thread> couriersThread = new ArrayList<>();
    private final Queue<ShipmentService> shipmentServices = new LinkedList<>();
}
