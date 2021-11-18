package com.company.store.eventsys.events;

import com.company.listener.EventMessage;

import java.util.HashMap;
import java.util.Map;

public class StoreMessage implements EventMessage {

    StoreMessage(Map<String, String> textInfo, Map<String, Double> numericInfo) {
        this.textInfo = copyTextInfo(textInfo);
        this.numericInfo = copyNumericInfo(numericInfo);
    }

    StoreMessage(StoreMessage toCopy) {
        //copy constructor
        this.textInfo = copyTextInfo(toCopy.textInfo);
        this.numericInfo = copyNumericInfo(toCopy.numericInfo);
    }

    @Override
    public String getTextInfo(String infoType) {
        //encapsulates Map get() method
        String result = textInfo.get(infoType);
        if(result == null) {
            //TODO: throw exception
        }

        return textInfo.get(infoType);
    }

    @Override
    public Double getNumericInfo(String infoType) {
        Double result = numericInfo.get(infoType);
        if (result == null) {
            //TODO: throw exception
        }

        return numericInfo.get(infoType);
    }

    private Map<String, String> copyTextInfo(Map<String, String> textInfo) {
        Map<String, String> infoCopy = new HashMap<>();

        if(textInfo != null) {
            for(Map.Entry<String, String> textEntry : textInfo.entrySet())
                infoCopy.put(textEntry.getKey(), textEntry.getValue());
        }

        return infoCopy;
    }

    private Map<String, Double> copyNumericInfo(Map<String, Double> numericInfo) {
        Map<String, Double> infoCopy = new HashMap<>();

        if (numericInfo != null) {
            for(Map.Entry<String, Double> numericEntry : numericInfo.entrySet()) {
                infoCopy.put(numericEntry.getKey(), numericEntry.getValue());
            }
        }

        return infoCopy;
    }

    private final Map<String, String> textInfo;
    private final Map<String, Double> numericInfo;
}
