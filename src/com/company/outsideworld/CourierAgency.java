package com.company.outsideworld;

import com.company.store.ShipmentService;
import com.company.store.purchase.PurchasingDepartment;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.Semaphore;


public class CourierAgency extends Thread {
    private CourierAgency() {
        for (int i = 0; i < 10; i++) {
            Courier courier = new Courier();
            couriers.add(courier);
            couriersThread.add(null);
        }
    }

    public static CourierAgency getInstance() {
        if (instance == null)
            instance = new CourierAgency();
        return instance;
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

        System.out.println("Ho spedito " + pacchiGestiti + " pacchi");
    }


    private void handleCouriers() throws InterruptedException {
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
                    System.out.println("assegnato");
                    pacchiGestiti++;
                }
            }
        } finally {
            couriersWriters.release();
        }
    }

    public void requestCourier(ShipmentService shipmentService) {
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

    public boolean isProgramFinished() {
        return programFinished;
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

    public Boolean isAnAvailableCourier() throws InterruptedException {
        boolean courierAvailable = false;
        try {
            acquire_nCouriers();
            try {
                acquire_nShipmentServiceReaders();
                for (Courier c : couriers)
                    if (!c.isWorking())
                        if (shipmentServices.isEmpty())
                            courierAvailable = true;
            } finally {
                release_nShipmentServiceReaders();
            }
        } finally {
            release_nCouriers();
        }
        return courierAvailable;
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

    private boolean emptyShipments() throws InterruptedException {
        boolean result;
        acquire_nShipmentServiceReaders();
        result = shipmentServices.isEmpty();
        release_nShipmentServiceReaders();
        return result;
    }

    private boolean programFinished = false;
    private static int pacchiGestiti = 0;

    private Semaphore shipmentServicesReaders = new Semaphore(1);
    private Semaphore shipmentServicesWriters = new Semaphore(1);
    private int nShipmentServiceReaders = 0;

    private Semaphore couriersReaders = new Semaphore(1);
    private Semaphore couriersWriters = new Semaphore(1);
    private int nCouriersReaders = 0;

    private static CourierAgency instance = null;
    private final ArrayList<Courier> couriers = new ArrayList<>();
    private final ArrayList<Thread> couriersThread = new ArrayList<>();
    private final Queue<ShipmentService> shipmentServices = new LinkedList<>();//TODO: adesso se cancello un ordine devo cancellarlo pure da qui.
    private final LinkedList<ShipmentService> test = new LinkedList<>();
}
