package com.company.store;

import com.company.constants.Constants;
import com.company.store.eventsys.events.EventBuilder;
import com.company.store.eventsys.events.EventIdentifier;
import com.company.store.eventsys.events.StoreEvent;
import com.company.store.eventsys.management.StoreEventManager;

public class UserAddressChanger implements AddressBehavior {

    private UserAddressChanger() {

    }

    public static UserAddressChanger getInstance() {
        if (instance == null)
            instance = new UserAddressChanger();
        return instance;
    }

    @Override
    public boolean changeAddress(Shipment shipment, String newAddress) {

        StoreEvent addressEvent = new StoreEvent(EventBuilder.buildStoreEvent()
                .withInfo(Constants.ID_SPEDIZIONE, shipment.getId())
                .withIdentifier(EventIdentifier.CHANGE_ADDRESS_ACCEPTED));
        StoreEventManager.getInstance().notify(addressEvent);
//
//        DataPair shipmentInfo = new DataPair(Constants.ID_SPEDIZIONE, shipment.getId());
//        StoreEvent addressEvent = new StoreEvent(EventIdentifier.CHANGE_ADDRESS, shipmentInfo);
//        StoreEventManager.getInstance().notify(addressEvent);

        shipment.setDestinationAddress(newAddress);
        return true;
    }

    private static UserAddressChanger instance = null;
}
