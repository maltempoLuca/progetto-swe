package com.company.store.testagencies;

import com.company.outsideworld.couriers.CourierAgency;
import com.company.store.shipping.ShipmentService;

public final class ManualCourierAgency implements CourierAgency {

    public ManualCourierAgency(ManualCourier courier) {
        this.courier = courier;
    }

    @Override
    public void requestCourier(ShipmentService shipmentService) {
        courier.setService(shipmentService);
    }

    ManualCourier courier;
}
