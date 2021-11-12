package com.company.store;

import com.company.constants.Constants;
import com.company.store.eventsys.events.DataPair;
import com.company.store.eventsys.events.EventIdentifier;
import com.company.store.eventsys.events.StoreEvent;
import com.company.store.eventsys.management.StoreEventManager;

public class UserAddressDenier implements AddressBehavior {

    private UserAddressDenier() {

    }

    public static UserAddressDenier getInstance() {
        if (instance == null)
            instance = new UserAddressDenier();
        return instance;
    }

    //TODO: implement method
    @Override
    public boolean changeAddress(Shipment shipment, String newAddress) {
        DataPair shipmentInfo = new DataPair(Constants.ID_SPEDIZIONE, shipment.getId());
        DataPair reason = new DataPair(Constants.REASON, Constants.CHANGE_ADDRESS_REASON);
        StoreEvent addressEvent = new StoreEvent(EventIdentifier.CHANGE_ADDRESS_REFUSED, shipmentInfo, reason);
        StoreEventManager.getInstance().notify(addressEvent);

        return false;
    }

    private static UserAddressDenier instance = null;
}
