package com.company.store.events.requests;

import exceptions.UnregisteredUserException;

public interface RequestListener {
    void handleRequest(RequestEvent request);
}
