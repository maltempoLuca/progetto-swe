package com.company.store.testagencies;

import com.company.outsideworld.couriers.CourierAgency;
import com.company.store.shipping.ShipmentService;

public class InstantDeliveryAgency implements CourierAgency {

    @Override
    public void requestCourier(ShipmentService shipmentService) {
        while (shipmentService.getShipment().getState().getNextState() != null) {
            shipmentService.updateShipmentState();
        }
    }
}
