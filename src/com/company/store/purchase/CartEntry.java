package com.company.store.purchase;

public final class CartEntry {

    CartEntry(Product product, int quantity) {
        this.product = product;
        this.quantity = quantity;
    }

    CartEntry(CartEntry toCopy) {
        //copy constructor

        this.product = new Product(toCopy.product);
        this.quantity = toCopy.quantity;
    }

    void increase(int quantityDelta) {
        if (quantityDelta > 0) {
            quantity += quantityDelta;
        }
    }

    void decrease(int quantityDelta) {
        if(quantityDelta > 0) {
            quantity -= quantityDelta;
            if(quantity < 0) {
                quantity = 0;
            }
        }
    }

    String getId() {
        return product.getId();
    }

    String getName() {
        return product.getName();
    }

    double getPrice() {
        return product.getPrice() * quantity;
    }

    int getQuantity() {
        return quantity;
    }

    private final Product product;
    private int quantity;
}
