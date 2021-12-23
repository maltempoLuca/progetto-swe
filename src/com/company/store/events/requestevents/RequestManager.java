package com.company.store.events.requestevents;

import com.company.store.controller.StoreRequest;
import com.company.store.events.subscription.SubscriptionManager;

import java.util.Collection;

public final class RequestManager {

    private RequestManager() {}

    public static RequestManager getInstance() {
        if (instance == null)
            instance = new RequestManager();
        return instance;
    }

    public void subscribe(RequestListener listener, StoreRequest... requestIds) {
        for(StoreRequest id : requestIds) {
            listenersManager.subscribe(listener, id);
        }
    }

    public void unsubscribe(RequestListener listener, StoreRequest... requestIds) {
        for(StoreRequest id : requestIds) {
            listenersManager.unsubscribe(listener, id);
        }
    }

    public void notify(RequestEvent request) {
        Collection<RequestListener> requestListeners = listenersManager.getSubscribers(request.getRequest());
        for(RequestListener listener : requestListeners) {
            listener.handleRequest(request);
        }
    }

    private static RequestManager instance = null;
    private final SubscriptionManager<StoreRequest, RequestListener> listenersManager = new SubscriptionManager<>();
}
