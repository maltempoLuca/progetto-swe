package com.company.store;

import com.company.constants.Constants;
import com.company.store.eventsys.events.EventBuilder;
import com.company.store.eventsys.events.EventIdentifier;
import com.company.store.eventsys.events.StoreEvent;
import com.company.store.eventsys.management.StoreEventManager;

public class InternalAddressChanger implements AddressBehavior {

    private InternalAddressChanger() {

    }

    public static InternalAddressChanger getInstance() {
        if (instance == null)
            instance = new InternalAddressChanger();
        return instance;
    }

    @Override
    public boolean changeAddress(Shipment shipment, String newAddress) {
        //TODO: a cosa serve questo try e catch?
        try {
            StoreEvent addressEvent = new StoreEvent(EventBuilder.buildStoreEvent()
                    .withInfo(Constants.ID_SPEDIZIONE, shipment.getId())
                    .withInfo(Constants.REASON, Constants.INTERNAL_ADDRESS_REASON)
                    .withIdentifier(EventIdentifier.CHANGE_ADDRESS_REFUSED));
            StoreEventManager.getInstance().notify(addressEvent);
        } catch (UnsupportedOperationException exception) {}
        return false;
    }

    private static InternalAddressChanger instance = null;
}
