package com.company.store.eventsys.events;

import com.company.listener.Event;
import com.company.listener.EventMessage;

import java.util.HashMap;
import java.util.Map;

public class StoreEvent implements Event {
    //an event that carries a variety of information
    //each String of info is associated to a key that signals the type of stored info

    //TODO: delete this (and make constructor package-private?)
    public StoreEvent(EventIdentifier identifier, DataPair... textInfo) {
        //fills Map with info read from DataPair
        this.identifier = identifier;
        Map<String, String> textInfoMap = new HashMap<>();

        for(DataPair entry : textInfo) {
            String infoType = entry.getDataType();
            String infoData = entry.getData();
            textInfoMap.put(infoType, infoData);
        }

        this.message = new StoreMessage(textInfoMap, null);
    }

    public StoreEvent(EventIdentifier identifier, Map<String, String> textInfo, Map<String, Double> numericInfo) {
        this.identifier = identifier;
        this.message = new StoreMessage(textInfo, numericInfo);
    }

    public StoreEvent(StoreEvent toCopy) {
        //copy constructor

        this.identifier = toCopy.identifier;
        this.message = new StoreMessage(toCopy.message);
    }

    @Override
    public EventIdentifier getIdentifier() {
        return identifier;
    }

    @Override
    public EventMessage getMessage() {
        return new StoreMessage(message);
    }

    private final EventIdentifier identifier;
    private final StoreMessage message;
}
