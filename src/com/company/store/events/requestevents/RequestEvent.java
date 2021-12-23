package com.company.store.events.requestevents;

import com.company.constants.Constants;
import com.company.exceptions.StoreInitializationException;
import com.company.store.controller.StoreRequest;
import com.company.store.OperationResult;
import com.company.store.controller.Loggable;
import com.company.exceptions.MissingInputException;

import java.util.HashMap;
import java.util.Map;

public final class RequestEvent implements Loggable {

    public RequestEvent(StoreRequest request, String userId) {
        this.request = request;
        this.userId = userId;
    }

    public RequestEvent(StoreRequest request) {
        this.request = request;
        this.userId = Constants.UNLOGGED_USER;
    }

 public OperationResult execute() throws StoreInitializationException {
        OperationResult result;

        try {
            result = request.execute(this);
        } catch(MissingInputException e) {
            result = new OperationResult(e.getMessage(), false);
        }

      return result;
  }

    @Override
    public String getLogMessage() {
        StringBuilder logMessageBuilder = new StringBuilder();
        String requestedText = " has requested ";
        String inputText = " with input ";

        logMessageBuilder.append(userId).append(requestedText).append(request.name());

        if (!userInput.isEmpty()) {
            logMessageBuilder.append(inputText);

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

    public StoreRequest getRequest() {
        return request;
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
                throw new MissingInputException(inputKey, request.name());
            }

        return result;
    }


    private final StoreRequest request;
    private final String userId;
    private final Map<String, String> userInput = new HashMap<>();
}
