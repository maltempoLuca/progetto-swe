package com.company.ui;

import com.company.constants.EventIdentifier;
import com.company.constants.ButtonIdentifier;
import com.company.events.listener.Event;
import com.company.events.EventFactory;
import com.company.factory.Factory;
import com.company.constants.Constants;
import com.company.store.Shipment;

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
        //params[1] = String (ViewIdentifier) - for BACK button only
        //params[1] = Shipment - for shipment button only

        Button newButton = null;

        if (params[0] instanceof ButtonIdentifier requestedButton) {

            switch (requestedButton) {
                case BACK:
                    if (params[1] instanceof String viewIdentfier) {
                        newButton = createNavigationButton(Constants.BACK_TEXT, viewIdentfier);
                    } else {
                        //TODO: throw exception
                    }
                    break;
                case SHIPMENT:
                    if(params[1] instanceof Shipment shipment) {
                        newButton = createShipmentButton(shipment);
                    } else {
                        //TODO: throw exception
                    }
                    break;

                case REGISTER:
                    newButton = createSimpleButton(EventIdentifier.REGISTER, Constants.REGISTER_TEXT);
                    break;

                case LOGIN:
                    newButton = createSimpleButton(EventIdentifier.LOGIN, Constants.LOGIN_TEXT);
                    break;

                case LOG_OUT:
                    newButton = createSimpleButton(EventIdentifier.LOG_OUT, Constants.LOG_OUT_TEXT);
                    break;

                case CATALOG:
                    newButton = createNavigationButton(Constants.TO_CATALOG_TEXT, Constants.CATALOG);
                    break;

                default:
                    //TODO: throw exception
            }
        } else {
            //TODO: throw exception
        }

        return newButton;
    }

    private SimpleButton createSimpleButton(EventIdentifier thrownEventId, String text) {
        Event thrownEvent = EventFactory.getInstance().factoryMethod(thrownEventId);
        return new SimpleButton(thrownEvent, text);
    }

    private NavigationButton createNavigationButton(String text, String nextView) {
        Event thrownEvent = EventFactory.getInstance().factoryMethod(EventIdentifier.CHANGE_VIEW, nextView);
        return new NavigationButton(thrownEvent, text);
    }

    private ShipmentButton createShipmentButton(Shipment shipment) {
        Event thrownEvent = EventFactory.getInstance().factoryMethod(EventIdentifier.CHANGE_VIEW, shipment.getId());
        return new ShipmentButton(thrownEvent, shipment.getId(), shipment.getState().getCurrentState());
    }

    private static ButtonFactory instance = null;
}
