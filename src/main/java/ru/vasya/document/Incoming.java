package ru.vasya.document;

import ru.vasya.staff.Person;

import java.util.Date;

@SedItem
public class Incoming extends Document {
    private Person origination;
    private Person destination;
    private int outgoingNumber;
    private Date outgoingDate;

    public Incoming(){

    }

    public Person getOrigination() {
        return origination;
    }

    public void setOrigination(Person origination) {
        this.origination = origination;
    }

    public Person getDestination() {
        return destination;
    }

    public void setDestination(Person destination) {
        this.destination = destination;
    }

    public int getOutgoingNumber() {
        return outgoingNumber;
    }

    public void setOutgoingNumber(int outgoingNumber) {
        this.outgoingNumber = outgoingNumber;
    }

    public Date getOutgoingDate() {
        return outgoingDate;
    }

    public void setOutgoingDate(Date outgoingDate) {
        this.outgoingDate = outgoingDate;
    }

    @Override
    public String toString(){
        return super.toString() + ", origination: " + origination + ", destination: " + destination + ", outgoing number: " + outgoingNumber + ", outgoing date: " + dateFormat.format(outgoingDate);
    }
}
