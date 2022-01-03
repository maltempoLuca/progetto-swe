package com.company.store.purchase;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public final class Cart {

    void increaseProduct(Product product, int quantity) {
        //adds specified amount of product to cart, if product is not in cart add it

        if (quantity > 0) {
            CartEntry entry;
            String id = product.getId();
            entry = contents.get(id);

            if (entry != null) {
                entry.increase(quantity);
            } else {
                addProduct(product, quantity);
            }
        }
    }

    void decreaseProduct(Product product, int quantity) {
        //removes specified amount of product from cart
        //if product quantity goes below 0 removes it from cart

        if (quantity > 0) {
            String id = product.getId();
            CartEntry entry = contents.get(id);

            if (entry != null) {
                entry.decrease(quantity);
                if (entry.getQuantity() == 0) {
                    removeProduct(id);
                }
            }
        }
    }

    double getTotal() {
        //calculate total price

        double total = 0;
        for (CartEntry entry : contents.values()) {
            total += entry.getPrice();
        }

        return total;
    }

    void clear() {
        contents.clear();
    }

    HashMap<String, CartEntry> getContents() {
        //creates deep copy of contents and returns it

        HashMap<String, CartEntry> contentsCopy = new HashMap<>();

        for (Map.Entry<String, CartEntry> toCopyEntry : contents.entrySet()) {
            contentsCopy.put(toCopyEntry.getKey(), new CartEntry(toCopyEntry.getValue()));
        }

        return contentsCopy;
    }

    boolean isEmpty() {
        return contents.isEmpty();
    }

    @Override
    public String toString() {
        Collection<CartEntry> contents = this.getContents().values();
        StringBuilder contentsToString = new StringBuilder();

        for (CartEntry entry : contents) {
            contentsToString.append("- ").append(entry.getName()).append(" x").append(entry.getQuantity()).append(" | ");

        }

        return contentsToString.toString();
    }

    private void removeProduct(String id) {
        contents.remove(id);
    }

    private void addProduct(Product product, int quantity) {
        if (quantity > 0) {
            String id = product.getId();
            CartEntry entry = new CartEntry(product, quantity);
            contents.put(id, entry);
        }
    }

    private final Map<String, CartEntry> contents = new HashMap<>();
}
