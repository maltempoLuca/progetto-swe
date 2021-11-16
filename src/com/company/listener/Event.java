package com.company.listener;


import com.company.store.eventsys.events.EventIdentifier;

public interface Event {

    public EventIdentifier getIdentifier();
    public String getTextInfo(String infoType);
    public Double getNumericInfo(String infoType);

}
