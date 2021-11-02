package com.company.store;
import com.company.constants.Constants;

public class PremiumService extends ShipmentService {
    public PremiumService(Shipment shipment) {
        super(Constants.HIGH_PRIORITY, shipment);
    }
}
