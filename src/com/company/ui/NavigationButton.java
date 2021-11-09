package com.company.ui;
import com.company.events.listener.Event;

public final class NavigationButton extends Button {
    //a button associated to a NavigationEvent

    public NavigationButton(Event thrownEvent, String text) {
        super(thrownEvent);
        this.text = text;
    }

    @Override
    public String getText() {
        return text;
    }

    private final String text;
}
