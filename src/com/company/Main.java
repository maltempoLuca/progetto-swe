package com.company;

import com.company.constants.ViewIdentifier;
import com.company.mvc.Controller;
import com.company.store.CustomerController;

public class Main {

    public static void main(String[] args) {
	// write your code here
        CustomerController controller = new CustomerController();
        controller.addView(ViewIdentifier.START);
        controller.addView(ViewIdentifier.HOME);
        controller.addView(ViewIdentifier.CATALOG);
        controller.init();
    }
}
