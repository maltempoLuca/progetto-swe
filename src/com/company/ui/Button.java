package com.company.ui;

import com.company.events.listener.Event;
import com.company.events.GlobalEventManager;

public abstract class Button {

    Button(Event thrownEvent) {
        this.thrownEvent = thrownEvent;
    }

    public void onClick() {
        GlobalEventManager.getInstance().throwEvent(thrownEvent);
    };

    public abstract String getText();

    Event thrownEvent;
}
