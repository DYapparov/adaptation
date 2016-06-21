package ru.vasya.document;

import java.util.Date;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class DocumentFactory {
    //Set to 100 to speed up DocumentExistsException throwing
    private static int MAX_REGISTRATION_NUMBER = 100;

    private String[] persons = {"First", "Second", "Third", "Fourth", "Fifth", "Sixth", "Seventh"};
    private Random rand = new Random();
    private int counter = 0;
    private static DocumentFactory instance;

    private DocumentFactory(){}

    public static DocumentFactory getInstance(){
        if (instance==null) instance = new DocumentFactory();
        return instance;
    }

    public Document getDocument(Class c){
        Document result = null;
        int id = ++counter;

        if (c.equals(Task.class)){
            result = new Task();
            ((Task) result).setDeliveryDate(getRandomFutureDate(1));
            ((Task) result).setFinishDate(getRandomFutureDate(48));
            ((Task) result).setPerformer(getRandomPerson());
            ((Task) result).setControlTag(rand.nextBoolean());
            ((Task) result).setController(getRandomPerson());
        }else if(c.equals(Outgoing.class)){
            result = new Outgoing();
            ((Outgoing)result).setDestination(getRandomPerson());
            ((Outgoing)result).setDeliveryMethod("SomeDeliveryMethod");
        }else if(c.equals(Incoming.class)){
            result = new Incoming();
            ((Incoming) result).setOrigination(getRandomPerson());
            ((Incoming) result).setDestination(getRandomPerson());
            ((Incoming) result).setOutgoingNumber(getRandomInt(5000));
            ((Incoming) result).setOutgoingDate(getRandomFutureDate(3));
        }
        result.setId(id);
        result.setDocName("Docname#" + id);
        result.setText("Text#" + id);
        result.setRegistrationNumber(rand.nextInt(MAX_REGISTRATION_NUMBER));
        result.setRegisterDate(new Date(System.currentTimeMillis()));
        result.setAuthor(getRandomPerson());

        return result;
    }

    private String getRandomPerson(){
        return persons[rand.nextInt(persons.length)];
    }

    private int getRandomInt(int max){
        return rand.nextInt(max);
    }

    //Method adds some "hours" to current time and returns. May have problems with hours>596)
    private Date getRandomFutureDate(int hours){
        return new Date(System.currentTimeMillis() + rand.nextInt(hours*3600000));
    }
}
