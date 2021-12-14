package com.company.store.shipping.strategy.addressbehavior;

import com.company.constants.Constants;
import com.company.store.shipping.ShipmentState;
import com.company.store.OperationResult;
import com.company.store.shipping.Shipment;
import com.company.store.events.shipmentevents.ShipEventIdentifier;
import com.company.store.events.shipmentevents.ShipmentEvent;
import com.company.store.events.shipmentevents.ShipmentEventManager;

public class UserAddressChanger implements AddressBehavior {

    private UserAddressChanger() {

    }

    public static UserAddressChanger getInstance() {
        if (instance == null)
            instance = new UserAddressChanger();
        return instance;
    }

    @Override
    public OperationResult changeAddress(Shipment shipment, String userEmail, String newAddress) {
        shipment.setDestinationAddress(newAddress);
        ShipmentState requestCompleted = new ShipmentState(Constants.ADDRESS_CHANGED, shipment.getState());
        shipment.setState(new ShipmentState(Constants.REQUEST_RECEIVED, requestCompleted));
        ShipmentEventManager.getInstance().notify(new ShipmentEvent(ShipEventIdentifier.UPDATED, new Shipment(shipment), userEmail));

        return new OperationResult("Destination address of shipment " + shipment.getId() + " changed to: " + newAddress,true);
    }

    private static UserAddressChanger instance = null;
}
