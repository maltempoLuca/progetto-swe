package com.company.store.purchase;

import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;

public final class CartTest {

    @Test
    public void addProductTest() {
        tested.increaseProduct(product, 3);

        Assert.assertFalse(tested.isEmpty());
        Assert.assertEquals(3, tested.getContents().get(productId).getQuantity());
    }

    @Test
    public void increaseProductTest() {
        tested.increaseProduct(product, 3);
        tested.increaseProduct(product, 7);
        tested.increaseProduct(product2, 5);

        Assert.assertEquals(10, tested.getContents().get(productId).getQuantity());
        Assert.assertEquals(5, tested.getContents().get(productId2).getQuantity());
    }

    @Test
    public void negativeIncreaseTest() {
        tested.increaseProduct(product, -1);
        Assert.assertTrue(tested.isEmpty());

        tested.increaseProduct(product, 5);
        tested.increaseProduct(product, -3);

        Assert.assertEquals(5, tested.getContents().get(productId).getQuantity());
    }

    @Test
    public void decreaseProductTest() {
        tested.increaseProduct(product, 10);
        tested.decreaseProduct(product, 3);

        Assert.assertEquals(7, tested.getContents().get(productId).getQuantity());
    }

    @Test
    public void negativeDecreaseTest() {
        tested.increaseProduct(product, 9);
        tested.decreaseProduct(product, -3);

        Assert.assertEquals(9, tested.getContents().get(productId).getQuantity());
    }

    @Test
    public void removeProductTest() {

        tested.increaseProduct(product, 10);
        tested.decreaseProduct(product, 17);

        Assert.assertNull(tested.getContents().get(productId));
        Assert.assertTrue(tested.isEmpty());
    }

    @Test
    public void decreaseAbsentProductTest() {
        tested.decreaseProduct(product, 2);
    }

    @Test
    public  void getTotalTest() {
        double errorDelta = 0.0001;
        tested.increaseProduct(product, 4);
        tested.increaseProduct(product2, 2);

        Assert.assertEquals(503.98, tested.getTotal(), errorDelta);
    }

    @Test
    public void deepCopyContentsTest() {
        tested.increaseProduct(product, 4);
        tested.increaseProduct(product2, 6);

        HashMap<String, CartEntry> contentsCopy = tested.getContents();
        contentsCopy.put(productId3, new CartEntry(product3, 3));
        contentsCopy.remove(productId);
        contentsCopy.get(productId2).increase(2);

        Assert.assertNull(tested.getContents().get(productId3));
        Assert.assertNotNull( tested.getContents().get(productId));
        Assert.assertEquals(6, tested.getContents().get(productId2).getQuantity());
    }

    Cart tested = new Cart();
    String productId = "id";
    String productId2 = "id2";
    String productId3 = "id3";
    Product product = new Product(productId, "name", 100.5);
    Product product2 = new Product(productId2, "name", 50.99);
    Product product3 = new Product(productId3, "name", 20);
}
