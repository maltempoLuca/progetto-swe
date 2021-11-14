package com.company.store;

import com.company.constants.Constants;
import com.company.store.eventsys.events.DataPair;
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
        DataPair shipmentInfo = new DataPair(Constants.ID_SPEDIZIONE, shipment.getId());
        StoreEvent cancelEvent = new StoreEvent(EventIdentifier.CANCEL, shipmentInfo);
        StoreEventManager.getInstance().notify(cancelEvent);

        shipment = null;
        return true;
    }

    private static CancelAllower instance = null;
}
