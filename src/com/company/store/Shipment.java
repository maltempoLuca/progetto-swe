package com.company.store;
import com.company.constants.Constants;
import com.company.constants.ShipmentState;

public class Shipment {

    //TODO: costruttore da mettere a package.
    public Shipment(String userId, String sender, String receiver, String senderAddress, String destinationAddress, String contents, String id) {
        this.userId = userId;
        this.sender = sender;
        this.receiver = receiver;
        this.senderAddress = senderAddress;
        this.destinationAddress = destinationAddress;
        this.contents = contents;
        this.id = id;
    }

    public Shipment(Shipment toCopy) {
        //copy constructor

        this.userId = toCopy.userId;
        this.sender = toCopy.sender;
        this.receiver = toCopy.receiver;
        this.senderAddress = toCopy.senderAddress;
        this.destinationAddress = toCopy.destinationAddress;
        this.contents = toCopy.contents;
        this.id = toCopy.id;
        this.state = toCopy.state;
    }

    //TODO: remove some setters?

    public void setState(ShipmentState state) {
        this.state = state;
    }

    public void setDestinationAddress(String destinationAddress) {
        this.destinationAddress = destinationAddress;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public void setSenderAddress(String senderAddress) {
        this.senderAddress = senderAddress;
    }

    public String getUserId() {
        return this.userId;
    }

    public String getSender() {
        return sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public String getSenderAddress() {
        return senderAddress;
    }

    public String getDestinationAddress() {
        return destinationAddress;
    }

    public String getContents() {
        return contents;
    }

    public String getId() {
        return id;
    }

    public ShipmentState getState() {
        return state;
    }

    private final String userId;
    private String sender;
    private String receiver;
    private String senderAddress;
    private String destinationAddress;
    private final String contents;
    private final String id;
    private ShipmentState state = Constants.CREATED;
}
