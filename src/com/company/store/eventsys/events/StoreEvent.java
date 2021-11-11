package com.company.store.eventsys.events;

import com.company.listener.Event;
import java.util.HashMap;
import java.util.Map;

public class StoreEvent implements Event {
    //an event that carries a variety of information
    //each String of info is associated to a key that signals the type of stored info

    public StoreEvent(EventIdentifier identifier, DataPair... info) {
        //fills Map with info read from DataPair

        this.identifier = identifier;

        for(DataPair entry : info) {
            String infoType = entry.getDataType();
            String infoData = entry.getData();
            this.info.put(infoType, infoData);
        }
    }

    @Override
    public EventIdentifier getIdentifier() {
        return identifier;
    }

    @Override
    public String getInfo(String infoType) {
        //encapsulates Map get() method
        return info.get(infoType);
    }

    private final EventIdentifier identifier;
    private final Map<String, String> info = new HashMap<>();
}
