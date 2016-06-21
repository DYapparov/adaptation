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

    @Override
    public String toString(){
        return super.toString() + ", origination: " + origination + ", destination: " + destination + ", outgoing number: " + outgoingNumber + ", outgoing date: " + sdf.format(outgoingDate);
    }
}
