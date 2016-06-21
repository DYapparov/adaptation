package ru.vasya.document;

import java.util.Date;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class DocumentFactory {
    private String[] persons = {"First", "Second", "Third", "Fourth", "Fifth", "Sixth", "Seventh"};
    private Random rand = new Random();
    private int counter = 0;
    private static DocumentFactory df;

    private Set<Integer> registeredDocNumbers = new HashSet<Integer>();

    private DocumentFactory(){}

    public static DocumentFactory getDocumentFactory(){
        if (df==null) df = new DocumentFactory();
        return df;
    }

    public Document getDocument(Class c) throws DocumentExistsException{
        int registerNumber = rand.nextInt(100);
        if (registeredDocNumbers.contains(registerNumber)){
            throw new DocumentExistsException("Registration number already exists - " + registerNumber);
        }
        int id = ++counter;
        String docName = "Docname#" + id;
        String text = "Text#" + id;
        registeredDocNumbers.add(registerNumber);
        Date registerDate = new Date(System.currentTimeMillis());
        String author = persons[rand.nextInt(persons.length)];

        if (c.equals(Task.class)){
            Date deliveryDate = new Date(System.currentTimeMillis() + rand.nextInt(3600000));
            Date finishDate = new Date(System.currentTimeMillis() + 24*3600000);
            String performer = persons[rand.nextInt(persons.length)];
            boolean controlTag = rand.nextBoolean();
            String controller = "";
            if(controlTag) controller = persons[rand.nextInt(persons.length)];
            return new Task(id, docName, text, registerNumber, registerDate, author, deliveryDate, finishDate, performer, controlTag, controller);
        }else if(c.equals(Outgoing.class)){
            return new Outgoing(id, docName, text, registerNumber, registerDate, author, persons[rand.nextInt(persons.length)], "Someway");
        }else if(c.equals(Incoming.class)){
            return new Incoming(id, docName, text, registerNumber, registerDate, author, persons[rand.nextInt(persons.length)],persons[rand.nextInt(persons.length)], 454, new Date(System.currentTimeMillis() + 6666666));
        }
        return null;
    }

    public String stringGenerator(){
        return null;
    }
}
