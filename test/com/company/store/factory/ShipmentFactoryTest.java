package com.company.store.factory;
import com.company.constants.Constants;
import com.company.store.shipping.*;
import com.company.exceptions.ExceptionMessages;
import com.company.exceptions.InvalidParameterException;
import org.junit.Assert;
import org.junit.Test;

public class ShipmentFactoryTest {
    Shipment testShipment = HelperShipment.getShipment();

    @Test
    public void standardCreationTest() throws InvalidParameterException {
        ShipmentService service = ShipmentFactory.getInstance().createService(testShipment, Constants.STANDARD, "testEmail");
        Assert.assertTrue(service instanceof StandardService);
    }

    @Test
    public void premiumCreationTest() throws InvalidParameterException {
        ShipmentService service = ShipmentFactory.getInstance().createService(testShipment, Constants.PREMIUM, "testEmail");
        Assert.assertTrue(service instanceof PremiumService);
    }

    @Test
    public void returnCreationTest() throws InvalidParameterException {
        ShipmentService service = ShipmentFactory.getInstance().createService(testShipment, Constants.RETURN, "testEmail");
        Assert.assertTrue(service instanceof ReturnService);
    }

    @Test
    public void nullParametersTest() {
           Exception exception = Assert.assertThrows(InvalidParameterException.class, () -> {ShipmentFactory.getInstance().createService(null, Constants.STANDARD, "testEmail");});
           Assert.assertEquals(ExceptionMessages.NULL_INVALID_PARAMETER, exception.getMessage());
    }

    @Test
    public void nullServiceTest() {
        Exception exception = Assert.assertThrows(InvalidParameterException.class, () -> {ShipmentFactory.getInstance().createService(testShipment, null, "testEmail");});
        Assert.assertEquals(ExceptionMessages.NULL_INVALID_PARAMETER, exception.getMessage());
    }

    @Test
    public void invalidServiceTest() {
        String invalidTypeOfService = "testService";
        Exception exception = Assert.assertThrows(InvalidParameterException.class, () -> {ShipmentFactory.getInstance().createService(testShipment, invalidTypeOfService, "testEmail");});
        Assert.assertEquals(ExceptionMessages.SERVICE_INVALID_PARAMETER + invalidTypeOfService , exception.getMessage());
    }

}
