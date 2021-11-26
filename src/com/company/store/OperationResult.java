package com.company.store;

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
            logMessageBuilder.append(" operation successful with message: ");
        } else {
            logMessageBuilder.append(" operation failed with message: ");
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
