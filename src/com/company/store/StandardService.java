package com.company.store;
import com.company.constants.Constants;

public class StandardService extends ShipmentService {

    StandardService(Shipment shipment) {
        super(Constants.LOW_PRIORITY, shipment);
    }
}
