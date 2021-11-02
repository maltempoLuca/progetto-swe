package com.company.ui;
import com.company.constants.ButtonEvent;
import com.company.constants.ViewIdentifier;

public final class NavigationButton extends Button {

    public NavigationButton(String text, ViewIdentifier nextView) {
        super(ButtonEvent.CHANGE_VIEW, text);
        this.nextView = nextView;
    }

    public ViewIdentifier getNextView() {
        return nextView;
    }

    private final ViewIdentifier nextView;
}
