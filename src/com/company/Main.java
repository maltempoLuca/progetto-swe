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

public class Main {
    //TODO: metti final in tutte classi e metodi

    public static void main(String[] args) throws InterruptedException {
        StandardCourierAgency courierAgency = new StandardCourierAgency();
        courierAgency.start();
        Buttons buttons = Buttons.getInstance();
        ShippingDepartment.getInstance().setCourierAgency(courierAgency);
        User luca = new User(Constants.LUCA_NAME, Constants.LUCA_SURNAME, Constants.LUCA_EMAIL);
        User sam = new User(Constants.SAM_NAME, Constants.SAM_SURNAME, Constants.SAM_EMAIL);
        User pie = new User(Constants.PIE_NAME, Constants.PIE_SURNAME, Constants.PIE_EMAIL);

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
        buttons.registerUser(luca.getEmail(), Constants.LUCA_PASSWORD);
        Thread.sleep(3000);
        buttons.loginUser(luca.getEmail(), Constants.LUCA_PASSWORD);
        Thread.sleep(3000);
        buttons.viewCatalogue(luca.getEmail());
        buttons.addToCart(luca.getEmail(), Constants.LUCA_ITEM, Constants.LUCA_QUANTITY);
        buttons.purchaseItemsFromCart(Constants.STANDARD, luca.getEmail(), Constants.LUCA_ADDRESS, Constants.LUCA_FULL_NAME);
        buttons.changeShipmentAddress(luca.getEmail(), Constants.LUCA_SHIPMENT, Constants.LUCA_NEW_ADDRESS_1);
        Thread.sleep(8000);
        buttons.changeShipmentAddress(luca.getEmail(), Constants.LUCA_SHIPMENT, Constants.LUCA_NEW_ADDRESS_2);
        Thread.sleep(3000);

        //System.out.println("Sessione di Sam: ");
        buttons.registerUser(sam.getEmail(), Constants.SAM_PASSWORD);
        Thread.sleep(3000);
        buttons.loginUser(sam.getEmail(), Constants.SAM_PASSWORD);
        Thread.sleep(3000);
        buttons.addToCart(sam.getEmail(), Constants.SAM_ITEM, Constants.SAM_QUANTITY);
        Thread.sleep(3000);
        buttons.purchaseItemsFromCart(Constants.PREMIUM, sam.getEmail(), Constants.SAM_ADDRESS, Constants.SAM_FULL_NAME);
        buttons.cancelShipment(sam.getEmail(), Constants.SAM_SHIPMENT);
        Thread.sleep(3000);

        //System.out.println("Sessione di Pie: ");
        buttons.registerUser(pie.getEmail(), Constants.PIE_PASSWORD);
        Thread.sleep(3000);
        buttons.loginUser(pie.getEmail(), Constants.PIE_PASSWORD);
        Thread.sleep(3000);
        buttons.addToCart(pie.getEmail(), Constants.PIE_ITEM, Constants.PIE_QUANTITY);
        Thread.sleep(3000);
        buttons.purchaseItemsFromCart(Constants.STANDARD, pie.getEmail(), Constants.PIE_ADDRESS, Constants.PIE_FULL_NAME);
        Thread.sleep(14000);
        buttons.returnShipment(pie.getEmail(), Constants.PIE_SHIPMENT);
        buttons.logoutUser(pie.getEmail());
        Thread.sleep(3000);
        buttons.logoutUser(luca.getEmail());
        Thread.sleep(3000);
        buttons.logoutUser(sam.getEmail());

        courierAgency.setProgramFinished();

        System.out.println(Constants.SEPARATOR);
    }
}





