package com.company.store.events.subscription;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class SubscriptionManager<Target, Subscriber> {

    public void subscribe(Subscriber sub, Target target) {
        //if target is not mapped to a list of subscribers create a new map entry for the target
        //else if subscriber is not yet subscribed to target add it to target's subscribers

        List<Subscriber> targetSubscribers = subscribers.get(target);
        boolean valid = false;

        if (targetSubscribers == null) {
            subscribers.put(target, new ArrayList<>());
            targetSubscribers = subscribers.get(target);
            valid = true;
        } else if (!targetSubscribers.contains(sub)) {
            valid = true;
        }

        if (valid) {
            targetSubscribers.add(sub);
        }
    }

    public void unsubscribe(Subscriber sub, Target target) {
        List<Subscriber> targetSubscribers = subscribers.get(target);

        if (targetSubscribers != null) {
            targetSubscribers.remove(sub);
            if (targetSubscribers.isEmpty()) {
                subscribers.remove(target);
            }
        }
    }

    public List<Subscriber> getSubscribers(Target target) {
        //create shallow copy and return it
        //copy is shallow because caller will need a reference to stored objects, not to their copies

        List<Subscriber> targetSubscribers = new ArrayList<>();
        List<Subscriber> toCopySubscribers = subscribers.get(target);

        if (toCopySubscribers != null)
            targetSubscribers.addAll(toCopySubscribers);

        return targetSubscribers;
    }

    private final Map<Target, List<Subscriber>> subscribers = new HashMap<>();
}
