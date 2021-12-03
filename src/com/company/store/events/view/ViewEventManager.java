package com.company.store.events.view;

import com.company.store.events.requests.RequestEvent;
import com.company.store.events.requests.RequestIdentifier;
import com.company.store.events.requests.RequestListener;
import com.company.store.events.subscription.SubscriptionManager;

import java.util.Collection;

public class ViewEventManager {

    private ViewEventManager() {}

    public static ViewEventManager getInstance() {
        if (instance == null)
            instance = new ViewEventManager();
        return instance;
    }

    public void subscribe(ViewEventListener listener, ViewEventIdentifier... requestIds) {
        for(ViewEventIdentifier id : requestIds) {
            listenersManager.subscribe(listener, id);
        }
    }

    public void unsubscribe(ViewEventListener listener, ViewEventIdentifier... requestIds) {
        for(ViewEventIdentifier id : requestIds) {
            listenersManager.unsubscribe(listener, id);
        }
    }

    public void notify(ViewEvent event) {
        Collection<ViewEventListener> requestListeners = listenersManager.getSubscribers(event.getId());
        for(ViewEventListener listener : requestListeners) {
            listener.handleViewEvent(event);
        }
    }

    private static ViewEventManager instance = null;
    private final SubscriptionManager<ViewEventIdentifier, ViewEventListener> listenersManager = new SubscriptionManager<>();
}
