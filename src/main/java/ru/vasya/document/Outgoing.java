package ru.vasya.document;


import java.util.Date;

public class Outgoing extends Document {
    private String destination;
    private String deliverymethod;

    public Outgoing(){

    }

    public Outgoing(int id, String docName, String text, Integer registerNumber, Date registerDate, String author, String destination, String deliverymethod) {
        super(id, docName, text, registerNumber, registerDate, author);
        this.destination = destination;
        this.deliverymethod = deliverymethod;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getDeliverymethod() {
        return deliverymethod;
    }

    public void setDeliverymethod(String deliverymethod) {
        this.deliverymethod = deliverymethod;
    }

    @Override
    public String toString() {
        return super.toString() + ", destination " + destination + ", delivery method: " + deliverymethod;
    }
}
