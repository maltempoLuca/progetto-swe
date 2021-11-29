package com.company.store;

import com.company.constants.Constants;
import com.company.constants.ShipmentState;
import org.junit.Test;
import org.junit.Assert;
import org.junit.function.ThrowingRunnable;

public class StandardServiceTest {
    private Shipment shipment = new Shipment("sender", "receiver", "senderAddress",
            "destinationAddress", "contents", "#000001");

    private StandardService service = new StandardService(shipment, "luchino@pippo.com");

    @Test//(expected = NullPointerException.class)
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

        service.updateShipmentState(); // impossibile da testare perche l eccezione viene catturata e gestita dentro al metodo
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
        while (shipment.getState() != Constants.SENT) {
            service.updateShipmentState();
        }
        service.changeAddress(newAddress);
        Assert.assertEquals("destinationAddress", shipment.getDestinationAddress());
    }

    @Test
    public void successCancelShipmentTest() {
        while (shipment.getState() != Constants.SENT) {
            ShipmentState currentState = shipment.getState();
            OperationResult result = service.cancelShipment();
            Assert.assertTrue(result.isSuccessful());
            shipment.setState(currentState);
            service.updateShipmentState();
        }
    }

    @Test
    public void failCancelShipmentTest() {
        while (shipment.getState() != Constants.SENT) {
            service.updateShipmentState();
        }
        while (shipment.getState() != Constants.DELIVERED) {
            ShipmentState currentState = shipment.getState();
            OperationResult result = service.cancelShipment();
            Assert.assertFalse(result.isSuccessful());
            shipment.setState(currentState);
            service.updateShipmentState();
        }
    }

    @Test
    public void successCreateReturnTest() {
        while (shipment.getState() != Constants.DELIVERED) {
            service.updateShipmentState();
        }
        OperationResult result = service.createReturn();
        Assert.assertTrue(result.isSuccessful());
    }

    @Test
    public void failCreateReturnTest() {
        while (shipment.getState() != Constants.DELIVERED) {
            OperationResult result = service.createReturn();
            Assert.assertFalse(result.isSuccessful());
            service.updateShipmentState();
        }
    }
}

