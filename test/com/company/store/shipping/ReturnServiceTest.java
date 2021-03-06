package com.company.store.shipping;

import com.company.constants.Constants;
import com.company.store.Store;
import com.company.store.user.UserDepartment;
import org.junit.After;
import com.company.store.OperationResult;
import org.junit.Assert;
import org.junit.Test;

public final class ReturnServiceTest {

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

    @Test
    public void failCreateReturnTest() {
        while (shipment.getState() != Constants.RETURN_DELIVERED) {
            OperationResult result = service.createReturn();
            Assert.assertFalse(result.isSuccessful());
            service.updateShipmentState();
        }
    }

    private final Shipment shipment = new Shipment("sender", "receiver", "senderAddress",
            "destinationAddress", "contents", "#000001");

    private final ReturnService service = new ReturnService(shipment, "luchino@pippo.com");
}
