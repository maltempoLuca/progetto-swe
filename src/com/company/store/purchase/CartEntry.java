package com.company.store.purchase;

public final class CartEntry {

    CartEntry(Product product, int quantity) {
        this.product = product;
        this.quantity = quantity;
    }

    CartEntry(CartEntry toCopy) {
        this.product = new Product(toCopy.product);
        this.quantity = toCopy.quantity;
    }

    CartEntry(Product product) {
        this(product, 1);
    }

    void increase(int quantityDelta) {
        if (quantity > 0) {
            quantity += quantityDelta;
        }
    }

    void decrease(int quantityDelta) {
        if(quantity > 0) {
            quantity -= quantityDelta;
        }
    }

    String getId() {
        return product.getId();
    }

    String getName() {
        return product.getName();
    }

    int getPrice() {
        return product.getPrice() * quantity;
    }

    int getQuantity() {
        return quantity;
    }

    private final Product product;
    private int quantity;
}
