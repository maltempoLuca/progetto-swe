package com.company.ui;

import com.company.constants.ButtonEvent;
import com.company.constants.ButtonIdentifier;
import com.company.constants.ViewIdentifier;
import com.company.factory.Factory;
import com.company.constants.Constants;

public final class ButtonFactory implements Factory {

    private ButtonFactory() {

    }

    public static ButtonFactory getInstance() {
        if (instance == null)
            instance = new ButtonFactory();
        return instance;
    }

    @Override
    public Button factoryMethod(Object... params) {
        //params[0] = ButtonIdentifier
        //paras[1] = ViewIdentifier - for BACK button only

        Button newButton = null;

        if (params[0] instanceof ButtonIdentifier) {

            ButtonIdentifier requestedButton = (ButtonIdentifier) params[0];
            switch (requestedButton) {
                case BACK:
                    if (params[1] instanceof ViewIdentifier) {
                        newButton = createBackButton((ViewIdentifier) params[1]);
                    } else {
                        //TODO: throw exception
                    }
                    break;

                case REGISTER:
                    newButton = createRegisterButton();
                    break;

                case LOGIN:
                    newButton = createLoginButton();
                    break;

                case LOG_OUT:
                    newButton = createLogoutButton();
                    break;

                case CATALOG:
                    newButton = createCatalogButton();
                    break;

                case INSERTEMAIL:
                    newButton = createInsertEmailButton();
                    break;

                case INSERTPASSWORD:
                    newButton = createInsertPasswordButton();
                    break;

                case CONFIRM_REGISTRATION:
                    newButton = createConfirmRegistrationButton();
                    break;

                case CONFIRM_LOGIN:
                    newButton = createConfirmLoginButton();
                    break;

                default:
                    //TODO: throw exception
            }
        } else {
            //TODO: throw exception
        }

        return newButton;
    }

    private NavigationButton createBackButton(ViewIdentifier nextView) {
        NavigationButton newBackButton = new NavigationButton(Constants.BACK_TEXT, nextView);
        return newBackButton;
    }

    private Button createLogoutButton() {
        Button newLogoutButton = new Button(ButtonEvent.LOG_OUT, "LOG OUT");
        return newLogoutButton;
    }

    private NavigationButton createCatalogButton() {
        NavigationButton newCatalogButton = new NavigationButton(Constants.TO_CATALOG_TEXT, ViewIdentifier.CATALOG);
        return newCatalogButton;
    }

    private Button createLoginButton() {
        NavigationButton newLoginButton = new NavigationButton(Constants.TO_LOGIN_NEXT, ViewIdentifier.LOGIN);
        return newLoginButton;
    }

    private Button createRegisterButton() {
        NavigationButton newRegisterButton = new NavigationButton(Constants.TO_REGISTER_NEXT, ViewIdentifier.REGISTER);
        return newRegisterButton;
    }

    private Button createInsertEmailButton() {
        WritableButton newInsertEmailButton = new WritableButton("Insert Email");
        return newInsertEmailButton;
    }

    private Button createInsertPasswordButton() {
        WritableButton newInsertEmailButton = new WritableButton("Insert Password");
        return newInsertEmailButton;
    }

    private Button createConfirmRegistrationButton() {
        Button newConfirmButton = new ConfirmationButton(ButtonIdentifier.CONFIRM_REGISTRATION);
        return newConfirmButton;
    }

    private Button createConfirmLoginButton() {
        Button newConfirmButton = new ConfirmationButton(ButtonIdentifier.CONFIRM_LOGIN);
        return newConfirmButton;
    }

    private static ButtonFactory instance = null;
}
