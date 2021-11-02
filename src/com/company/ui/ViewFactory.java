package com.company.ui;

import com.company.constants.ButtonIdentifier;
import com.company.constants.Constants;
import com.company.constants.ViewIdentifier;
import com.company.factory.Factory;
import com.company.mvc.View;

public final class ViewFactory implements Factory {

    private ViewFactory() {

    }

    public static ViewFactory getInstance() {
        if (instance == null)
            instance = new ViewFactory();
        return instance;
    }

    @Override
    public View factoryMethod(Object... params) {
        //params[0] = ViewIdentifier

        View newView = null;

        if (params[0] instanceof ViewIdentifier) {
            ViewIdentifier requestedView = (ViewIdentifier) params[0];
            switch (requestedView) {

                case START:
                    newView = createStartView();
                    break;

                case HOME:
                    newView = createHomeView();
                    break;

                case CATALOG:
                    newView = createCatalogView();
                    break;

                default:
            }

        } else {
            //TODO: throw exception
        }
        return newView;
    }

    CustomerView createStartView() {
        CustomerView newStartView = new CustomerView("Welcome to Pippo.com");
        newStartView.addButton( ButtonFactory.getInstance().factoryMethod(ButtonIdentifier.REGISTER));
        newStartView.addButton( ButtonFactory.getInstance().factoryMethod(ButtonIdentifier.LOGIN));
        return newStartView;
    }

    CustomerView createHomeView() {
        CustomerView newHomeView = new CustomerView(Constants.HOME_TOP_TEXT);
        newHomeView.addButton((Button) ButtonFactory.getInstance().factoryMethod(ButtonIdentifier.CATALOG));
        newHomeView.addButton(ButtonFactory.getInstance().factoryMethod(ButtonIdentifier.LOG_OUT));
        return newHomeView;
    }

    CustomerView createCatalogView() {
        CustomerView newCatalogView = new CustomerView(Constants.CATLOG_TOP_TEXT);
        newCatalogView.addButton((Button) ButtonFactory.getInstance().factoryMethod(ButtonIdentifier.BACK, ViewIdentifier.HOME));
        newCatalogView.addButton((Button) ButtonFactory.getInstance().factoryMethod(ButtonIdentifier.CATALOG));
        return newCatalogView;
    }

    private static ViewFactory instance = null;
}
