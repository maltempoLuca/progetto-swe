package com.company.store.eventsys.events.requests;

import com.company.store.controller.Loggable;

import java.util.HashMap;
import java.util.Map;

public class RequestEvent implements Loggable {

    public RequestEvent(RequestIdentifier id, String userId) {
        this.id = id;
        this.userId = userId;
    }

    public RequestEvent(RequestIdentifier id) {
        this.id = id;
        this.userId = "unlogged user";
    }


    @Override
    public String getLogMessage() {
        StringBuilder logMessageBuilder = new StringBuilder();
        logMessageBuilder.append(userId).append(" has requested ").append(id.name());

        if(!userInput.isEmpty()) {
            logMessageBuilder.append(" with input ");

            for(Map.Entry<String, String> input : userInput.entrySet()) {
                logMessageBuilder.append("[").append(input.getKey()).append(": ").append(input.getValue()).append("]");
            }
        }

        return logMessageBuilder.toString();
    }

    public RequestEvent addInput(String type, String data) {
        userInput.put(type, data);
        return this;
    }

    public RequestIdentifier getId() {
        return id;
    }

    public String getUserId() {
        return userId;
    }

    public String getUserInput(String inputKey) {
        String result = userInput.get(inputKey);
        if(result == null) {
            //TODO: throw exception
        }
        return result;
    }



    private final RequestIdentifier id;
    private final String userId;
    private final Map<String, String> userInput = new HashMap<>();
}
