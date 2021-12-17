package com.company.store.events;

import com.company.constants.Constants;
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
