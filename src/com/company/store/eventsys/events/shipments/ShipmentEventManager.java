package com.company.store.eventsys.events.shipments;

import com.company.store.eventsys.events.subscription.SubscriptionManager;

import java.util.Collection;

public class ShipmentEventManager {

    private ShipmentEventManager() {}

    public static ShipmentEventManager getInstance() {
        if (instance == null)
            instance = new ShipmentEventManager();
        return instance;
    }

    public void subscribe(ShipmentEventListener listener, ShipEventIdentifier... requestIds) {
        for(ShipEventIdentifier id : requestIds) {
            listenersManager.subscribe(listener, id);
        }
    }

    public void unsubscribe(ShipmentEventListener listener, ShipEventIdentifier... requestIds) {
        for(ShipEventIdentifier id : requestIds) {
            listenersManager.unsubscribe(listener, id);
        }
    }

    public void notify(ShipmentEvent event) {
        Collection<ShipmentEventListener> requestListeners = listenersManager.getSubscribers(event.getId());
        for(ShipmentEventListener listener : requestListeners) {
            listener.handleEvent(event);
        }
    }

    private static ShipmentEventManager instance = null;
    private final SubscriptionManager<ShipEventIdentifier, ShipmentEventListener> listenersManager = new SubscriptionManager<>();
}
