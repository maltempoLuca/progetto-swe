package com.company.store.events.subscription;

import org.junit.Assert;
import org.junit.Test;

public final class SubscriptionManagerTest {

    @Test
    public void subscribeTest() {
        String aTarget = "aTarget";
        TargetTest aSubscriber = new TargetTest(0);
        TargetTest anotherSubscriber = new TargetTest(1);
        subManager.subscribe(aSubscriber, aTarget);
        subManager.subscribe(anotherSubscriber, aTarget);
        Assert.assertEquals(2, subManager.getSubscribers(aTarget).size());
        Assert.assertTrue(subManager.getSubscribers(aTarget).contains(aSubscriber));
        Assert.assertTrue(subManager.getSubscribers(aTarget).contains(anotherSubscriber));

    }

    @Test
    public void unsubscribeTest() {
        String aTarget = "aTarget";
        String anotherTarget = "anotherTarget";
        TargetTest aSubscriber = new TargetTest(0);
        TargetTest anotherSubscriber = new TargetTest(1);
        TargetTest aThirdSubscriber = new TargetTest(2);

        subManager.subscribe(aSubscriber, aTarget);
        subManager.subscribe(anotherSubscriber, aTarget);
        subManager.subscribe(aSubscriber, anotherTarget);
        subManager.subscribe(aThirdSubscriber, anotherTarget);
        subManager.unsubscribe(aSubscriber, aTarget);

        Assert.assertFalse(subManager.getSubscribers(aTarget).contains(aSubscriber));
        Assert.assertTrue(subManager.getSubscribers(anotherTarget).contains(aSubscriber));

        subManager.unsubscribe(anotherSubscriber,aTarget);
        Assert.assertTrue(subManager.getSubscribers(aTarget).isEmpty());
    }

    @Test
    public void unsubscribeBothMissingTest() {
        //should not throw exception
        TargetTest aMissingSubscriber = new TargetTest(0);
        String aMissingTarget = "aMissingTarget";
        subManager.unsubscribe(aMissingSubscriber, aMissingTarget);
    }

    @Test
    public void unsubscribeMissingSubscriberTest() {
        //should not throw exception
        TargetTest aSubscriber = new TargetTest(0);
        TargetTest aMissingSubscriber = new TargetTest(1);
        String aTarget = "aTarget";
        subManager.subscribe(aSubscriber, aTarget);
        subManager.unsubscribe(aMissingSubscriber, aTarget);

        Assert.assertEquals(1, subManager.getSubscribers(aTarget).size());
        Assert.assertTrue(subManager.getSubscribers(aTarget).contains(aSubscriber));
    }

    @Test
    public void unsubscribeFromMissingTarget() {
        TargetTest aSubscriber = new TargetTest(0);
        String aTarget = "aTarget";
        String aMissingTarget = "aMissingTarget";
        subManager.subscribe(aSubscriber, aTarget);
        subManager.unsubscribe(aSubscriber, aMissingTarget);

        Assert.assertEquals(1, subManager.getSubscribers(aTarget).size());
        Assert.assertTrue(subManager.getSubscribers(aTarget).contains(aSubscriber));
    }

    @Test
    public void subscribersShallowCopyTest() {
        TargetTest aSubscriber = new TargetTest(0);
        TargetTest anotherSubscriber = new TargetTest(1);
        TargetTest aThirdSubscriber = new TargetTest(2);
        String aTarget = "aTarget";

        subManager.subscribe(aSubscriber, aTarget);
        subManager.subscribe(anotherSubscriber, aTarget);

        subManager.getSubscribers(aTarget).add(aThirdSubscriber);
        subManager.getSubscribers(aTarget).remove(aSubscriber);

        Assert.assertFalse(subManager.getSubscribers(aTarget).contains(aThirdSubscriber));
        Assert.assertTrue(subManager.getSubscribers(aTarget).contains(aSubscriber));

        int aSubscriberIndex = subManager.getSubscribers(aTarget).indexOf(aSubscriber);
        subManager.getSubscribers(aTarget).get(aSubscriberIndex).setValue(3);

        Assert.assertEquals(3, subManager.getSubscribers(aTarget).get(aSubscriberIndex).getValue());
    }

    private final SubscriptionManager<String, TargetTest> subManager = new SubscriptionManager<>();
}

final class TargetTest {
    TargetTest(int value) {
        this.value = value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    private int value;
}
