package com.company.store;

import com.company.constants.Constants;
import com.company.store.controller.Loggable;

public final class OperationResult implements Loggable {
    //carries outcome of a request as a boolean value
    //also carries a message for the user

    public OperationResult(String message, boolean successful) {
        this.message = message;
        this.successful = successful;
    }

    @Override
    public String getLogMessage() {
        StringBuilder logMessageBuilder = new StringBuilder();
        if(successful) {
            logMessageBuilder.append(Constants.SUCCESSFULL_OPERATION_MESSAGE);
        } else {
            logMessageBuilder.append(Constants.FAILED_OPERATION_MESSAGE);
        }

        logMessageBuilder.append("\"").append(message).append("\"");
        return logMessageBuilder.toString();
    }

    public String getMessage() {
        return message;
    }

    public boolean isSuccessful() {
        return successful;
    }

    private final String message;
    private final boolean successful;
}
