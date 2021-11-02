package com.company.ui;

import com.company.constants.ButtonEvent;

public class Button {

    public Button(ButtonEvent event, String text) {
        this.event = event;
        this.text = text;
    }

    public final ButtonEvent getEvent() {
        return event;
    };

    public final String getText() {
        return text;
    };

    private final ButtonEvent event;
    private final String text;
}
