package com.company.store.events.view;

import com.company.store.events.requests.RequestEvent;

public interface ViewEventListener {
    void handleViewEvent(ViewEvent event);
}
