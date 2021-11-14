package com.company.store.eventsys.management;

import com.company.listener.Event;
import com.company.listener.EventListener;
import com.company.listener.EventManager;
import com.company.store.eventsys.events.EventIdentifier;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class StoreEventManager implements EventManager {

    private StoreEventManager() {};

    @Override
    public void subscribe(EventListener listener, EventIdentifier... targetEvents) {
        //subscribe listener to every specified event
        //if specified event is not yet managed create a new list of subscribers for the event

        for(EventIdentifier eventIdentifier : targetEvents) {
            List<EventListener> subscribers = listeners.get(eventIdentifier);

            if (subscribers == null) {
                listeners.put(eventIdentifier, new ArrayList<>());
                subscribers = listeners.get(eventIdentifier);
            }
            subscribers.add(listener);
        }
    }

    @Override
    public void unsubscribe(EventListener listener, EventIdentifier... targetEvents) {
        //unsubscribe listener to every specified event
        //if an event has no more subscribers remove its listeners entry

        for(EventIdentifier eventIdentifier : targetEvents) {
            List<EventListener> subscribers = listeners.get(eventIdentifier);
            subscribers.remove(listener);

            if (subscribers.isEmpty()) {
                listeners.remove(eventIdentifier);
            }
        }

    }

    @Override
    public void notify(Event event) {

        for(EventListener listener : listeners.get(event.getIdentifier())) {
            listener.handleEvent(event);
        }

    }

    public static StoreEventManager getInstance() {
        if (instance == null)
            instance = new StoreEventManager();
        return instance;
    }

    private final Map<EventIdentifier, List<EventListener>> listeners = new HashMap<>();
    private static StoreEventManager instance = null;
}
