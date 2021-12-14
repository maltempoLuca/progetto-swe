package com.company.store.events.requestevents;

import com.company.constants.Constants;
import com.company.exceptions.ExceptionMessages;
import com.company.exceptions.MissingInputException;
import com.company.store.controller.StoreRequest;
import org.junit.Assert;
import org.junit.Test;

public class RequestEventTest {
    RequestEvent tested = new RequestEvent(StoreRequest.REGISTER_REQUEST);

    @Test
    public void missingInputTest() {
        tested.addInput(Constants.USER_EMAIL, "email");
        Exception exception = Assert.assertThrows(MissingInputException.class, () -> {
            tested.getUserInput(Constants.USER_PSW);
        });
        Assert.assertEquals(ExceptionMessages.KEY_MISSING_INPUT + Constants.USER_PSW +
                ExceptionMessages.IN_REQUEST_MISSING_INPUT + StoreRequest.REGISTER_REQUEST.name(),
                exception.getMessage());
    }
}
