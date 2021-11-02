package com.company.store;
import com.company.constants.ButtonEvent;
import com.company.constants.ViewIdentifier;
import com.company.mvc.*;
import com.company.ui.CustomerView;
import com.company.ui.Button;
import com.company.ui.NavigationButton;
import com.company.ui.ViewFactory;

import java.util.Map;
import java.util.HashMap;
import java.util.Scanner;

public final class CustomerController implements Controller {

    @Override
    public void getInput() {

        boolean keyPressed;

        while (active) {
            keyPressed = false;

            if (currentView != null) {
                String key = scanner.nextLine();

                if (key.equalsIgnoreCase("p")) {
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
        ViewIdentifier nextView;

        int success = userDepartment.loginUser(email, password);
        if (success == 0) {
            //loginView.accessDenied();

            currentView.setWarning("Email o Password errata, per riprovare premere p.");
            currentView.refreshWarning();
            nextView = ViewIdentifier.START;

        } else {

            currentView.setWarning("Login riuscito, digitare 'p' continuare.");
            currentView.refreshWarning();
            currentUser = email;
            views.get(ViewIdentifier.HOME).setTopText("Ciao " + currentUser + " benvenuto su Pippo.com");
            nextView = ViewIdentifier.HOME;

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

        ViewIdentifier nextView = ViewIdentifier.START;

        //nextView(currentView.currentState());

        selectView(nextView);
    }

    private void logOut() {
        if (currentUser != null) {
            currentUser = null;
        }
        selectView(ViewIdentifier.START);
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
