package com.company.listener;

import com.company.store.eventsys.events.EventIdentifier;

public interface EventManager {
    void subscribe(EventListener listener, EventIdentifier... targetEvents);
    void unsubscribe(EventListener listener, EventIdentifier... targetEvents);
    void notify(Event event);

}
