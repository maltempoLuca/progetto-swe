package com.company.store.eventsys.events;

import org.junit.Assert;
import org.junit.Test;

public class EventBuilderTest {

    @Test
    public void correctResetTest() {
        StoreEvent firstEvent = EventBuilder.buildStoreEvent().withInfo("key1", "info").withInfo("key2", "info").withInfo("key1", 0.).withIdentifier(EventIdentifier.TEST);
        StoreEvent secondEvent = EventBuilder.buildStoreEvent().withInfo("key1", "other info").withIdentifier(EventIdentifier.TEST);

        Assert.assertNotEquals(firstEvent.getMessage().getTextInfo("key1"), secondEvent.getMessage().getTextInfo("key1"));
        Assert.assertNull(secondEvent.getMessage().getTextInfo("key2")); //TODO: should throw exception instead
        Assert.assertNull(secondEvent.getMessage().getNumericInfo("key1")); //TODO: should throw exception instead
    }
}
