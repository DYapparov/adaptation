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

        //Generating registration number and performing check for the same existing number in  "registeredDocNumbers"
        int registerNumber = rand.nextInt(100);
        if (registeredDocNumbers.contains(registerNumber)){
            throw new DocumentExistsException("Registration number already exists - " + registerNumber);
        } else {
            registeredDocNumbers.add(registerNumber);
        }

        int id = ++counter;
        String docName = "Docname#" + id;
        String text = "Text#" + id;
        Date registerDate = new Date(System.currentTimeMillis());
        String author = getRandomPerson();

        if (c.equals(Task.class)){
            Date deliveryDate = getRandomFutureDate(1);
            Date finishDate = getRandomFutureDate(48);
            String performer = getRandomPerson();
            boolean controlTag = rand.nextBoolean();
            String controller = null;
            if(controlTag) controller = getRandomPerson();
            return new Task(id, docName, text, registerNumber, registerDate, author, deliveryDate, finishDate, performer, controlTag, controller);
        }else if(c.equals(Outgoing.class)){
            return new Outgoing(id, docName, text, registerNumber, registerDate, author, getRandomPerson(), "Someway");
        }else if(c.equals(Incoming.class)){
            return new Incoming(id, docName, text, registerNumber, registerDate, author, getRandomPerson(), getRandomPerson(), getRandomInt(5000), getRandomFutureDate(24));
        }
        return null;
    }

    private String getRandomPerson(){
        return persons[rand.nextInt(persons.length)];
    }

    private int getRandomInt(int max){
        return rand.nextInt(max);
    }

    private Date getRandomFutureDate(int hours){
        return new Date(System.currentTimeMillis() + rand.nextInt(hours*3600000));
    }
}
