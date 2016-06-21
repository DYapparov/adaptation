package ru.vasya.document;


import java.util.Date;

public class Outgoing extends Document {
    private String destination;
    private String deliveryMethod;

    public Outgoing(){

    }

    public Outgoing(int id, String docName, String text, Integer registerNumber, Date registerDate, String author, String destination, String deliverymethod) {
        super(id, docName, text, registerNumber, registerDate, author);
        this.destination = destination;
        this.deliveryMethod = deliverymethod;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getDeliveryMethod() {
        return deliveryMethod;
    }

    public void setDeliveryMethod(String deliveryMethod) {
        this.deliveryMethod = deliveryMethod;
    }

    @Override
    public String toString() {
        return super.toString() + ", destination " + destination + ", delivery method: " + deliveryMethod;
    }
}
