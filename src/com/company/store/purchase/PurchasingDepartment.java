package com.company.store.purchase;

import com.company.constants.Constants;
import com.company.listener.Event;
import com.company.store.eventsys.events.EventBuilder;
import com.company.store.eventsys.events.EventIdentifier;
import com.company.store.eventsys.events.StoreEvent;
import com.company.store.eventsys.management.StoreEventManager;

import java.util.*;

//TODO: comment and test
public final class PurchasingDepartment {

    private PurchasingDepartment() {
        this.catalog = initCatalog();
    }

    public static PurchasingDepartment getInstance() {
        if (instance == null)
            instance = new PurchasingDepartment();
        return instance;
    }

    public void addUserCart(String userEmail) {
        carts.put(userEmail.toLowerCase(), new Cart());
    }

    public void addToCart(String productId, int quantity, String userEmail) {
        String userEmailLowerCase = userEmail.toLowerCase();

        Cart userCart = carts.get(userEmailLowerCase);

        if (userCart != null) {
            Product product = catalog.get(productId);
            if (product != null) {
                userCart.increaseProduct(product, quantity);
            } else {
                //TODO: throw exception
            }
        } else {
            //TODO: throw exception
        }
    }

    public void purchase(String userEmail) {
        //TODO: add price of selected service?
        //if user cart exists and is not empty generates an event with purchase info
        String userEmailLowerCase = userEmail.toLowerCase();

        //TODO: simulate user input?
        Cart userCart = carts.get(userEmailLowerCase);

        if (userCart != null) {
            if (!userCart.isEmpty()) {
                double total = userCart.getTotal();
                String cartContentsString = readCartContents(userCart);

                createPurchaseEvent(userEmailLowerCase, "indirizzo", Constants.STANDARD, cartContentsString, total);

                userCart.clear();
            } //TODO: decide what to do when cart is empty

        } //TODO: throw exception
    }

    private String readCartContents(Cart cart) {

        Collection<CartEntry> contents = cart.getContents().values();
        StringBuilder contentsToString = new StringBuilder();

        for (CartEntry entry : contents) {
            contentsToString.append("- ").append(entry.getName()).append(" x").append(entry.getQuantity()).append("\n");
        }

        return contentsToString.toString();
    }

    private void createPurchaseEvent(String userEmail, String address, String service, String contents, double total) {
        String userEmailLowerCase = userEmail.toLowerCase();

        Event purchaseEvent = new StoreEvent(EventBuilder.buildStoreEvent()
                .withInfo(Constants.USER_EMAIL, userEmailLowerCase)
                .withInfo(Constants.DESTINATION_ADDRESS, address)
                .withInfo(Constants.SHIPMENT_SERVICE, service)
                .withInfo(Constants.CONTENTS, contents)
                .withInfo(Constants.PRICE, total)
                .withIdentifier(EventIdentifier.PURCHASE_COMPLETED));

        StoreEventManager.getInstance().notify(purchaseEvent);
    }

    private HashMap<String, Product> initCatalog() {
        return CatalogUtility.buildCatalog();
    }

    public Map<String, Product> getCatalog() {
        //creates deep copy of catalog and returns it

        //TODO: test this
        Map<String, Product> catalogCopy = new HashMap<>();

        for (Map.Entry<String, Product> toCopyEntry : catalog.entrySet()) {
            catalogCopy.put(toCopyEntry.getKey(), new Product(toCopyEntry.getValue()));
        }

        return catalogCopy;
    }

    private static PurchasingDepartment instance = null;
    private final Map<String, Product> catalog;
    private final Map<String, Cart> carts = new HashMap<>();
}
