package com.company.store.shipping;

import com.company.store.shipping.Shipment;

public final class HelperShipment {

    public static Shipment getShipment() {
        return new Shipment("test", "test", "test", "test", "test", "test");
    }
}
