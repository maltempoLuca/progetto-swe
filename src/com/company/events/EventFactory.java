package com.company.events;

import com.company.constants.EventIdentifier;
import com.company.events.listener.Event;
import com.company.factory.Factory;

public class EventFactory implements Factory {

    private EventFactory() {};

    public static EventFactory getInstance() {
        if (instance == null)
            instance = new EventFactory();
        return instance;
    }

    @Override
    public Event factoryMethod(Object... params) {
        //params[0] = Event identifier
        //params[1] = String - If present must carry info about the event

        Event newEvent = null;

        if(params[0] instanceof EventIdentifier identifier) {
            switch (identifier) {
                case CHANGE_VIEW:
                    if (params[1] instanceof String) {
                        newEvent = createNavigationEvent((String) params[1]);
                    } else {
                        //TODO: throw exception
                    }
                    break;

                default:
                    newEvent = createSimpleEvent(identifier);
                    break;
            }
        } else {
            //TODO: throw exception
        }

        return newEvent;
    }


    NavigationEvent createNavigationEvent(String nextView) {
        NavigationEvent newNavigationEvent = new NavigationEvent(nextView);
        return newNavigationEvent;
    }

    SimpleEvent createSimpleEvent(EventIdentifier identifier) {
        SimpleEvent newSimpleEvent = new SimpleEvent(identifier);
        return newSimpleEvent;
    }

    private static EventFactory instance = null;
}
