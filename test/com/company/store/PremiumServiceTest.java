package com.company.store;

import com.company.constants.Constants;
import org.junit.Assert;
import org.junit.Test;

public class PremiumServiceTest {
    private Shipment shipment = new Shipment("userId", "sender", "receiver", "senderAddress",
            "destinationAddress", "contents", "#000001");

    private PremiumService service = new PremiumService(shipment);

    @Test(expected = NullPointerException.class)
    public void updateStateTest() {
        Assert.assertEquals(Constants.CREATED, shipment.getState());

        service.updateShipmentState();
        Assert.assertEquals(Constants.SENT, shipment.getState());

        service.updateShipmentState();
        Assert.assertEquals(Constants.IN_TRANSIT, shipment.getState());

        service.updateShipmentState();
        Assert.assertEquals(Constants.OUT_FOR_DELIVERY, shipment.getState());

        service.updateShipmentState();
        Assert.assertEquals(Constants.DELIVERED, shipment.getState());

        service.updateShipmentState();
    }

    @Test
    public void successChangeAddressTest() {
        String newAddress = "new address";
        service.changeAddress(newAddress);
        Assert.assertEquals(newAddress, shipment.getDestinationAddress());
    }

    @Test
    public void failChangeAddressTest() {
        String newAddress = "new address";
        while (shipment.getState() != Constants.OUT_FOR_DELIVERY) {
            service.updateShipmentState();
        }
        service.changeAddress(newAddress);
        Assert.assertEquals("destinationAddress", shipment.getDestinationAddress());
    }

    //TODO: test internal address changer
    //TODO: test other strategy behaviors
}
