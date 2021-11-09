package com.company.ui;

import com.company.constants.ButtonEvent;
import com.company.constants.ButtonIdentifier;

public class ConfirmationButton extends Button{
    public ConfirmationButton(ButtonIdentifier locationOfConfirm){
        super(ButtonEvent.CONFIRM, "CONFIRM");
        this.locationOfConfirm = locationOfConfirm;
    }

    public ButtonIdentifier getLocationOfConfirm() {
        return locationOfConfirm;
    }

    private final ButtonIdentifier locationOfConfirm;
}
