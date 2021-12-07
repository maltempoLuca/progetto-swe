package com.company.store;

import com.company.constants.Constants;
import com.company.constants.ShipmentState;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import org.junit.function.ThrowingRunnable;

public class ReturnServiceTest {
    private Shipment shipment = new Shipment("sender", "receiver", "senderAddress",
            "destinationAddress", "contents", "#000001");

    private ReturnService service = new ReturnService(shipment, "luchino@pippo.com");

    @Test
    public void updateStateTest() {
        Assert.assertEquals(Constants.RETURN_CREATED, shipment.getState());

        service.updateShipmentState();
        Assert.assertEquals(Constants.PICKED_UP, shipment.getState());

        service.updateShipmentState();
        Assert.assertEquals(Constants.RETURN_DELIVERED, shipment.getState());

        service.updateShipmentState(); // impossibile da testare perche l eccezione viene catturata e gestita dentro al metodo
    }

    @Test
    public void failChangeAddressTest() {
        String newAddress = "new address";
        while (shipment.getState() != Constants.RETURN_DELIVERED) {
            service.changeAddress(newAddress);
            Assert.assertEquals("destinationAddress", shipment.getDestinationAddress());
            service.updateShipmentState();
        }
    }


    @Test
    public void failCancelShipmentTest() {
        while (shipment.getState() != Constants.RETURN_DELIVERED) {
            ShipmentState currentState = shipment.getState();
            OperationResult result = service.cancelShipment();
            Assert.assertFalse(result.isSuccessful());
            shipment.setState(currentState);
            service.updateShipmentState();
        }
    }

    @Test(expected = UnsupportedOperationException.class)
    public void failCreateReturnTest() {
        while (shipment.getState() != Constants.RETURN_DELIVERED) {
            service.createReturn();
            service.updateShipmentState();
        }
    }

    @After
    public void clearInstances() {
        Store.clearInstance();
        ShippingDepartment.clearInstance();
        ShippingDepartment.clearInstance();
        UserDepartment.clearInstance();
    }
}
