package com.company.store.controller;

import com.company.constants.Constants;
import com.company.constants.ShipmentState;
import com.company.store.Shipment;
import com.company.view.View;

import java.util.*;

public class UserView implements View {

    UserView(String title) {
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
        shipmentsData.put(id, data);
    }

    public void addMessage(String message) {
        messages.add(message);
    }

    public void addOptional(String optional) {
        optionals.add(optional);
    }

    private void readContents() {
        contents.setLength(0);
        readTitle();
        readAndClear(optionals);
        readAll(shipmentsData.values());
        readAll(messages);
        contents.append("---------------------");
    }

    private void readTitle() {
        contents.append(title).append("\n");
    }

    private void readAll(Collection<String> stringCollection) {
        for(String element : stringCollection) {
            contents.append(element).append("\n");
        }
    }

    private void readAndClear(Collection<String> stringCollection) {
        readAll(stringCollection);
        stringCollection.clear();
    }

    private String buildShipmentData(Shipment shipment) {
        StringBuilder data = new StringBuilder();
        data.append("- ID: ").append(shipment.getId()).append(", ");
        data.append("Sender: ").append(shipment.getSender()).append(", ");
        data.append("Receiver: ").append(shipment.getReceiver()).append(", ");
        data.append("Sender Address: ").append(shipment.getSenderAddress()).append(", ");
        data.append("Destination Address: ").append(shipment.getDestinationAddress()).append(", ");
        data.append("State: ").append(shipment.getState().getCurrentState());
        data.append("\n");
        data.append("Contents: ").append(shipment.getContents()).append("\n");
        return data.toString();
    }

    private final String title;
    private final Map<String, String> shipmentsData = new LinkedHashMap<>();
    private final List<String > messages = new ArrayList<>();
    private final List<String> optionals = new ArrayList<>();
    private final StringBuilder contents = new StringBuilder();
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