package com.company;

import com.company.constants.Constants;
import com.company.constants.ViewIdentifier;
import com.company.mvc.Controller;
import com.company.store.CustomerController;

public class Main {

    public static void main(String[] args) {
	// write your code here
        CustomerController controller = new CustomerController();
        controller.addView(Constants.START);
        controller.addView(Constants.HOME);
        controller.addView(Constants.CATALOG);
        controller.init();
    }
}
