package com.company.store.eventsys.events;

import com.company.listener.EventMessage;

import java.util.HashMap;
import java.util.Map;

public final class StoreMessage implements EventMessage {

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

        return result;
    }

    @Override
    public Double getNumericInfo(String infoType) {
        Double result = numericInfo.get(infoType);
        if (result == null) {
            //TODO: throw exception
        }

        return result;
    }

    private Map<String, String> copyTextInfo(Map<String, String> textInfo) {
        Map<String, String> infoCopy = new HashMap<>();

        if(textInfo != null) {
            infoCopy.putAll(textInfo);
        }



        return infoCopy;
    }

    private Map<String, Double> copyNumericInfo(Map<String, Double> numericInfo) {
        Map<String, Double> infoCopy = new HashMap<>();

        if (numericInfo != null) {
            infoCopy.putAll(numericInfo);
        }

        return infoCopy;
    }

    private final Map<String, String> textInfo;
    private final Map<String, Double> numericInfo;
}
