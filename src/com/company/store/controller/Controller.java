package com.company.store.controller;

import com.company.constants.Constants;
import com.company.store.eventsys.events.EventIdentifier;
import com.company.listener.Event;
import com.company.listener.EventListener;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

public class Controller implements EventListener {

    @Override
    public void handleEvent(Event event) {
        String logEntry;
        EventIdentifier eventIdentifier = event.getIdentifier();

        switch (eventIdentifier) {
            case PURCHASE_COMPLETED:
                logEntry = buildLogEntry("L'utente " + event.getInfo(Constants.USEREMAIL) + " ha eseguito un ordine");
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

    private String timeToString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        LocalTime time = LocalTime.now();
        String formattedTime = "[" + time.format(formatter) +"]";
        return formattedTime;
    }

    private String buildLogEntry(String message) {
        return timeToString() + ": " + message;
    }

    private final Map<String, HistoryView> views = new HashMap<>();
}
