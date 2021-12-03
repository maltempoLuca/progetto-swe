package com.company.store.controller;

import com.company.constants.Constants;
import com.company.constants.ShipmentState;
import com.company.constants.Utility;
import com.company.store.Shipment;
import com.company.view.View;

import java.util.*;
import java.util.concurrent.Semaphore;

public class UserView implements View {

    UserView(String title) {
        this.title = title;
    }

    @Override
    public void draw() {
        try {
            mutex.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        readContents();
        System.out.println(contents);
        mutex.release();
    }

    public void updateShipment(Shipment shipment) {
        try {
            mutex.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        String id = shipment.getId();
        String data = buildShipmentData(shipment);
        shipmentsData.put(id, new ViewElement(data));
        mutex.release();
    }

    public void addOptional(String optional) {
        try {
            mutex.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        optionals.add(new ViewElement(optional));
        mutex.release();
    }

    public void addLogEntry(Loggable loggable) {
        try {
            mutex.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        log.add(new ViewElement(Utility.timeToString() + loggable.getLogMessage() + "\n"));
        mutex.release();
    }

    private void readContents() {
        contents.setLength(0);
        readTitle();
        readAll(log);
        readAndClear(optionals);
        contents.append("\nSHIPMENTS:\n");
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
        data.append("- ID: ").append(shipment.getId()).append(", ");
        data.append("Sender: ").append(shipment.getSender()).append(", ");
        data.append("Receiver: ").append(shipment.getReceiver()).append(",\n");
        data.append("Sender Address: ").append(shipment.getSenderAddress()).append(",\n");
        data.append("Destination Address: ").append(shipment.getDestinationAddress()).append(", ");
        data.append("State: ").append(shipment.getState().getCurrentState()).append("\n");
        data.append("Contents: ").append(shipment.getContents()).append("\n");
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

    private final String title;
    private final Map<String, ViewElement> shipmentsData = new LinkedHashMap<>();
    private final List<ViewElement> optionals = new ArrayList<>();
    private final List<ViewElement> log = new ArrayList<>();
    private final StringBuilder contents = new StringBuilder();
    private final Semaphore mutex = new Semaphore(1);
}

/*
Sistema
    Log:
        - [10:22] Utente 1 ha provato a registrarsi ma la password non va bene
        - [10:23] Utente 2 si è registrato con mail: email, password: password
-------
Utente 2
    Log:
        - [10:23] Utente 2 si è registrato
        - [11:00] Utente 2 ha ordinato un paio di scarpe
        - [12:00] Utente 2 ha cambiato l'indirizzo di consegna
    Shipments:
        - #00001 Indrizzo: indirizzo nuovo, Contenuto: paio di scarpe, Stato: spedita

-------
Utente 3

----
Corrieri:
    Log:
        - Corriere 1 ha aggiornato lo stato sepdizione #00001
*/