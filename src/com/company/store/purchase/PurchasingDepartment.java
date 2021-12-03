package com.company.store.purchase;

import com.company.constants.Constants;
import com.company.store.OperationResult;
import com.company.store.ShippingDepartment;
import com.company.store.events.view.ViewEvent;
import com.company.store.events.view.ViewEventIdentifier;
import com.company.store.events.view.ViewEventManager;

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

    public OperationResult addToCart(String productId, int quantity, String userEmail) {
        String userEmailLowerCase = userEmail.toLowerCase();
        boolean successful = false;
        String operationMessage;

        Cart userCart = carts.get(userEmailLowerCase);

        if (userCart != null) {
            Product product = catalog.get(productId);
            if (product != null) {
                userCart.increaseProduct(product, quantity);
                operationMessage = product.getName() + " x" + quantity + " added to cart";
                successful = true;
            } else {
                //TODO: throw exception?
                operationMessage = "Product does not exist";
            }
        } else {
            //TODO: throw exception!!
            operationMessage = "No such user found";
        }

        return new OperationResult(operationMessage, successful);
    }

    public OperationResult purchase(String typeOfService, String userEmail, String destinationAddress, String receiver) {
        //TODO: add price of selected service?
        //if user cart exists and is not empty generates an event with purchase info
        String userEmailLowerCase = userEmail.toLowerCase();
        boolean successful = false;
        String operationMessage;

        Cart userCart = carts.get(userEmailLowerCase);

        if (userCart != null) {
            if (!userCart.isEmpty()) {
                double total = userCart.getTotal();
                String cartContentsString = readCartContents(userCart);

                ShippingDepartment.getInstance().handlePurchase(userEmailLowerCase, typeOfService, destinationAddress,
                        receiver, cartContentsString);

                userCart.clear();
                operationMessage = "User " + userEmailLowerCase + " has purchased: " + cartContentsString +
                        "with service: " + typeOfService;
                successful = true;

            } else {
                //TODO: decide what to do when cart is empty
                operationMessage = "Purchase failed, cart is empty";
            }

        }  else {
            //TODO: throw exception?
            operationMessage = "Purchase failed, no user corresponding to " + userEmailLowerCase;
        }

        return new OperationResult(operationMessage, successful);
    }

    private String readCartContents(Cart cart) {

        Collection<CartEntry> contents = cart.getContents().values();
        StringBuilder contentsToString = new StringBuilder();
        //TODO: remove comment?
        // int lines = 0;
        // int maxCharactersPerLine = 30;

        for (CartEntry entry : contents) {
            contentsToString.append("- ").append(entry.getName()).append(" x").append(entry.getQuantity()).append(" | ");

            /*
            if(contentsToString.length() - lines*maxCharactersPerLine >= maxCharactersPerLine) {
                contentsToString.append("\n");
                lines++;
            }
             */
        }

        return contentsToString.toString();
    }

    private HashMap<String, Product> initCatalog() {
        return CatalogUtility.buildCatalog();
    }

    public OperationResult getCatalog(String userEmail) {

        StringBuilder catalogBuilder = new StringBuilder();

        for(Product product : catalog.values()) {
            catalogBuilder.append(product.getId()).append(" ").append(product.getName()).append(" ").append(product.getPrice()).append("\n");
        }

        ViewEvent event = new ViewEvent(ViewEventIdentifier.CATALOG, userEmail, catalogBuilder.toString());
        ViewEventManager.getInstance().notify(event);

        return new OperationResult("Here's the Catalog", true);
    }

    private static PurchasingDepartment instance = null;
    private final Map<String, Product> catalog;
    private final Map<String, Cart> carts = new HashMap<>();
}
