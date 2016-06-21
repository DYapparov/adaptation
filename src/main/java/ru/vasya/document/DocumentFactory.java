package ru.vasya.document;

import java.lang.reflect.Field;
import java.util.*;

public class DocumentFactory {

    private ArrayList<String> persons = new ArrayList<String>(Arrays.asList("First", "Second", "Third", "Fourth", "Fifth", "Sixth", "Seventh"));
    private Random rand = new Random();
    private int counter = 0;
    private static DocumentFactory instance;

    private DocumentFactory(){}

    public static DocumentFactory getInstance(){
        if (instance==null) {
            instance = new DocumentFactory();
        }
        return instance;
    }

    public Document getDocument(Class c){
        Document result = null;
        int id =++counter;
        try {
            result = (Document) c.newInstance();
            result.setId(id);
            result.setDocName("Docname#" + id);
            result.setText("Text#" + id);
            result.setAuthor(getRandomPerson());
            for (Field field : c.getDeclaredFields()) {
                field.setAccessible(true);
                if(String.class.equals(field.getType())){
                    field.set(result, getRandomPerson());
                } else if(Date.class.equals(field.getType())){
                    field.set(result, getRandomFutureDate(1));
                } else if (Integer.class.equals(field.getType())){
                    field.set(result, getRandomInt(1000));
                }
                field.setAccessible(false);
            }
        } catch (IllegalAccessException e){
            e.printStackTrace();
        } catch (InstantiationException e){
            e.printStackTrace();
        }
        return result;
    }
    private String getRandomPerson(){
        return persons.get(rand.nextInt(persons.size()));
    }

    private int getRandomInt(int max){
        return rand.nextInt(max);
    }

    //Method adds some "hours" to current time and returns. May have problems with hours>596)
    private Date getRandomFutureDate(int hours){
        return new Date(System.currentTimeMillis() + rand.nextInt(hours*3600000));
    }
}
