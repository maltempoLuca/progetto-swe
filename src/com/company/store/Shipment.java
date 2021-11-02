package com.company.store;
import com.company.constants.Constants;

public class Shipment {

    Shipment(String sender, String receiver, String senderAddress, String destinationAddress, String contents, String id) {
        this.sender = sender;
        this.receiver = receiver;
        this.senderAddress = senderAddress;
        this.destinationAddress = destinationAddress;
        this.contents = contents;
        this.id = id;
    }

    public void setState(String state) {
        this.state = state;
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

    public String getState() {
        return state;
    }

    private String sender;
    private String receiver;
    private String senderAddress;
    private String destinationAddress;
    private String contents;
    private String id;
    private String state = Constants.INITIAL_STATE;
}
