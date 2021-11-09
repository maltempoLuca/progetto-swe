package com.company.events;

import com.company.constants.EventIdentifier;
import com.company.events.listener.Event;

public class SimpleEvent implements Event {
    //an event without additional info
    SimpleEvent(EventIdentifier identifier) {
        this.identifier = identifier;
    }

    @Override
    public EventIdentifier getIdentifier() {
        return identifier;
    }

    private EventIdentifier identifier;
}
