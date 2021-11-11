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
        readLog();
    }

    private void readTitle() {
        contents.append(title).append("\n");
    }

    private void readLog() {
        for(String logEntry : log) {
            contents.append(logEntry).append("\n");
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