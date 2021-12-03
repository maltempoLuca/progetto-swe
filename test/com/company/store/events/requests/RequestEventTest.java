package com.company.store.events.requests;

import com.company.constants.Constants;
import exceptions.ExceptionMessages;
import exceptions.MissingInputException;
import org.junit.Assert;
import org.junit.Test;

public class RequestEventTest {
    RequestEvent tested = new RequestEvent(RequestIdentifier.REGISTER_REQUEST);

    @Test
    public void missingInputTest() {
        tested.addInput(Constants.USER_EMAIL, "email");
        Exception exception = Assert.assertThrows(MissingInputException.class, () -> {
            tested.getUserInput(Constants.USER_PSW);
        });
        Assert.assertEquals(ExceptionMessages.KEY_MISSING_INPUT + Constants.USER_PSW +
                ExceptionMessages.IN_REQUEST_MISSING_INPUT + RequestIdentifier.REGISTER_REQUEST.name(),
                exception.getMessage());
    }
}
