package com.company.store;
import com.company.constants.Constants;
import com.company.constants.ShipmentState;

public class Shipment {

    Shipment(String sender, String receiver, String senderAddress, String destinationAddress, String contents, String id) {
        this.sender = sender;
        this.receiver = receiver;
        this.senderAddress = senderAddress;
        this.destinationAddress = destinationAddress;
        this.contents = contents;
        this.id = id;
    }

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

    private String sender;
    private String receiver;
    private String senderAddress;
    private String destinationAddress;
    private String contents;
    private String id;
    private ShipmentState state = Constants.CREATED;
}
