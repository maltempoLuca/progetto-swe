package com.company.store;

import com.company.constants.Constants;
import org.junit.Test;
import org.junit.Assert;

public class UserDepartmentTest {

    @Test
    public void registerTest() {
        String result = userDepartment.registerUser("marcorossi@prova.com", "passw0rd");
        Assert.assertEquals(Constants.SUCCESS, result);
    }

    @Test
    public void alreadyRegisteredTest() {
        userDepartment.registerUser("giuseppeverdi@prova.com", "passw0rd");

        String result = userDepartment.registerUser("GiUsEppEVeRDI@PROVA.cOm", "passw0rd2");
        Assert.assertEquals(Constants.EMAIL_ALREADY_USED, result);
    }

    @Test
    public void passwordIsTooShortTest() {
        String result = userDepartment.registerUser("francescobianchi@prova.com", "psw3");
        Assert.assertEquals(Constants.SHORT_PSW, result);
    }
    @Test
    public void passwordMustContainNumbersTest() {
        String result = userDepartment.registerUser("robertaverdi@prova.com", "password");
        Assert.assertEquals(Constants.ONLY_LETTERS_PSW, result);
    }

    @Test
    public void passwordMustContainLettersTest() {
        String result = userDepartment.registerUser("gianlucablu@prova.com", "123456");
        Assert.assertEquals(Constants.ONLY_NUMBERS_PSW, result);
    }

    @Test
    public void emailMustContainAtSymbol() {
        String result = userDepartment.registerUser("gianlucablu.prova.com", "password0");
        Assert.assertEquals(Constants.INVALID_EMAIL, result);
    }

    @Test
    public void correctLoginTest() {
        userDepartment.registerUser("simonerosso@prova.com", "passw0rd");
        String result = userDepartment.loginUser("simonerosso@prova.com", "passw0rd");
        Assert.assertEquals(Constants.SUCCESS, result);
    }

    @Test
    public void noEmailLoginTest() {
        userDepartment.registerUser("marcofirenze@prova.com", "passw0rd");
        String result = userDepartment.loginUser("marcopistoia@.com", "passw0rd");
        Assert.assertEquals(Constants.WRONG_EMAIL, result);
    }

    @Test
    public void wrongPasswordLoginTest() {
        userDepartment.registerUser("stefanorossi@prova.com", "passw0rd");
        String result = userDepartment.loginUser("stefanorossi@prova.com", "Passw0rd");
        Assert.assertEquals(Constants.WRONG_PSW, result);
    }

    private final UserDepartment userDepartment = UserDepartment.getInstance();
}
