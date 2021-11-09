package com.company.ui;


import com.company.constants.ButtonEvent;

import java.util.Scanner;

public final class WritableButton extends Button {
    public WritableButton(String text) {
        super(ButtonEvent.USERSCAN, text);
    }

    public void setUserText(String userText) {
        this.userText = userText;
    }

    public String getUserText() {
        return userText;
    }

    public void resetUserText() {
        userText = "";
    }

    private String userText = "";
}
