package com.company.store;

import com.company.constants.Constants;
import com.company.listener.Event;
import com.company.listener.EventListener;
import com.company.listener.EventMessage;
import com.company.store.eventsys.events.EventIdentifier;
import com.company.store.eventsys.management.StoreEventManager;
import org.junit.Test;
import org.junit.Assert;

public class UserDepartmentTest {
    UserDepartmentEventTester eventTester = new UserDepartmentEventTester();

    @Test
    public void registerTest() {
        userDepartment.registerUser("marcorossi@prova.com", "passw0rd");
        Assert.assertEquals(Constants.REGISTRATION_SUCCESS, eventTester.getResult());
    }

    @Test
    public void alreadyRegisteredTest() {
        userDepartment.registerUser("giuseppeverdi@prova.com", "passw0rd");
        userDepartment.registerUser("GiUsEppEVeRDI@PROVA.cOm", "passw0rd2");
        Assert.assertEquals(Constants.EMAIL_ALREADY_USED, eventTester.getResult());
    }

    @Test
    public void passwordIsTooShortTest() {
        userDepartment.registerUser("francescobianchi@prova.com", "psw3");
        Assert.assertEquals(Constants.SHORT_PSW, eventTester.getResult());
    }

    @Test
    public void passwordMustContainNumbersTest() {
        userDepartment.registerUser("robertaverdi@prova.com", "password");
        Assert.assertEquals(Constants.ONLY_LETTERS_PSW, eventTester.getResult());
    }

    @Test
    public void passwordMustContainLettersTest() {
        userDepartment.registerUser("gianlucablu@prova.com", "123456");
        Assert.assertEquals(Constants.ONLY_NUMBERS_PSW, eventTester.getResult());
    }

    @Test
    public void emailMustContainAtSymbol() {
        userDepartment.registerUser("gianlucablu.prova.com", "password0");
        Assert.assertEquals(Constants.INVALID_EMAIL, eventTester.getResult());
    }

    @Test
    public void correctLoginTest() {
        userDepartment.registerUser("simonerosso@prova.com", "passw0rd");
        userDepartment.loginUser("simonerosso@prova.com", "passw0rd");
        Assert.assertEquals(Constants.LOGIN_SUCCESS, eventTester.getResult());
    }

    @Test
    public void noEmailLoginTest() {
        userDepartment.registerUser("marcofirenze@prova.com", "passw0rd");
        userDepartment.loginUser("marcopistoia@.com", "passw0rd");
        Assert.assertEquals(Constants.WRONG_EMAIL, eventTester.getResult());
    }

    @Test
    public void wrongPasswordLoginTest() {
        userDepartment.registerUser("stefanorossi@prova.com", "passw0rd");
        userDepartment.loginUser("stefanorossi@prova.com", "Passw0rd");
        Assert.assertEquals(Constants.WRONG_PSW, eventTester.getResult());
    }

    @Test
    public void correctLogOutTest() {
        userDepartment.registerUser("deboragialla@prova.com", "passw0rd");
        userDepartment.loginUser("deboragialla@prova.com", "passw0rd");
        userDepartment.logOut("deboragialla@prova.com");
        Assert.assertEquals(Constants.LOGOUT_SUCCESS, eventTester.getResult());
    }

    @Test
    public void alreadyLogOutTest() {
        userDepartment.registerUser("deborablu@prova.com", "passw0rd");
        userDepartment.logOut("deborablu@prova.com");
        Assert.assertEquals(Constants.ALREADY_LOGGED_OUT, eventTester.getResult());
    }

    @Test
    public void invalidEmailLogOut() {
        userDepartment.registerUser("deborarossa@prova.com", "passw0rd");
        userDepartment.loginUser("deborarossa@prova.com", "passw0rd");
        userDepartment.logOut("deboranera@prova.com");
        Assert.assertEquals(Constants.WRONG_EMAIL, eventTester.getResult());
    }


    private final UserDepartment userDepartment = UserDepartment.getInstance();
}

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