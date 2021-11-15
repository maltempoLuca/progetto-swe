package com.company.store.eventsys.events;

import com.company.store.purchase.PurchasingDepartment;

import java.util.HashMap;
import java.util.Map;

public class EventBuilder {

    public static EventBuilder buildStoreEvent() {
        if (instance == null)
            instance = new EventBuilder();
        return instance;
    }

    public EventBuilder withInfo(String infoType, String info) {
        textData.put(infoType, info);
        return this;
    }

    public EventBuilder withInfo(String infoType, Double info) {
        numericData.put(infoType, info);
        return this;
    }

    public StoreEvent withIdentifier(EventIdentifier identifier) {
        return new StoreEvent(identifier, textData, numericData);
    }

    private static EventBuilder instance = null;
    private final Map<String, String> textData = new HashMap<>();
    private final Map<String, Double> numericData = new HashMap<>();
}
