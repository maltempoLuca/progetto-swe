package com.company.listener;

public interface EventManager {
    void subscribe(EventListener listener, String... targetEvents);
    void unsubscribe(EventListener listener, String... targetEvents);
    void notify(Event event);

}
