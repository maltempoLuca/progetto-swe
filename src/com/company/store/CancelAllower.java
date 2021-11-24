package com.company.store;

import com.company.constants.Constants;
import com.company.store.eventsys.events.DataPair;
import com.company.store.eventsys.events.EventBuilder;
import com.company.store.eventsys.events.EventIdentifier;
import com.company.store.eventsys.events.StoreEvent;
import com.company.store.eventsys.events.shipments.ShipEventIdentifier;
import com.company.store.eventsys.events.shipments.ShipmentEvent;
import com.company.store.eventsys.events.shipments.ShipmentEventManager;
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
        //TODO:: aggiungere stato "CANCELLATA"
        ShipmentEvent shipmentEvent = new ShipmentEvent(ShipEventIdentifier.CANCELED, new Shipment(shipment));
        ShipmentEventManager.getInstance().notify(shipmentEvent);
        return true;
    }

    private static CancelAllower instance = null;
}
