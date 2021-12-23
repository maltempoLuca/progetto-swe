package com.company;

import com.company.constants.Constants;
import com.company.outsideworld.couriers.StandardCourierAgency;
import com.company.store.Buttons;
import com.company.store.shipping.ShippingDepartment;
import com.company.store.controller.Controller;
import com.company.store.controller.RequestIdentifier;
import com.company.store.events.requestevents.RequestManager;
import com.company.store.events.shipmentevents.ShipEventIdentifier;
import com.company.store.events.shipmentevents.ShipmentEventManager;
import com.company.store.events.viewevents.ViewEventIdentifier;
import com.company.store.events.viewevents.ViewEventManager;
import com.company.outsideworld.users.User;

public class Main { // final?
    //TODO: metti final in tutte classi e metodi

    public static void main(String[] args) throws InterruptedException {
        StandardCourierAgency courierAgency = new StandardCourierAgency();
        courierAgency.start();
        Buttons buttons = Buttons.getInstance();
        ShippingDepartment.getInstance().setCourierAgency(courierAgency);
        User luca = new User("Luca", "Maltempo", "luchino@pippo.com");
        User sam = new User("Samuele", "Ruotolo", "sam@pippo.com");
        User pie = new User("Pietro", "Siliani", "pie@pippo.com");

        // creo il controller e gli dico quali eventi deve ascoltare
        Controller controller = new Controller();
        RequestManager.getInstance().subscribe(controller, RequestIdentifier.REGISTER_REQUEST,
                RequestIdentifier.LOGIN_REQUEST, RequestIdentifier.LOGOUT_REQUEST,
                RequestIdentifier.ADD_TO_CART_REQUEST, RequestIdentifier.CANCEL_REQUEST, RequestIdentifier.PURCHASE_REQUEST,
                RequestIdentifier.CHANGE_ADDRESS_REQUEST, RequestIdentifier.RETURN_REQUEST, RequestIdentifier.VIEW_CATALOGUE_REQUEST);
        ShipmentEventManager.getInstance().subscribe(controller, ShipEventIdentifier.CANCELED,
                ShipEventIdentifier.CREATED, ShipEventIdentifier.UPDATED, ShipEventIdentifier.RETURNED);
        ViewEventManager.getInstance().subscribe(controller, ViewEventIdentifier.CATALOG);


        // provo a vedere se funzionano gli eventi
        //System.out.println("---------------------");
        // System.out.println("Sessione di Luca: ");
        buttons.logoutUser(luca.getEmail());
        Thread.sleep(3000);
        buttons.registerUser(luca.getEmail(), "lucaPassword1");
        Thread.sleep(3000);
        buttons.loginUser(luca.getEmail(), "lucaPassword1");
        Thread.sleep(3000);
        buttons.viewCatalogue(luca.getEmail());
        buttons.addToCart(luca.getEmail(), "06060", "1");
        buttons.purchaseItemsFromCart(Constants.STANDARD, luca.getEmail(), "Indirizzo di Casa Luca", "Luca Maltempo");
        buttons.changeShipmentAddress(luca.getEmail(), "#000001", "nuovoIndirizzo");
        Thread.sleep(8000);
        buttons.changeShipmentAddress(luca.getEmail(), "#000001", "indirizzoNuovo");
        Thread.sleep(3000);

        //System.out.println("Sessione di Sam: ");
        buttons.registerUser(sam.getEmail(), "samPassword1");
        Thread.sleep(3000);
        buttons.loginUser(sam.getEmail(), "samPassword1");
        Thread.sleep(3000);
        buttons.addToCart(sam.getEmail(), "01998", "2");
        Thread.sleep(3000);
        buttons.purchaseItemsFromCart(Constants.PREMIUM, sam.getEmail(), "Indirizzo di Casa Sam", "Samuele Ruotolo");
        buttons.cancelShipment(sam.getEmail(), "#000002");
        Thread.sleep(3000);

        //System.out.println("Sessione di Pie: ");
        buttons.registerUser(pie.getEmail(), "piePassword1");
        Thread.sleep(3000);
        buttons.loginUser(pie.getEmail(), "piePassword1");
        Thread.sleep(3000);
        buttons.addToCart(pie.getEmail(), "06060", "1");
        Thread.sleep(3000);
        buttons.purchaseItemsFromCart(Constants.STANDARD, pie.getEmail(), "Indirizzo di Casa Pietro", "Pietro Siliani");
        Thread.sleep(14000);
        buttons.returnShipment(pie.getEmail(), "#000003");
        buttons.logoutUser(pie.getEmail());
        Thread.sleep(3000);
        buttons.logoutUser(luca.getEmail());
        Thread.sleep(3000);
        buttons.logoutUser(sam.getEmail());

        courierAgency.setProgramFinished();

        System.out.println(Constants.SEPARATOR);
        System.out.println("Inizio test thread:");
    }
}





