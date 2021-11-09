package com.company.events;

import com.company.constants.EventIdentifier;
import com.company.events.listener.Event;

public class NavigationEvent implements Event {

    NavigationEvent(String nextView) {
        this.nextView = nextView;
    }

    @Override
    public EventIdentifier getIdentifier() {
        return identifier;
    }

   @Override
    public String getInfo() {
        return nextView;
    }

    private final EventIdentifier identifier = EventIdentifier.CHANGE_VIEW;
    private final String nextView;
}
