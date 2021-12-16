package com.company.store.testagencies;

import com.company.store.shipping.ShipmentService;

public class ManualCourier {

    public void updateShipment() {
        service.updateShipmentState();
    }

    public void setService(ShipmentService service) {
        this.service = service;
    }

    private ShipmentService service;
}
