package com.company.store;
import org.junit.Test;
import org.junit.Assert;

public class UserDepartmentTest {

    @Test
    public void registerTest() {
        int result = userDepartment.registerUser("email@diprova.com", "passw0rd");
        Assert.assertEquals(1, result);
    }

    @Test
    public void alreadyRegisteredTest() {
        userDepartment.registerUser("email@diprova1.com", "passw0rd");

        int result = userDepartment.registerUser("EmaIl@diprOVA1.cOm", "passw0rd2");
        Assert.assertEquals(0, result);
    }

    @Test
    public void correctLoginTest() {
        userDepartment.registerUser("email@diprova2.com", "passw0rd");
        int result = userDepartment.loginUser("email@diprova2.com", "passw0rd");
        Assert.assertEquals(1, result);
    }

    @Test
    public void noEmailLoginTest() {
        userDepartment.registerUser("email@diprova3.com", "passw0rd");
        int result = userDepartment.loginUser("email@diprova.m", "passw0rd");
        Assert.assertEquals(0, result);
    }

    @Test
    public void wrongPasswordLoginTest() {
        userDepartment.registerUser("email@diprova4.com", "passw0rd");
        int result = userDepartment.loginUser("email@diprova.com", "Passw0rd");
        Assert.assertEquals(0, result);
    }

    private final UserDepartment userDepartment = UserDepartment.getInstance();
}
