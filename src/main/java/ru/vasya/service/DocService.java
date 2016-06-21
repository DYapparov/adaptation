package ru.vasya.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.vasya.document.*;

import java.util.*;

public class DocService {
    private static final int MAX_REGISTRATION_NUMBER = 100;
    private static final Logger LOGGER = LoggerFactory.getLogger(DocService.class);

    private ArrayList<Class> DOC_CLASSES = new ArrayList<Class>(Arrays.asList(Task.class, Outgoing.class, Incoming.class));

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
        if (instance==null) {
            instance = new DocService();
        }
        return instance;
    }

    public void registerDocument(Document d) throws DocumentExistsException{
        int regNum = rand.nextInt(MAX_REGISTRATION_NUMBER);
        d.setRegistrationNumber(regNum);
        d.setRegisterDate(new Date(System.currentTimeMillis()));
        if(registeredDocNumbers.contains(regNum))
            throw new DocumentExistsException("Registration number already exists - " + regNum);
        registeredDocNumbers.add(regNum);
    }

    public Collection<Document> getRandomDocs(int count){
        Collection<Document> result = new TreeSet<Document>();
        for (int i =0; i < count; i++){
            Document d = df.getDocument(DOC_CLASSES.get(rand.nextInt(DOC_CLASSES.size())));
            try {
                registerDocument(d);
                result.add(d);
                LOGGER.info(this + ": registered new doc - ", d);
            } catch (DocumentExistsException e){
                LOGGER.warn(this + ": ", e);
            }
        }
        return result;
    }

}