package com.company.ui;

import java.util.*;

public class ButtonList {
    //wraps ArrayList of Buttons, allows access through index and key

    public Button get(int index) {
        return buttons.get(index);
    }

    public Button get(String key) {
        int index = keyToIndex.get(key);
        return this.get(index);
    }

    public boolean add(Button button) {
        //generate key based on button text, allows only 1 button with same text

        String key = button.getText();
        return this.add(key, button);
    }

    public boolean add(String key, Button button){
        boolean result = false;

        if (!keyToIndex.containsKey(key)) {
            keyToIndex.put(key, buttons.size());
            buttons.add(button);
            result = true;
        }

        return result;
    }

    public Button remove(int index) {
        //search for key matching index
        //if such key is found call remove(String key) to ensure index consistency

        Button result = null;
        Iterator<Map.Entry<String, Integer>> iterator = keyToIndex.entrySet().iterator();
        String toRemoveKey = null;
        boolean found = false;

        while (iterator.hasNext() && !found) {
            Map.Entry<String, Integer> pair = iterator.next();
            if(pair.getValue() == index) {
                toRemoveKey = pair.getKey();
                found = true;
            }
        }

        if (found) {
            result = this.remove(toRemoveKey);
        }

        return result;
    }

    public Button remove(String key) {
        Button result = null;

        if(keyToIndex.containsKey(key)) {
            int toRemoveIndex = keyToIndex.get(key);
            keyToIndex.remove(key);
            adjustIndexes(toRemoveIndex);

            result = buttons.remove(toRemoveIndex);
        }

        return result;
    }

    private void adjustIndexes(int startIndex) {
        //decreases by 1 every index higher than startIndex
        for(String key : keyToIndex.keySet()) {
            int index = keyToIndex.get(key);
            if(index > startIndex) {
                keyToIndex.replace(key, index - 1);
            }
        }
    }
    
    private final Map<String, Integer> keyToIndex = new HashMap<>();
    private final List<Button> buttons = new ArrayList<>();
}
