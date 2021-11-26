package com.company.store.controller;

import com.company.view.View;

import java.util.ArrayList;
import java.util.List;

public class LogView implements View {

    @Override
    public void draw() {
        System.out.println("LOG:\n");
        for(String logEntry : log) {
            System.out.println(logEntry);
        }
        System.out.println("---------------------");
    }

    public void addLogEntry(Loggable loggable) {
        log.add(loggable.getLogMessage() + "\n");
    }

    private final List<String> log = new ArrayList<>();
}
