package com.company;

import com.company.constants.Constants;
import com.company.outsideworld.CourierAgency;
import com.company.store.*;
import com.company.store.controller.Controller;
import com.company.store.eventsys.events.EventBuilder;
import com.company.store.eventsys.events.EventIdentifier;
import com.company.store.eventsys.events.StoreEvent;
import com.company.store.eventsys.management.StoreEventManager;
import com.company.user.User;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        Store store = Store.getInstance();
        User luca = new User("Luca", "Maltempo", "luchino@pippo.com");
        User sam = new User("Samuele", "Ruotolo", "sam@pippo.com");
        User pie = new User("Pietro", "Siliani", "pie@pippo.com");

        // creo il controller e gli dico quali eventi deve ascoltare
        Controller controller = new Controller();
        StoreEventManager.getInstance().subscribe(controller, EventIdentifier.REGISTER_REQUEST,
                EventIdentifier.LOGIN_REQUEST, EventIdentifier.LOGOUT_REQUEST, EventIdentifier.LOGIN_ACCEPTED,
                EventIdentifier.LOGIN_REFUSED, EventIdentifier.REGISTRATION_ACCEPTED, EventIdentifier.REGISTRATION_REFUSED,
                EventIdentifier.LOGOUT_ACCEPTED, EventIdentifier.LOGOUT_REFUSED);


        // provo a vedere se funzionano gli eventi
        System.out.println("---------------------");
        System.out.println("Sessione di Luca: ");
        store.logoutUser(luca.getEmail());
        store.registerUser(luca.getEmail(), "lucaPassword1");
        store.logoutUser(luca.getEmail());
        store.loginUser(luca.getEmail(), "lucaPassword1");
        store.logoutUser(luca.getEmail());

        System.out.println("---------------------");
        System.out.println("Sessione di Sam: ");
        store.registerUser(sam.getEmail(), "samPassword");
        store.registerUser(sam.getEmail(), "samPassword1");
        store.loginUser(sam.getEmail(), "samPassword1");
        store.logoutUser(sam.getEmail());


        System.out.println("---------------------");
        System.out.println("Sessione di Pie: ");
        store.registerUser(pie.getEmail(), "piePassword1");
        store.loginUser(pie.getEmail(), "piePassword0");
        store.loginUser(pie.getEmail(), "piePassword1");
        store.logoutUser(pie.getEmail());


        System.out.println("------------------------------------------------------------------------------------");
        System.out.println("Inizio test thread:");


        CourierAgency courierAgency = CourierAgency.getInstance();
        Thread t = new Thread(courierAgency);
        t.start();
        for (int i = 0; i < 22; i++) {
            Shipment shipment = new Shipment("a", "a", "", "", "", "i");
            ShipmentService shipmentService = new StandardService(shipment);
            courierAgency.requestCourier(shipmentService);
        }

        courierAgency.setProgramFinished(); // METTERE A TRUE SOLO QUANDO HAI FINITO TUTTI GLI ACQUISTI DA SIMULARE.
    }
}





