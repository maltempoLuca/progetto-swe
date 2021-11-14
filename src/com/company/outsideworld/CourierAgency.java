package com.company.outsideworld;

import com.company.store.ShipmentService;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.Semaphore;

//requestCourier() dovrebbe averlo shipping department secondo me.
public class CourierAgency extends Thread {

    private CourierAgency() {
        for (int i = 0; i < 10; i++)
            couriers.add(new Courier());
    }

    public static CourierAgency getInstance() {
        if (instance == null)
            instance = new CourierAgency();
        return instance;
    }

    @Override
    public void run() {
        int interrompiThread = 10;
        while (interrompiThread > 0) {
            try {
                handleCouriers();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            interrompiThread--;
        }
        System.out.println("Ho spedito " + pacchiGestiti + " pacchi");
    }

    private void handleCouriers() throws InterruptedException {
        mutexShipmentServices.acquire();
        try {
            while (!shipmentServices.isEmpty()) {
                mutexCouriers.acquire();
                try {
                    for (Courier c : couriers) {
                        if (!c.isWorking()) {
                            c.assignShipmentService(shipmentServices.poll()); // accedi a shippmentServices tramite semaforo!
                            pacchiGestiti++;
                            Thread t = new Thread(c);
                            t.start();
                        }
                    }
                } finally {
                    mutexCouriers.release();
                }
            }
        } finally {
            mutexShipmentServices.release();
        }
    }

    public Boolean isAnAvailableCourier() throws InterruptedException {
        mutexCouriers.acquire();
        try {
            for (Courier c : couriers) {
                if (!c.isWorking()) {
                    mutexShipmentServices.acquire();
                    try {
                        if (shipmentServices.isEmpty()) return true;  // accedi a shippmentServices tramite semaforo!
                    } finally {
                        mutexShipmentServices.release();
                    }
                }
            }
            return false;
        } finally {
            mutexCouriers.release();
        }
    }

    public void requestCourier(ShipmentService shipmentService) throws InterruptedException {
        mutexShipmentServices.acquire();
        try {
            shipmentServices.add(shipmentService);
        } finally {
            mutexShipmentServices.release();
        }
    }

    private static CourierAgency instance = null;
    private static int pacchiGestiti = 0;
    Semaphore mutexCouriers = new Semaphore(1);
    Semaphore mutexShipmentServices = new Semaphore(1);
    private final ArrayList<Courier> couriers = new ArrayList<>();
    private final Queue<ShipmentService> shipmentServices = new LinkedList<>(); // adesso se cancello un ordine lo
    // devo cancellare pure da qui, bella merda.
}
