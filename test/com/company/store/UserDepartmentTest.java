package com.company.store;

import com.company.constants.Constants;
import com.company.store.shipping.ShippingDepartment;
import org.junit.After;
import com.company.store.user.UserDepartment;
import org.junit.Test;
import org.junit.Assert;

public class UserDepartmentTest {

    @Test
    public void registerTest() {
        OperationResult result =userDepartment.registerUser("marcorossi@prova.com", "passw0rd");
        Assert.assertEquals(Constants.REGISTRATION_SUCCESS, result.getMessage());
        Assert.assertTrue(result.isSuccessful());
    }

    @Test
    public void alreadyRegisteredTest() {
        userDepartment.registerUser("giuseppeverdi@prova.com", "passw0rd");
        OperationResult result = userDepartment.registerUser("GiUsEppEVeRDI@PROVA.cOm", "passw0rd2");
        Assert.assertEquals(Constants.EMAIL_ALREADY_USED, result.getMessage());
        Assert.assertFalse(result.isSuccessful());
    }

    @Test
    public void passwordIsTooShortTest() {
        OperationResult result = userDepartment.registerUser("francescobianchi@prova.com", "psw3");
        Assert.assertEquals(Constants.SHORT_PSW, result.getMessage());
        Assert.assertFalse(result.isSuccessful());
    }

    @Test
    public void passwordMustContainNumbersTest() {
        OperationResult result = userDepartment.registerUser("robertaverdi@prova.com", "password");
        Assert.assertEquals(Constants.ONLY_LETTERS_PSW, result.getMessage());
        Assert.assertFalse(result.isSuccessful());
    }

    @Test
    public void passwordMustContainLettersTest() {
        OperationResult result = userDepartment.registerUser("gianlucablu@prova.com", "123456");
        Assert.assertEquals(Constants.ONLY_NUMBERS_PSW, result.getMessage());
        Assert.assertFalse(result.isSuccessful());
    }

    @Test
    public void emailMustContainAtSymbolTest() {
        OperationResult result = userDepartment.registerUser("gianlucablu.prova.com", "password0");
        Assert.assertEquals(Constants.INVALID_EMAIL, result.getMessage());
        Assert.assertFalse(result.isSuccessful());
    }

    @Test
    public void correctLoginTest() {
        userDepartment.registerUser("simonerosso@prova.com", "passw0rd");
        OperationResult result = userDepartment.loginUser("simonerosso@prova.com", "passw0rd");
        Assert.assertEquals(Constants.LOGIN_SUCCESS, result.getMessage());
        Assert.assertTrue(result.isSuccessful());
    }

    @Test
    public void noEmailLoginTest() {
        userDepartment.registerUser("marcofirenze@prova.com", "passw0rd");
        OperationResult result = userDepartment.loginUser("marcopistoia@.com", "passw0rd");
        Assert.assertEquals(Constants.WRONG_EMAIL, result.getMessage());
        Assert.assertFalse(result.isSuccessful());
    }

    @Test
    public void wrongPasswordLoginTest() {
        userDepartment.registerUser("stefanorossi@prova.com", "passw0rd");
        OperationResult result = userDepartment.loginUser("stefanorossi@prova.com", "Passw0rd");
        Assert.assertEquals(Constants.WRONG_PSW, result.getMessage());
        Assert.assertFalse(result.isSuccessful());
    }

    @Test
    public void correctLogOutTest() {
        userDepartment.registerUser("deboragialla@prova.com", "passw0rd");
        userDepartment.loginUser("deboragialla@prova.com", "passw0rd");
        OperationResult result = userDepartment.logOut("deboragialla@prova.com");
        Assert.assertEquals(Constants.LOGOUT_SUCCESS, result.getMessage());
        Assert.assertTrue(result.isSuccessful());
    }

    @Test
    public void alreadyLogOutTest() {
        userDepartment.registerUser("deborablu@prova.com", "passw0rd");
        OperationResult result = userDepartment.logOut("deborablu@prova.com");
        Assert.assertEquals(Constants.ALREADY_LOGGED_OUT, result.getMessage());
        Assert.assertFalse(result.isSuccessful());
    }

    @Test
    public void invalidEmailLogOut() {
        userDepartment.registerUser("deborarossa@prova.com", "passw0rd");
        userDepartment.loginUser("deborarossa@prova.com", "passw0rd");
        OperationResult result = userDepartment.logOut("deboranera@prova.com");
        Assert.assertEquals(Constants.WRONG_EMAIL, result.getMessage());
        Assert.assertFalse(result.isSuccessful());
    }

    private final UserDepartment userDepartment = new UserDepartment();
}

/*
class UserDepartmentEventTester implements EventListener {

    UserDepartmentEventTester() {
        StoreEventManager.getInstance().subscribe(this, EventIdentifier.OPERATION_COMPLETED);
    }

    public void handleEvent(Event event) {
        EventIdentifier eventIdentifier = event.getIdentifier();

        if (eventIdentifier.equals(EventIdentifier.OPERATION_COMPLETED)) {
            EventMessage message = event.getMessage();
            this.result = message.getTextInfo(Constants.OPERATION_RESULT);
        }
    }

    public String getResult() {
        return result;
    }

    private String result;
}
 */