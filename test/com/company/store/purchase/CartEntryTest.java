package com.company.store.purchase;

import org.junit.Assert;
import org.junit.Test;

public class CartEntryTest {
    CartEntry tested;

    @Test
    public void copyTest() {
        tested = new CartEntry(new Product("id", "name", 10), 3);
        CartEntry deepCopy = new CartEntry(tested);
        CartEntry shallowCopy = tested;

        deepCopy.increase(2);

        Assert.assertNotEquals(deepCopy.getQuantity(), tested.getQuantity());
        Assert.assertEquals(shallowCopy.getQuantity(), tested.getQuantity());
        Assert.assertEquals(3, tested.getQuantity());
        Assert.assertEquals(5, deepCopy.getQuantity());
    }
}
