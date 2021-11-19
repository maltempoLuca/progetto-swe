package com.company.store.eventsys.events;

import com.company.listener.Event;
import java.util.HashMap;
import java.util.Map;

public class StoreEvent implements Event {
    //an event that carries a variety of information
    //each String of info is associated to a key that signals the type of stored info

    //TODO: delete this (and make constructor package-private?)
    public StoreEvent(EventIdentifier identifier, DataPair... textInfo) {
        //fills Map with info read from DataPair
        this.identifier = identifier;
        this.textInfo = new HashMap<>();
        this.numericInfo = new HashMap<>();

        for(DataPair entry : textInfo) {
            String infoType = entry.getDataType();
            String infoData = entry.getData();
            this.textInfo.put(infoType, infoData);
        }
    }

    public StoreEvent(EventIdentifier identifier, Map<String, String> textInfo, Map<String, Double> numericInfo) {
        this.identifier = identifier;
        this.textInfo = copyTextInfo(textInfo);
        this.numericInfo = copyNumericInfo(numericInfo);
    }

    public StoreEvent(StoreEvent toCopy) {
        //copy constructor

        this.identifier = toCopy.identifier;
        this.textInfo = copyTextInfo(toCopy.textInfo);
        this.numericInfo = copyNumericInfo(toCopy.numericInfo);
    }

    @Override
    public EventIdentifier getIdentifier() {
        return identifier;
    }

    @Override
    public String getTextInfo(String infoType) {
        //encapsulates Map get() method
        String result = textInfo.get(infoType);
        if(result == null) {
            //TODO: throw exception
        }

        return result;
    }

    @Override
    public Double getNumericInfo(String infoType) {
        Double result = numericInfo.get(infoType);
        if (result == null) {
            //TODO: throw exception
        }

        return numericInfo.get(infoType);  //TODO:: stessa cosa anche qui.
    }

    private Map<String, String> copyTextInfo(Map<String, String> textInfo) {
        Map<String, String> infoCopy = new HashMap<>();

        if(textInfo != null) {
            infoCopy.putAll(textInfo);
        }
//        infoCopy.putAll(textInfo);   //TODO:: guarda pietro, l'ide mi suggerisce questa istruzione che è piu chiara.


        return infoCopy;
    }

    private Map<String, Double> copyNumericInfo(Map<String, Double> numericInfo) {
        Map<String, Double> infoCopy = new HashMap<>();

        if (numericInfo != null) {
            for(Map.Entry<String, Double> numericEntry : numericInfo.entrySet()) {
                infoCopy.put(numericEntry.getKey(), numericEntry.getValue());
            }
        }
//        infoCopy.putAll(textInfo);   //TODO:: guarda pietro, l'ide mi suggerisce questa istruzione che è piu chiara.

        return infoCopy;
    }

    private final EventIdentifier identifier;
    private final Map<String, String> textInfo;
    private final Map<String, Double> numericInfo;
}
