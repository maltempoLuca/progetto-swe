package com.company.store.eventsys.events;

import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class StoreEventTest {

    @Test
    public void constructorTest() {
        Map<String, String> textInfo = new HashMap<>();
        Map<String, Double> numericInfo = new HashMap<>();
        textInfo.put("key1", "info");
        textInfo.put("key2", "info");
        textInfo.put("key3", "info");
        numericInfo.put("key1", 0.01);
        numericInfo.put("key2", 0.02);
        StoreEvent tested = new StoreEvent(EventIdentifier.TEST, textInfo, numericInfo);

        numericInfo.put("key3", 0.03);
        textInfo.put("key4", "info");
        Assert.assertNull(tested.getNumericInfo("key3")); //TODO: should throw exception instead
        Assert.assertNull(tested.getTextInfo("key4")); //TODO: should throw exception instead

        numericInfo.replace("key1", numericInfo.get("key1") + 3);
        Assert.assertNotSame(numericInfo.get("key1"), tested.getNumericInfo("key1"));

        textInfo.remove("key2");
        Assert.assertNotNull(tested.getTextInfo("key2"));
    }
}
