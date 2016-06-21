package ru.vasya.service;

import ru.vasya.document.*;

import java.util.*;

public class DocService {
    private Class[] DOC_CLASSES = {Task.class, Outgoing.class, Incoming.class};


    private static DocService instance;
    private DocumentFactory df;
    private Set<Integer> registeredDocNumbers;
    private Random rand;

    private DocService(){
        df = DocumentFactory.getInstance();
        registeredDocNumbers = new HashSet<Integer>();
        rand = new Random();
    }

    public static DocService getInstance(){
        return (instance==null)? new DocService():instance;
    }


    public void registerDocument(Document d) throws DocumentExistsException{
        int regNum = d.getRegistrationNumber();
        if(registeredDocNumbers.contains(regNum))
            throw new DocumentExistsException("Registration number already exists - " + regNum);
        registeredDocNumbers.add(regNum);
    }

    public List<Document> getRandomDocs(int count){
        List<Document> result = new ArrayList<Document>();
        for (int i =0; i < count; i++){
            Document d = df.getDocument(DOC_CLASSES[rand.nextInt(DOC_CLASSES.length)]);
            try {
                registerDocument(d);
                result.add(d);
            } catch (DocumentExistsException e){
                System.out.println(e.toString());
            }
        }
        return result;
    }

}