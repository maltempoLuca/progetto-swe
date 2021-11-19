package com.company.store;

import com.company.constants.Constants;
import com.company.store.eventsys.events.DataPair;
import com.company.store.eventsys.events.EventBuilder;
import com.company.store.eventsys.events.EventIdentifier;
import com.company.store.eventsys.events.StoreEvent;
import com.company.store.eventsys.management.StoreEventManager;

public class CancelAllower implements CancelBehavior {

    private CancelAllower() {

    }

    public static CancelAllower getInstance() {
        if (instance == null)
            instance = new CancelAllower();
        return instance;
    }

    @Override
    public boolean cancelShipment(Shipment shipment) {
        StoreEvent cancelEvent = new StoreEvent(EventBuilder.buildStoreEvent()
                .withInfo(Constants.ID_SPEDIZIONE, shipment.getId())
                .withIdentifier(EventIdentifier.CANCEL_SUCCESS));
        StoreEventManager.getInstance().notify(cancelEvent);

        shipment = null;
        return true;
    }

    private static CancelAllower instance = null;
}
