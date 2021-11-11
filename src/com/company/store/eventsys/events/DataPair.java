package com.company.store.eventsys.events;

public class DataPair {
    //a struct to hold data before storing them into a StoreEvent
    //this is only used to simplify StoreEvent constructor and to enforce a specific structure and type to its parameters
    //getters visibility is package because the only class that should be able to access data is StoreEvent

    DataPair(String dataType, String data) {
        this.dataType = dataType;
        this.data = data;
    }

    String getDataType() {
        return dataType;
    }

    String getData() {
        return data;
    }

    private final String dataType;
    private final String data;
}
