package com.company.store;

import com.company.constants.Constants;
import com.company.constants.ShipmentState;
import com.company.store.events.shipments.ShipEventIdentifier;
import com.company.store.events.shipments.ShipmentEvent;
import com.company.store.events.shipments.ShipmentEventManager;

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
        ShipmentEventManager.getInstance().notify(new ShipmentEvent(ShipEventIdentifier.UPDATED, new Shipment(shipment), userEmail)); //TODO: deep copy shipment
        shipment.setState(new ShipmentState(Constants.REQUEST_RECEIVED, shipment.getState()));

        return new OperationResult("Destination address of shipment " + shipment.getId() + " changed to: " + newAddress,true);
    }

    private static UserAddressChanger instance = null;
}
