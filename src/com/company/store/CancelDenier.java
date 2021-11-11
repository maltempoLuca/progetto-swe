package com.company.store;

import com.company.store.eventsys.events.DataPair;
import com.company.store.eventsys.events.StoreEvent;
import com.company.store.eventsys.management.StoreEventManager;

public class CancelDenier implements CancelBehavior {

    private CancelDenier() {

    }

    public static CancelDenier getInstance() {
        if (instance == null)
            instance = new CancelDenier();
        return instance;
    }

    @Override
    public boolean cancelShipment(Shipment shipment) {
        return false;
    }

    /*
    public boolean _cancelShipment(Shipment shipment) {
        DataPair pippo = new DataPair("ID_SPEDIZIONE", shipment.getId());
        DataPair motivo = new DataPair("REASON", "perchè la spedizione è partita");
        StoreEvent event = new StoreEvent("CANCEL_REFUSED", pippo, motivo);
        StoreEventManager.getInstance().notify(event);

        //---
        switch(event.getIdentifier()) {

            case "CANCEL_REFUSED":
                String idSpedizione = event.getInfo("ID_SPEDIZIONE");
                String motivoCancellazione = event.getInfo("REASON");
                String daStampare = "L'utente ha provato a cancellare la spedizione numero: " + idSpedizione +
                        "ma la cancellazione è stata rifiutata" + motivoCancellazione;
                break;
            default:
        }

        return false;
    }
    */
    private static CancelDenier instance = null;
}
