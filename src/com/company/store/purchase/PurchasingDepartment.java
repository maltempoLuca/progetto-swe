package com.company.store.purchase;

import com.company.store.OperationResult;
import com.company.store.shipping.ShippingDepartment;
import com.company.store.events.viewevents.ViewEvent;
import com.company.store.events.viewevents.ViewEventIdentifier;
import com.company.store.events.viewevents.ViewEventManager;

import java.util.*;

//TODO: comment
public final class PurchasingDepartment {

    public PurchasingDepartment(ShippingDepartment shippingDepartment) {
        this.catalog = initCatalog();
        this.shippingDepartment = shippingDepartment;
    }

    public void addUserCart(String userEmail) {
        carts.put(userEmail.toLowerCase(), new Cart());
    }

    public OperationResult addToCart(String productId, int quantity, String userEmail) {
        //if user cart and selected product both exist add or increase selected product by specified quantity
        String userEmailLowerCase = userEmail.toLowerCase();
        boolean successful = false;
        String operationMessage;
        String addToCartText = " added to cart";
        String noProductText = "Product does not exist";
        String noUserText = "no such user found";

        Cart userCart = carts.get(userEmailLowerCase);

        if (userCart != null) {
            Product product = catalog.get(productId);
            if (product != null) {
                userCart.increaseProduct(product, quantity);
                operationMessage = product.getName() + " x" + quantity + addToCartText;
                successful = true;
            } else {
                operationMessage = noProductText;
            }
        } else {
            operationMessage = userEmailLowerCase + noUserText;
        }

        return new OperationResult(operationMessage, successful);
    }

    public OperationResult purchase(String typeOfService, String userEmail, String destinationAddress, String receiver) {
        //if user cart exists and is not empty:
        //1. notifies ShippingDepartment of purchase
        //2. clears user cart
        //3. generates a result message with purchase info

        String userEmailLowerCase = userEmail.toLowerCase();
        boolean successful = false;
        String operationMessage;
        String userText = "User ";
        String purchasedText = " has purchased: ";
        String serviceText = "with service: ";
        String failedPurchaseText = "Purchase failed, cart is empty";
        String noUserText = "no such user found";
        String priceText = "total price: ";

        Cart userCart = carts.get(userEmailLowerCase);

        if (userCart != null) {
            if (!userCart.isEmpty()) {
                double total = userCart.getTotal();
                String cartContentsString = userCart.toString();
                shippingDepartment.handlePurchase(userEmailLowerCase, typeOfService, destinationAddress,
                        receiver, cartContentsString);
                    userCart.clear();
                    operationMessage = userText + userEmailLowerCase + purchasedText + cartContentsString +
                            serviceText + typeOfService + ", " + priceText + ": " + total + "€";
                    successful = true;

            } else {
                operationMessage = failedPurchaseText;
            }

        } else {
            operationMessage = userEmailLowerCase + noUserText;
        }

        return new OperationResult(operationMessage, successful);
    }

    private HashMap<String, Product> initCatalog() {
        return CatalogUtility.buildCatalog();
    }

    public OperationResult getCatalog(String userEmail) {

        StringBuilder catalogBuilder = new StringBuilder();
        String catalogText = "Here's the Catalog";

        for (Product product : catalog.values()) {
            catalogBuilder.append(product.getId()).append(" ").append(product.getName()).append(" ").append(product.getPrice()).append("\n");
        }

        ViewEvent event = new ViewEvent(ViewEventIdentifier.CATALOG, userEmail, catalogBuilder.toString());
        ViewEventManager.getInstance().notify(event);

        return new OperationResult(catalogText, true);
    }

    private final ShippingDepartment shippingDepartment;
    private final Map<String, Product> catalog;
    private final Map<String, Cart> carts = new HashMap<>();
}
