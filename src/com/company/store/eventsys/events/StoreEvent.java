package com.company.store.eventsys.events;

import com.company.listener.Event;
import java.util.HashMap;
import java.util.Map;

public class StoreEvent implements Event {
    //an event that carries a variety of information
    //each String of info is associated to a key that signals the type of stored info

    //TODO: delete this (and make constructore package-private?)
    public StoreEvent(EventIdentifier identifier, DataPair... textInfo) {
        //fills Map with info read from DataPair

        this.identifier = identifier;

        for(DataPair entry : textInfo) {
            String infoType = entry.getDataType();
            String infoData = entry.getData();
            this.textInfo.put(infoType, infoData);
        }
    }

    public StoreEvent(EventIdentifier identifier, Map<String, String> textInfo, Map<String, Double> numericInfo) {
        this.identifier = identifier;

        if(textInfo != null) {
            for(Map.Entry<String, String> textEntry : textInfo.entrySet())
            this.textInfo.put(textEntry.getKey(), textEntry.getValue());
        }

        if (numericInfo != null) {
            for(Map.Entry<String, Double> numericEntry : numericInfo.entrySet()) {
                this.numericInfo.put(numericEntry.getKey(), numericEntry.getValue());
            }
        }
    }

    @Override
    public EventIdentifier getIdentifier() {
        return identifier;
    }

    @Override
    public String getTextInfo(String infoType) {
        //encapsulates Map get() method
        return textInfo.get(infoType);
    }

    @Override
    public double getNumericInfo(String infoType) {
        return numericInfo.get(infoType);
    }

    private final EventIdentifier identifier;
    private final Map<String, String> textInfo = new HashMap<>();
    private final Map<String, Double> numericInfo = new HashMap<>();
}
