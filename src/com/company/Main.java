package com.company;

import com.company.constants.Constants;
import com.company.outsideworld.couriers.StandardCourierAgency;
import com.company.store.Buttons;
import com.company.store.Store;
import com.company.store.purchase.PurchasingDepartment;
import com.company.store.shipping.ShippingDepartment;
import com.company.store.controller.Controller;
import com.company.store.controller.StoreRequest;
import com.company.store.events.requestevents.RequestManager;
import com.company.store.events.shipmentevents.ShipEventIdentifier;
import com.company.store.events.shipmentevents.ShipmentEventManager;
import com.company.store.events.viewevents.ViewEventIdentifier;
import com.company.store.events.viewevents.ViewEventManager;
import com.company.outsideworld.users.User;
import com.company.store.user.UserDepartment;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        StandardCourierAgency courierAgency = new StandardCourierAgency();
        courierAgency.start();

        UserDepartment userDepartment = new UserDepartment();
        ShippingDepartment shippingDepartment = new ShippingDepartment();
        shippingDepartment.setCourierAgency(courierAgency);
        PurchasingDepartment purchasingDepartment = new PurchasingDepartment(shippingDepartment);
        Store.setUserDepartment(userDepartment);
        Store.setShippingDepartment(shippingDepartment);
        Store.setPurchasingDepartment(purchasingDepartment);

        Buttons buttons = Buttons.getInstance();

        User luca = new User(Constants.LUCA_NAME, Constants.LUCA_SURNAME, Constants.LUCA_EMAIL);
        User sam = new User(Constants.SAM_NAME, Constants.SAM_SURNAME, Constants.SAM_EMAIL);
        User pie = new User(Constants.PIE_NAME, Constants.PIE_SURNAME, Constants.PIE_EMAIL);

        Controller controller = new Controller();

        buttons.logoutUser(luca.getEmail());
        Thread.sleep(sleepTime);
        buttons.registerUser(luca.getEmail(), Constants.LUCA_PASSWORD);
        Thread.sleep(sleepTime);
        buttons.loginUser(luca.getEmail(), Constants.LUCA_PASSWORD);
        Thread.sleep(sleepTime);
        buttons.viewCatalogue(luca.getEmail());
        Thread.sleep(sleepTime);
        buttons.addToCart(luca.getEmail(), Constants.LUCA_ITEM, Constants.LUCA_QUANTITY);
        Thread.sleep(sleepTime);
        buttons.purchaseItemsFromCart(Constants.STANDARD, luca.getEmail(), Constants.LUCA_ADDRESS, Constants.LUCA_FULL_NAME);
        buttons.changeShipmentAddress(luca.getEmail(), Constants.LUCA_SHIPMENT, Constants.LUCA_NEW_ADDRESS_1);
        Thread.sleep(changeAddressSleepTime);
        buttons.changeShipmentAddress(luca.getEmail(), Constants.LUCA_SHIPMENT, Constants.LUCA_NEW_ADDRESS_2);
        Thread.sleep(sleepTime);

        buttons.registerUser(sam.getEmail(), Constants.SAM_PASSWORD);
        Thread.sleep(sleepTime);
        buttons.loginUser(sam.getEmail(), Constants.SAM_PASSWORD);
        Thread.sleep(sleepTime);
        buttons.addToCart(sam.getEmail(), Constants.SAM_ITEM, Constants.SAM_QUANTITY);
        Thread.sleep(sleepTime);
        buttons.purchaseItemsFromCart(Constants.PREMIUM, sam.getEmail(), Constants.SAM_ADDRESS, Constants.SAM_FULL_NAME);
        buttons.cancelShipment(sam.getEmail(), Constants.SAM_SHIPMENT);
        Thread.sleep(sleepTime);

        buttons.registerUser(pie.getEmail(), Constants.PIE_PASSWORD);
        Thread.sleep(sleepTime);
        buttons.loginUser(pie.getEmail(), Constants.PIE_PASSWORD);
        Thread.sleep(sleepTime);
        buttons.addToCart(pie.getEmail(), Constants.PIE_ITEM, Constants.PIE_QUANTITY);
        Thread.sleep(sleepTime);
        buttons.purchaseItemsFromCart(Constants.STANDARD, pie.getEmail(), Constants.PIE_ADDRESS, Constants.PIE_FULL_NAME);
        Thread.sleep(returnSleepTime);
        buttons.returnShipment(pie.getEmail(), Constants.PIE_SHIPMENT);
        Thread.sleep(returnSleepTime);
        buttons.returnShipment(pie.getEmail(), Constants.PIE_SHIPMENT);
        buttons.logoutUser(pie.getEmail());
        Thread.sleep(sleepTime);
        buttons.logoutUser(luca.getEmail());
        Thread.sleep(sleepTime);
        buttons.logoutUser(sam.getEmail());

        courierAgency.setProgramFinished();
    }

    private static final int sleepTime = 5000;
    private static final int changeAddressSleepTime = 8000;
    private static final int returnSleepTime = 14000;
}





