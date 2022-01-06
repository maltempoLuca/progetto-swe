package com.company.store.view;

import com.company.constants.Constants;
import com.company.store.controller.Loggable;
import com.company.store.shipping.Shipment;
import com.company.store.view.View;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.Semaphore;

public final class UserView implements View {

    public UserView(String title) {
        this.title = title;
    }

    @Override
    public void draw() {
        readContents();
        System.out.println(contents);
    }

    public void updateShipment(Shipment shipment) {
        String id = shipment.getId();
        String data = buildShipmentData(shipment);
        shipmentsData.put(id, new ViewElement(data));
    }

    public void addOptional(String optional) {
        optionals.add(new ViewElement(optional));
    }

    public void addLogEntry(Loggable loggable) {
        log.add(new ViewElement(timeToString() + loggable.getLogMessage() + "\n"));
    }

    private void readContents() {
        contents.setLength(0);
        readTitle();
        readAll(log);
        readAndClear(optionals);
        contents.append("\n" + Constants.SHIPMENTS + ":\n");
        readAll(shipmentsData.values());
        contents.append(Constants.SEPARATOR);
    }

    private void readTitle() {
        contents.append(title).append("\n");
    }

    private void readAll(Collection<ViewElement> stringCollection) {
        for (ViewElement element : stringCollection) {
            //element = String.join(("\u0332",  element.split("",-1));
            if(!element.printed) {
                contents.append(Constants.ANSI_BLACK_BOLD).append(Constants.ANSI_BLACK_UNDERLINED);
                element.printed = true;
            }
            contents.append(element).append(Constants.ANSI_RESET).append("\n");

        }
    }

    private void readAndClear(Collection<ViewElement> stringCollection) {
        readAll(stringCollection);
        stringCollection.clear();
    }

    private String buildShipmentData(Shipment shipment) {
        StringBuilder data = new StringBuilder();
        String idText = "- ID: ";
        String senderText = "Sender: ";
        String receiverText = "Receiver: ";
        String senderaddressText = "Sender Address: ";
        String destinationAddressText = "Destination Address: ";
        String stateText = "State: ";
        String contentsText = "Contents: ";

        data.append(idText).append(shipment.getId()).append(", ");
        data.append(senderText).append(shipment.getSender()).append(", ");
        data.append(receiverText).append(shipment.getReceiver()).append(",\n");
        data.append(senderaddressText).append(shipment.getSenderAddress()).append(",\n");
        data.append(destinationAddressText).append(shipment.getDestinationAddress()).append(", ");
        data.append(stateText).append(shipment.getState().getCurrentState()).append("\n");
        data.append(contentsText).append(shipment.getContents()).append("\n");
        return data.toString();
    }

    private static class ViewElement {
        private ViewElement(String content) {
            this.content = content;
            this.printed = false;
        }

        public final String toString() {
            return content;
        }

        private Boolean printed;
        private final String content;
    }

    private String timeToString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss.SSS");
        LocalTime time = LocalTime.now();
        return "[" + time.format(formatter) + "] ";
    }


    private final String title;
    private final Map<String, ViewElement> shipmentsData = new LinkedHashMap<>();
    private final List<ViewElement> optionals = new ArrayList<>();
    private final List<ViewElement> log = new ArrayList<>();
    private final StringBuilder contents = new StringBuilder();
}