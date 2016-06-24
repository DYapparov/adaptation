package ru.vasya.model.document;

import ru.vasya.model.staff.Person;

@SedItem
public class Outgoing extends Document {
    private Person destination;
    private String deliveryMethod;

    public Outgoing(){

    }

    public Person getDestination() {
        return destination;
    }

    public void setDestination(Person destination) {
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
