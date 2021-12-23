package com.company.store.events.requestevents;

import com.company.constants.Constants;
import com.company.store.controller.RequestIdentifier;
import com.company.store.events.OperationResult;
import com.company.store.controller.Loggable;
import com.company.exceptions.MissingInputException;

import java.util.HashMap;
import java.util.Map;

public final class RequestEvent implements Loggable {

    public RequestEvent(RequestIdentifier id, String userId) {
        this.id = id;
        this.userId = userId;
    }

    public RequestEvent(RequestIdentifier id) {
        this.id = id;
        this.userId = Constants.UNLOGGED_USER;
    }

 public OperationResult execute() {
        OperationResult result;

        try {
            result = id.execute(this);
        } catch(MissingInputException e) {
            result = new OperationResult(e.getMessage(), false);
        }

      return result;
  }

    @Override
    public String getLogMessage() {
        StringBuilder logMessageBuilder = new StringBuilder();
        logMessageBuilder.append(userId).append(" has requested ").append(id.name());

        if (!userInput.isEmpty()) {
            logMessageBuilder.append(" with input ");

            for (Map.Entry<String, String> input : userInput.entrySet()) {
                logMessageBuilder.append("[").append(input.getKey()).append(": ").append(input.getValue()).append("]");
            }
        }

        return logMessageBuilder.toString();
    }

    public RequestEvent addInput(String type, String data) {
        userInput.put(type, data);
        return this;
    }

    public int parseInput(String inputKey) {
        String stringLiteral = userInput.get(inputKey);
        return Integer.parseInt(stringLiteral);
    }

    public RequestIdentifier getId() {
        return id;
    }

    public String getUserId() {
        String result = userId;
        if (userInput.containsKey(Constants.USER_EMAIL))
            result = userInput.get(Constants.USER_EMAIL).toLowerCase();
        return result;
    }

    public String getUserInput(String inputKey) throws MissingInputException {

            String result = userInput.get(inputKey);
            if (result == null) {
                throw new MissingInputException(inputKey, id.name());
            }

        return result;
    }


    private final RequestIdentifier id;
    private final String userId;
    private final Map<String, String> userInput = new HashMap<>();
}
