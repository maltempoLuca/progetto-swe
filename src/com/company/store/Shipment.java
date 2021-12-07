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

    public Shipment(Shipment toCopy) {
        //copy constructor

        this.sender = toCopy.sender;
        this.receiver = toCopy.receiver;
        this.senderAddress = toCopy.senderAddress;
        this.destinationAddress = toCopy.destinationAddress;
        this.contents = toCopy.contents;
        this.id = toCopy.id;
        this.state = toCopy.state;
    }

    public void setState(ShipmentState state) {
        this.state = state;
    }

    public void setDestinationAddress(String destinationAddress) {
        this.destinationAddress = destinationAddress;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
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
    private final String contents;
    private final String id;
    private ShipmentState state = Constants.CREATED;
}
