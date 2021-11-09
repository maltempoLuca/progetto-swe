package com.company.store;

import com.company.constants.ButtonEvent;
import com.company.constants.ButtonIdentifier;
import com.company.constants.Constants;
import com.company.constants.ViewIdentifier;
import com.company.mvc.*;
import com.company.ui.*;
import com.sun.net.httpserver.Authenticator;

import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.Scanner;

public final class CustomerController implements Controller {

    @Override
    public void getInput() {

        while (active) {
            if (currentView != null) {
                String key = scanner.nextLine();

                if (key.equalsIgnoreCase("")) {
                    checkButton(currentView.getCurrentButton());
                } else if (key.equalsIgnoreCase("u")) {
                    moveUp();
                } else if (key.equalsIgnoreCase("d")) {
                    moveDown();
                } else if (key.equalsIgnoreCase("e")) {
                    active = false;
                }
            } else {
                //TODO: throw exception
                System.out.println("No view");
                active = false;
            }
        }
    }

    public void init() {
        if (currentView != null) {
            currentView.refresh();
            getInput();
        }
    }

    //TODO: test view selection
    public void selectView(ViewIdentifier identifier) {
        currentView.clear();
        currentView = views.get(identifier);
        currentView.refresh();
    }

    public void checkButton(Button button) {
        ButtonEvent event = button.getEvent();

        switch (event) {
            case CHANGE_VIEW:
                if (button instanceof NavigationButton) {
                    selectView(((NavigationButton) button).getNextView());
                } else {
                    //TODO: throw exception
                }
                break;
            case USERSCAN:
                if (button instanceof WritableButton) {
                    ((WritableButton) button).setUserText(scanUserText());
                } else {
                    //TODO: throw exception
                }
                currentView.refresh();
                break;
            case CONFIRM:
                if (((ConfirmationButton) button).getLocationOfConfirm() == ButtonIdentifier.CONFIRM_LOGIN)
                    loginProcedure();
                else if (((ConfirmationButton) button).getLocationOfConfirm() == ButtonIdentifier.CONFIRM_REGISTRATION)
                    registerProcedure();
                else {
                    //TODO:: THROW EXCEPTION, STO CODICE È UNA MERDA DIO SANTO.
                }
                break;

            case LOG_OUT:
                logOut();
                break;

            case CHANGE_ADDRESS:
                break;
            default:
        }
    }

    private void loginProcedure() {
        ArrayList<String> userInput = currentView.recoverUserInput();
        String result = userDepartment.loginUser(userInput.get(0), userInput.get(1));
        if (!result.equals(Constants.SUCCESS)) {
            currentView.setWarning(result);
        } else {
            currentView.setWarning("Login riuscito.");
            currentView = views.get(ViewIdentifier.HOME);
            currentUser = userInput.get(0);
        }
        currentView.refresh();
    }

    private void registerProcedure() {
        ArrayList<String> userInput = currentView.recoverUserInput();
        String result = userDepartment.registerUser(userInput.get(0), userInput.get(1));
        if (!result.equals(Constants.SUCCESS)) {
            currentView.setWarning(result);
        } else {
            currentView.setWarning("Account creato, per fare il login tornare sulla home usando BACK.");
        }
        currentView.refresh();
    }

    private void logOut() {
        if (currentUser != null) {
            currentUser = null;
        }
        selectView(ViewIdentifier.START);
    }

    private String scanUserText() {
        System.out.print("Write here: ");
        String text = scanner.nextLine();
        return text;
    }

    private void moveUp() {
        currentView.moveUp();
        currentView.refresh();
    }

    private void moveDown() {
        currentView.moveDown();
        currentView.refresh();
    }

    public void addView(ViewIdentifier view) {
        CustomerView newView = (CustomerView) ViewFactory.getInstance().factoryMethod(view);
        if (views.isEmpty()) {
            views.put(view, newView);
            currentView = newView;
        } else if (!views.containsKey(view)) {
            views.put(view, newView);
        } else {
            //TODO: throw exception
        }
    }

    private String currentUser = null;
    private final UserDepartment userDepartment = UserDepartment.getInstance();
    private boolean active = true;
    private CustomerView currentView = null;
    private final Map<ViewIdentifier, CustomerView> views = new HashMap<>();
    private final Scanner scanner = new Scanner(System.in);
}
