package com.company.events.listener;

import com.company.constants.EventIdentifier;

public interface EventManager {

    void subscribe(EventListener listener, EventIdentifier... targetEvents);
    void unsubscribe(EventListener listener, EventIdentifier... targetEvents);
    void throwEvent(Event event);

}
