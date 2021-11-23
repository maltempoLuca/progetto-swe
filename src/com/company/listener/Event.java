package com.company.listener;


import com.company.store.ShippingDepartment;
import com.company.store.eventsys.events.EventIdentifier;

public interface Event {
    public EventIdentifier getIdentifier();
    public EventMessage getMessage();
}
