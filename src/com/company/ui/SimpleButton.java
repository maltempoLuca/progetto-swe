package com.company.ui;

import com.company.events.listener.Event;

public final class SimpleButton extends Button {
    //a button associated to a SimpleEvent

    public SimpleButton(Event thrownEvent, String text) {
        super(thrownEvent);
        this.text = text;
    }

    public final String getText() {
        return text;
    };

    private final String text;
}
