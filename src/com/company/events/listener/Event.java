package com.company.events.listener;


import com.company.constants.EventIdentifier;

public interface Event {

    public EventIdentifier getIdentifier();
    public default String getInfo() {
        throw new UnsupportedOperationException();

    };
}
