package ru.vasya.document;

import java.util.Date;

public class Incoming extends Document {
    private String origination;
    private String destination;
    private int outgoingNumber;
    private Date outgoingDate;

    public Incoming(){

    }

    public Incoming(int id, String docName, String text, Integer registerNumber, Date registerDate, String author, String origination, String destination, int outgoingNumber, Date outgoingDate) {
        super(id, docName, text, registerNumber, registerDate, author);
        this.origination = origination;
        this.destination = destination;
        this.outgoingNumber = outgoingNumber;
        this.outgoingDate = outgoingDate;
    }

    public String getOrigination() {
        return origination;
    }

    public void setOrigination(String origination) {
        this.origination = origination;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
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
        return super.toString() + ", origination: " + origination + ", destination: " + destination + ", outgoing number: " + outgoingNumber + ", outgoing date: " + sdf.format(outgoingDate);
    }
}
