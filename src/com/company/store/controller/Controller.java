package com.company.store.controller;

import com.company.store.eventsys.events.EventIdentifier;
import com.company.listener.Event;
import com.company.listener.EventListener;

import java.util.HashMap;
import java.util.Map;

public class Controller implements EventListener {

    @Override
    public void handleEvent(Event event) {
        //manage every type of events that could happen

        EventIdentifier eventIdentifier = event.getIdentifier();

        switch (eventIdentifier) {
            case CHANGE_ADDRESS:
                break;

            case CHANGE_ADDRESS_REFUSED:
                break;

            case CANCEL:
                break;

            case CANCEL_REFUSED:
                break;

            case RETURN:
                break;

            case RETURN_REFUSED:
                break;
        }

    }

    private void clearViews() {
        //delete all prints from console

        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    private void refreshViews() {
        clearViews();
        renderViews();
    }

    private void renderViews() {
        for(HistoryView view : views.values()) {
            view.render();
        }
    }

    private final Map<String, HistoryView> views = new HashMap<>();
}
