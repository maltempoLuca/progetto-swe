package com.company.store;

import com.company.constants.Constants;
import com.company.store.eventsys.events.EventBuilder;
import com.company.store.eventsys.events.EventIdentifier;
import com.company.store.eventsys.events.StoreEvent;
import com.company.store.eventsys.management.StoreEventManager;

public class ReturnDenier implements ReturnBehavior {

    private ReturnDenier() {

    }

    public static ReturnDenier getInstance() {
        if (instance == null)
            instance = new ReturnDenier();
        return instance;
    }

    @Override
    public boolean createReturn(Shipment shipment) {

        StoreEvent returnEvent = new StoreEvent(EventBuilder.buildStoreEvent()
                .withInfo(Constants.ID_SPEDIZIONE, shipment.getId())
                .withInfo(Constants.REASON, Constants.RETURN_REASON)
                .withIdentifier(EventIdentifier.RETURN_REFUSED));
        StoreEventManager.getInstance().notify(returnEvent);

//        DataPair shipmentInfo = new DataPair(Constants.ID_SPEDIZIONE, shipment.getId());
//        DataPair reason = new DataPair(Constants.REASON, Constants.RETURN_REASON);
//        StoreEvent returnEvent = new StoreEvent(EventIdentifier.RETURN_REFUSED, shipmentInfo, reason);
//        StoreEventManager.getInstance().notify(returnEvent);

        return false;
    }

    private static ReturnDenier instance = null;
}
