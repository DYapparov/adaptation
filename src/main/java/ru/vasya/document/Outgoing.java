package ru.vasya.document;

@SedItem
public class Outgoing extends Document {
    private String destination;
    private String deliveryMethod;

    public Outgoing(){

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
