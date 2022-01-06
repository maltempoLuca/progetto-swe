package com.company.store.events.viewevents;

public final class ViewEvent {

    public ViewEvent(ViewEventIdentifier id, String userEmail, String content) {
        this.content = content;
        this.id = id;
        this.userEmail = userEmail;
    }

    public String getContent() {
        return content;
    }

    public ViewEventIdentifier getId() {
        return id;
    }

    public String getUserEmail() {
        return userEmail;
    }

    private final String userEmail;
    private final String content;
    private final ViewEventIdentifier id;
}
