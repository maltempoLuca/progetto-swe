package com.company;

import com.company.outsideworld.CourierAgency;
import com.company.store.*;
import com.company.store.controller.Controller;
import com.company.store.events.requests.RequestIdentifier;
import com.company.store.events.requests.RequestManager;
import com.company.store.events.shipments.ShipEventIdentifier;
import com.company.store.events.shipments.ShipmentEventManager;
import com.company.user.User;

import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        Buttons buttons = Buttons.getInstance();
        User luca = new User("Luca", "Maltempo", "luchino@pippo.com");
        User sam = new User("Samuele", "Ruotolo", "sam@pippo.com");
        User pie = new User("Pietro", "Siliani", "pie@pippo.com");

        // creo il controller e gli dico quali eventi deve ascoltare
        Controller controller = new Controller();
        RequestManager.getInstance().subscribe(controller, RequestIdentifier.REGISTER_REQUEST,
                RequestIdentifier.LOGIN_REQUEST, RequestIdentifier.LOGOUT_REQUEST);
        ShipmentEventManager.getInstance().subscribe(controller, ShipEventIdentifier.CANCELED,
                ShipEventIdentifier.CREATED, ShipEventIdentifier.UPDATED, ShipEventIdentifier.RETURNED);


        // provo a vedere se funzionano gli eventi
        //System.out.println("---------------------");
        // System.out.println("Sessione di Luca: ");
        buttons.logoutUser(luca.getEmail());
        Thread.sleep(3000);
        buttons.registerUser(luca.getEmail(), "lucaPassword1");
        Thread.sleep(3000);
        buttons.logoutUser(luca.getEmail());
        Thread.sleep(3000);
        buttons.loginUser(luca.getEmail(), "lucaPassword1");
        Thread.sleep(3000);
        buttons.logoutUser(luca.getEmail());
        Thread.sleep(3000);

        //System.out.println("---------------------");
        //System.out.println("Sessione di Sam: ");
        buttons.registerUser(sam.getEmail(), "samPassword");
        Thread.sleep(3000);
        buttons.registerUser(sam.getEmail(), "samPassword1");
        Thread.sleep(3000);
        buttons.loginUser(sam.getEmail(), "samPassword1");
        Thread.sleep(3000);
        buttons.logoutUser(sam.getEmail());
        Thread.sleep(3000);

        //System.out.println("---------------------");
        //System.out.println("Sessione di Pie: ");
        buttons.registerUser(pie.getEmail(), "piePassword1");
        Thread.sleep(3000);
        buttons.loginUser(pie.getEmail(), "piePassword0");
        Thread.sleep(3000);
        buttons.loginUser(pie.getEmail(), "piePassword1");
        Thread.sleep(3000);
        buttons.logoutUser(pie.getEmail());
        Thread.sleep(3000);


        //System.out.println("------------------------------------------------------------------------------------");
        //System.out.println("Inizio test thread:");


        CourierAgency courierAgency = new CourierAgency();
        Thread t = new Thread(courierAgency);
        t.start();
        List<String> users = new ArrayList<>();
        users.add("luchino@pippo.com");
        users.add("sam@pippo.com");
        users.add("pie@pippo.com");
        for (Integer i = 0; i < 22; i++) {
            Shipment shipment = new Shipment("a", "a", "", "", "", i.toString());
            ShipmentService shipmentService = new StandardService(shipment, users.get(i%3));
            courierAgency.requestCourier(shipmentService);
        }

        courierAgency.setProgramFinished(); // METTERE A TRUE SOLO QUANDO HAI FINITO TUTTI GLI ACQUISTI DA SIMULARE.
    }
}





