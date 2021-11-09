package com.company.store;
import com.company.constants.Constants;
import com.company.constants.EventIdentifier;
import com.company.events.listener.Event;
import com.company.events.listener.EventListener;
import com.company.events.GlobalEventManager;
import com.company.mvc.*;
import com.company.ui.CustomerView;
import com.company.ui.ViewFactory;

import java.util.Map;
import java.util.HashMap;
import java.util.Scanner;

public final class CustomerController implements Controller, EventListener {

    @Override
    public void getInput() {

        boolean keyPressed;

        while (active) {
            keyPressed = false;

            if (currentView != null) {
                String key = scanner.nextLine();

                if (key.equalsIgnoreCase("p")) {
                    currentView.getCurrentButton().onClick();
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

    @Override
    public void handleEvent(Event event) {

        EventIdentifier identifier = event.getIdentifier();

        try {

            switch (identifier) {
                case CHANGE_VIEW:
                    selectView(event.getInfo());
                    break;

                case REGISTER:
                    scanRegister();
                    break;

                case LOGIN:
                    scanLogin();
                    break;

                case LOG_OUT:
                    logOut();
                    break;

                case CHANGE_ADDRESS:
                    break;
                default:
            }
        } catch (UnsupportedOperationException e) {
            currentView.setWarning(e.getMessage());
            currentView.refresh();
        }
    }

    public void init() {
        GlobalEventManager.getInstance().subscribe(this, EventIdentifier.values());
        if (currentView != null) {
            currentView.refresh();
            getInput();
        }
    }

    /*
    public void checkButton(Button button) {
        EventIdentifier event = button.getEvent();

        switch(event) {
            case CHANGE_VIEW:
                if (button instanceof NavigationButton) {
                    selectView(((NavigationButton)button).getNextView());
                } else {
                    //TODO: throw exception
                }
                break;

            case REGISTER:
                scanRegister();
                break;

            case LOGIN:
                scanLogin();
                break;

            case LOG_OUT:
                logOut();
                break;

            case CHANGE_ADDRESS:
                break;
            default:
        }
    }
    */

    private void scanLogin() {
        //LoginView loginView = LoginView.getInstance();
        //loginView.emailView();

        currentView.setWarning("Per effettuare il Login inserire la mail utilizzata durante la registrazione al sito.");
        currentView.refreshWarning();

        //String email = scanner.nextLine();

        String email = getText();

        //loginView.passwordView();

        currentView.setWarning("Per effettuare il login inserire la password.");
        currentView.refreshWarning();

        //String password = scanner.nextLine();

        String password = getText();
        String nextView;

        int success = userDepartment.loginUser(email, password);
        if (success == 0) {
            //loginView.accessDenied();

            currentView.setWarning("Email o Password errata, per riprovare premere p.");
            currentView.refreshWarning();
            nextView = Constants.START;

        } else {

            currentView.setWarning("Login riuscito, digitare 'p' continuare.");
            currentView.refreshWarning();
            currentUser = email;
            views.get(Constants.HOME).setTopText("Ciao " + currentUser + " benvenuto su Pippo.com");
            nextView = Constants.HOME;

            //loginView.accessGranted();

            currentUser = email;
        }

        String input = "";
        while (!input.equalsIgnoreCase("p")) {
            input = scanner.nextLine();
        }

        selectView(nextView);

        //nextView(currentView.currentState());
    }

    private void scanRegister() {
        //RegisterView registerView = RegisterView.getInstance();

        //registerView.emailView();

        currentView.setWarning("Inserire una mail: ");
        currentView.refreshWarning();

        //String email = scanner.nextLine();

        String email = getText();

        //registerView.passwordView();

        currentView.setWarning("Inserire una password: ");
        currentView.refreshWarning();

        //String password = scanner.nextLine();

        String password = getText();

        int success = userDepartment.registerUser(email, password);
        if (success == 0) {
            //registerView.accessDenied();

            currentView.setWarning("Email o Password errata, per riprovare digitare 'p'.");
            currentView.refreshWarning();
            //nextView =

        } else {
            //registerView.accessGranted();

            currentView.setWarning("Account creato, digitare 'p' continuare.");
            currentView.refreshWarning();
        }

        String input = "";
        while (!input.equalsIgnoreCase("p")) {
            input = scanner.nextLine();
        }

        String nextView = Constants.START;

        //nextView(currentView.currentState());

        selectView(nextView);
    }

    private void logOut() {
        if (currentUser != null) {
            currentUser = null;
        }
        selectView(Constants.START);
    }
    
    private String getText() {
        //get text from user
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

    public void addView(String viewIdentifier) {
        CustomerView newView = (CustomerView) ViewFactory.getInstance().factoryMethod(viewIdentifier);
        if (views.isEmpty()) {
            views.put(viewIdentifier, newView);
            currentView = newView;
        } else if (!views.containsKey(viewIdentifier)) {
           views.put(viewIdentifier, newView);
        } else {
            //TODO: throw exception
        }
    }

    //TODO: test view selection
    public void selectView(String viewIdentifier) {
        currentView.clear();
        currentView = views.get(viewIdentifier);
        currentView.refresh();
    }

    private String currentUser = null;
    private final UserDepartment userDepartment = UserDepartment.getInstance();
    private boolean active = true;
    private CustomerView currentView = null;
    private final Map<String, CustomerView> views = new HashMap<>();
    private final Scanner scanner = new Scanner(System.in);
}
