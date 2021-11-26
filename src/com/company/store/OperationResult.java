package com.company.store;

import com.company.constants.Utility;
import com.company.store.controller.Loggable;

public class OperationResult implements Loggable {

    public OperationResult(String message, boolean successful) {
        this.message = message;
        this.successful = successful;
    }

    @Override
    public String getLogMessage() {
        StringBuilder logMessageBuilder = new StringBuilder();
        if(successful) {
            logMessageBuilder.append("Operation successful with message: ");
        } else {
            logMessageBuilder.append("Operation failed with message: ");
        }

        logMessageBuilder.append("\"").append(message).append("\"");
        return Utility.timeToString() + logMessageBuilder;
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