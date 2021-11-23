package com.company.outsideworld;

import com.company.store.ShipmentService;

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
        while (!programFinished) {
            try {
                handleCouriers();
                //sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("Ho spedito " + pacchiGestiti + " pacchi");
    }

    private void handleCouriers() throws InterruptedException {
        try {
            couriersWriters.acquire();
            try {
                acquire_nShipmentServiceReaders();
                for (int i = 0; i < couriers.size(); i++) {
                    Courier currentCourier = couriers.get(i);
                    if (!shipmentServices.isEmpty() && !currentCourier.isWorking()) {
                        currentCourier.assignShipmentService(shipmentServices.poll());
                        Thread courierThread = new Thread(currentCourier);
                        couriersThread.set(i, courierThread);
                        courierThread.start();
                        System.out.println("assegnato");
                        pacchiGestiti++;
                    }
                }
            } finally {
                release_nShipmentServiceReaders();
            }
        } finally {
            couriersWriters.release();
        }
    }

    public void requestCourier(ShipmentService shipmentService) throws InterruptedException {
        shipmentServicesWriters.acquire();
        try {
            if (!programFinished)
                shipmentServices.add(shipmentService);
        } finally {
            shipmentServicesWriters.release();
        }
    }

    public boolean isProgramFinished() {
        return programFinished;
    }

    public void setProgramFinished() throws InterruptedException {   //TODO:: fa busy waiting, è necessario?
        try {
            acquire_nShipmentServiceReaders();
            do {
                acquire_nCouriers();
                for (Thread t : couriersThread)
                    t.join();
                release_nCouriers();
            }
            while (!shipmentServices.isEmpty());
            programFinished = true;
        } finally {
            release_nShipmentServiceReaders();
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


    private static CourierAgency instance = null;
    private boolean programFinished = false;
    private static int pacchiGestiti = 0;

    Semaphore shipmentServicesReaders = new Semaphore(1);
    Semaphore shipmentServicesWriters = new Semaphore(1);
    private int nShipmentServiceReaders = 0;

    Semaphore couriersReaders = new Semaphore(1);
    Semaphore couriersWriters = new Semaphore(1);
    private int nCouriersReaders = 0;

    private final ArrayList<Courier> couriers = new ArrayList<>();
    private final ArrayList<Thread> couriersThread = new ArrayList<>();
    private final Queue<ShipmentService> shipmentServices = new LinkedList<>();//TODO: adesso se cancello un ordine devo cancellarlo pure da qui.
    private final LinkedList<ShipmentService> test = new LinkedList<>();
}
