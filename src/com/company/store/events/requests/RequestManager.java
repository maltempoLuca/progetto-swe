package com.company.store.events.requests;

import com.company.store.events.subscription.SubscriptionManager;

import java.util.Collection;

public class RequestManager {

    private RequestManager() {}

    public static RequestManager getInstance() {
        if (instance == null)
            instance = new RequestManager();
        return instance;
    }

    public void subscribe(RequestListener listener, RequestIdentifier... requestIds) {
        for(RequestIdentifier id : requestIds) {
            listenersManager.subscribe(listener, id);
        }
    }

    public void unsubscribe(RequestListener listener, RequestIdentifier... requestIds) {
        for(RequestIdentifier id : requestIds) {
            listenersManager.unsubscribe(listener, id);
        }
    }

    public void notify(RequestEvent request) {
        Collection<RequestListener> requestListeners = listenersManager.getSubscribers(request.getId());
        for(RequestListener listener : requestListeners) {
            listener.handleRequest(request);
        }
    }

    private static RequestManager instance = null;
    private final SubscriptionManager<RequestIdentifier, RequestListener> listenersManager = new SubscriptionManager<>();
}
