package com.company.store;

import com.company.constants.Constants;
import com.company.store.eventsys.events.EventBuilder;
import com.company.store.eventsys.events.EventIdentifier;
import com.company.store.eventsys.events.StoreEvent;
import com.company.store.eventsys.management.StoreEventManager;

public class ReturnAllower implements ReturnBehavior {

    private ReturnAllower() {

    }

    public static ReturnAllower getInstance() {
        if (instance == null)
            instance = new ReturnAllower();
        return instance;
    }

    @Override
    public boolean createReturn(Shipment shipment) {

        StoreEvent returnEvent = new StoreEvent(EventBuilder.buildStoreEvent()
                .withInfo(Constants.ID_SPEDIZIONE, shipment.getId())
                .withIdentifier(EventIdentifier.RETURN_ACCEPTED));
        StoreEventManager.getInstance().notify(returnEvent);
        //TODO:: la creazione ed il lancio dell'evento dovrebbero essere alla fine del metodo penso.
//
//        DataPair shipmentInfo = new DataPair(Constants.ID_SPEDIZIONE, shipment.getId());
//        StoreEvent returnEvent = new StoreEvent(EventIdentifier.RETURN, shipmentInfo);
//        StoreEventManager.getInstance().notify(returnEvent);

        String tempSender = shipment.getSender();
        shipment.setSender(shipment.getReceiver());
        shipment.setReceiver(tempSender);

        String tempSenderAddress = shipment.getSenderAddress();
        shipment.setSenderAddress(shipment.getDestinationAddress());
        shipment.setDestinationAddress(tempSenderAddress);

        shipment.setState(Constants.RETURN_CREATED);
        return true;
    }

    private static ReturnAllower instance = null;
}
