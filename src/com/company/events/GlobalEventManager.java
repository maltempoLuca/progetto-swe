package com.company.events;

import com.company.constants.EventIdentifier;
import com.company.events.listener.Event;
import com.company.events.listener.EventListener;
import com.company.events.listener.EventManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GlobalEventManager implements EventManager {

    private GlobalEventManager() {
        //create list of listeners for each possible event, use event ids as keys

        for(EventIdentifier event : EventIdentifier.values()) {
            listeners.put(event, new ArrayList<>());
        }
    }

    public static GlobalEventManager getInstance() {
        if (instance == null)
            instance = new GlobalEventManager();
        return instance;
    }

    @Override
    public void subscribe(EventListener listener, EventIdentifier... targetEvents) {
        //subscribe listener to every specified event

        for(EventIdentifier event : targetEvents) {
            List<EventListener> subscribers = listeners.get(event);
            subscribers.add(listener);
        }
    }

    @Override
    public void unsubscribe(EventListener listener, EventIdentifier... targetEvents) {
        //unsubscribe listener from every specified event

        for(EventIdentifier event : targetEvents) {
            List<EventListener> subscribers = listeners.get(event);
            subscribers.remove(listener);
        }

    }

    @Override
    public void throwEvent(Event event) {
        //notify interested subscribers of new instance of event

        List<EventListener> subscribers = listeners.get(event.getIdentifier());
        for(EventListener listener : subscribers) {
            listener.handleEvent(event);
        }

    }

    private final Map<EventIdentifier, List<EventListener>> listeners = new HashMap<>();
    private static GlobalEventManager instance = null;
}
