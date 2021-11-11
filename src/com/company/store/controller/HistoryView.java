package com.company.store.controller;

import com.company.view.View;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HistoryView implements View {

    @Override
    public void render() {
        System.out.println(contents);
    }

    @Override
    public void clear() {
        contents.setLength(0);
    }

    private void readContents() {
        contents.setLength(0);
        readTitle();
        readShipments();
        readOperations();
    }

    private void readTitle() {
        contents.append(title).append("\n");
    }

    private void readOperations() {
        for(String operation : log) {
            contents.append(operation).append("\n");
        }
    }

    private void readShipments() {
        for (String shipmentData : shipmentsData.values()) {
            contents.append(shipmentData).append("\n");
        }
    }

    private String title;
    private final Map<String, String> shipmentsData = new HashMap<>();
    private final List<String> log = new ArrayList<>();
    private final StringBuilder contents = new StringBuilder();
}
