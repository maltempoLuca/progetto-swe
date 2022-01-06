package com.company.store.shipping;

import com.company.constants.Constants;
import com.company.store.Store;
import com.company.store.user.UserDepartment;
import org.junit.After;
import com.company.store.OperationResult;
import org.junit.Assert;
import org.junit.Test;

public class PremiumServiceTest {

    @Test
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
        while (shipment.getState() != Constants.OUT_FOR_DELIVERY) {
            service.updateShipmentState();
        }
        service.changeAddress(newAddress);
        Assert.assertEquals("destinationAddress", shipment.getDestinationAddress());
    }

    @Test
    public void successCancelShipmentTest() {
        Shipment shipmentClone;
        while (shipment.getState() != Constants.OUT_FOR_DELIVERY) {
            shipmentClone = new Shipment(shipment);
            PremiumService shipmentServiceClone = new PremiumService(shipmentClone,"luchino@pippo.com");
            OperationResult result = shipmentServiceClone.cancelShipment();
            Assert.assertTrue(result.isSuccessful());
            service.updateShipmentState();
        }
    }

    @Test
    public void failCancelShipmentTest() {
        while (shipment.getState() != Constants.OUT_FOR_DELIVERY) {
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

    private final Shipment shipment = new Shipment("sender", "receiver", "senderAddress",
            "destinationAddress", "contents", "#000001");

    private final PremiumService service = new PremiumService(shipment, "luchino@pippo.com");
}