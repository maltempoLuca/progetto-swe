package com.company.ui;

import com.company.mvc.View;

import java.util.List;
import java.util.LinkedList;

public final class CustomerView implements View {

    public CustomerView(String textField) {
        this.topText = textField;
        readButtons();
    }

    @Override
    public void refresh() {
        clear();
        readContents();
        render();
    }

    public void refreshWarning() {
        clear();
        contents.setLength(0);
        readWarning();
        render();
    }

    @Override
    public void render() {
        System.out.println(contents);
    }

    @Override
    public void clear() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    public void moveUp() {
        if (currentButtonIndex > 0) {
            currentButtonIndex--;
        }
    }

    public void moveDown() {
        if (currentButtonIndex < buttons.size() - 1) {
            currentButtonIndex++;
        }
    }

    public void addButton(Button button) {
        if (buttons.isEmpty()) {
            currentButtonIndex = 0;
        }
        buttons.add(button);
    }

    private void readContents() {
        contents.setLength(0);
        readTopText();
        readButtons();
        readWarning();
    }

    private void readTopText() {
        if (topText != null) {
            contents.append(topText).append("\n");
        }
    }

    private void readButtons() {

        for (int index = 0; index < buttons.size(); index++) {
            if (index == currentButtonIndex) {
                contents.append("->");
            }

            Button button = buttons.get(index);
            contents.append("[");
            contents.append(button.getText());
            contents.append("]\n");
        }
    }

    private void readWarning() {
        contents.append(warning).append("\n");
        warning = "";
    }

    public void setWarning(String warning) {
        this.warning = warning;
    }

    public void setTopText(String topText) {
        this.topText = topText;
    }

    public Button getCurrentButton() {
        return buttons.get(currentButtonIndex);
    }


    private int currentButtonIndex = -1;
    private final List<Button> buttons = new LinkedList<>();
    private final StringBuilder contents = new StringBuilder();
    private String topText;
    private String warning = "";
}
