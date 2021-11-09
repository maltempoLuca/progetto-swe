package com.company.store;
import com.company.constants.Constants;
import org.junit.Test;
import org.junit.Assert;

public class StandardServiceTest {
    private Shipment shipment = new Shipment("sender", "receiver", "senderAddress",
                                             "destinationAddress", "contents", "#000001");

    private StandardService service = new StandardService(shipment);

    //TODO: test exception if nextState == null
    @Test
    public void updateStateTest() {
        Assert.assertEquals(Constants.CREATED, shipment.getState());

        service.updateShipmentState();
        Assert.assertEquals(Constants.SENT, shipment.getState());

        service.updateShipmentState();
        Assert.assertEquals(Constants.IN_TRANSIT, shipment.getState());

        service.updateShipmentState();
        Assert.assertEquals(Constants.IN_DELIVERY, shipment.getState());

        service.updateShipmentState();
        Assert.assertEquals(Constants.DELIVERED, shipment.getState());
    }

    @Test
    public void successChangeAddressTest() {
        String newAddress = "new address";
        try {
            service.changeAddress(newAddress);
        } catch (ChangeAddressException e) {};
        Assert.assertEquals(newAddress, shipment.getDestinationAddress());
    }

    @Test
    public void failChangeAddressTest() {
        String newAddress = "new address";
        while(shipment.getState() != Constants.SENT) {
            service.updateShipmentState();
        }
        try {
            service.changeAddress(newAddress);
        } catch (ChangeAddressException e) {};
        Assert.assertEquals("destinationAddress", shipment.getDestinationAddress());
    }

    //TODO: test internal address changer
    //TODO: test other strategy behaviors
}
