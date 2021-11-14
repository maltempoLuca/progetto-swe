package com.company;

import com.company.constants.Constants;
import com.company.outsideworld.CourierAgency;
import com.company.store.*;
import com.company.user.User;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        UserDepartment userDepartment = UserDepartment.getInstance();
        User luca = new User("Luca", "Maltempo", "luchino@pippo.com");
        User sam = new User("Samuele", "Ruotolo", "sam@pippo.com");
        User pie = new User("Pietro", "Siliani", "pie@pippo.com");

        System.out.println("Sessione di Luca: ");
        System.out.println(userDepartment.logOut(luca.getEmail()));
        System.out.println(userDepartment.registerUser(luca.getEmail(), "lucaPassword1"));
        System.out.println(userDepartment.logOut(luca.getEmail()));
        System.out.println(userDepartment.loginUser(luca.getEmail(), "lucaPassword1"));
        System.out.println((userDepartment.logOut(luca.getEmail())));

        System.out.println("Sessione di Sam: ");
        System.out.println(userDepartment.registerUser(sam.getEmail(), "samPassword"));
        System.out.println(userDepartment.registerUser(sam.getEmail(), "samPassword1"));
        System.out.println(userDepartment.loginUser(sam.getEmail(), "samPassword1"));
        System.out.println((userDepartment.logOut(sam.getEmail())));

        System.out.println("Sessione di Pie: ");
        System.out.println(userDepartment.registerUser(pie.getEmail(), "piePassword1"));
        System.out.println(userDepartment.loginUser(pie.getEmail(), "piePassword0"));
        System.out.println(userDepartment.loginUser(pie.getEmail(), "piePassword1"));
        System.out.println((userDepartment.logOut(pie.getEmail())));

        CourierAgency courierAgency = CourierAgency.getInstance();
        Thread t = new Thread(courierAgency);
        for (int i = 0; i < 22; i++) {
            Shipment shipment = new Shipment("a", "a", "", "", "", "i");
            ShipmentService shipmentService = new StandardService(shipment);
            courierAgency.requestCourier(shipmentService);
        }
        t.start();
    }

}
