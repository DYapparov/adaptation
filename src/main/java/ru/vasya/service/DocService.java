package ru.vasya.service;

import com.google.common.reflect.ClassPath;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.vasya.model.document.*;
import ru.vasya.model.staff.Person;

import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Stateful;
import javax.ejb.Stateless;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.util.*;

@Stateless
public class DocService {
    private static final int MAX_REGISTRATION_NUMBER = 100000;
    private static final Logger LOGGER = LoggerFactory.getLogger(DocService.class);
    private static DocService instance;
    private static Map<Person, TreeSet<Document>> documents;

    @EJB
    private DocumentFactory df;

    private Set<String> registeredDocNumbers;
    private List<Class> docClasses;
    private Random rand;

    public Map<Person, TreeSet<Document>> getDocuments(){
        if (documents==null){
            documents = getRandomDocs(200);
        }
        return documents;
    }

    public DocService(){
        registeredDocNumbers = new HashSet<String>();
        rand = new Random();
        docClasses = getDocumentClasses();
    }

    public static DocService getInstance(){
        if (instance==null) {
            instance = new DocService();
        }
        return instance;
    }

    public void registerDocument(Document d) throws DocumentExistsException{
        String regNum = "" + rand.nextInt(MAX_REGISTRATION_NUMBER);
        d.setRegistrationNumber(regNum);
        d.setRegisterDate(new Date());
        if(registeredDocNumbers.contains(regNum))
            throw new DocumentExistsException("Registration number already exists - " + regNum);
        registeredDocNumbers.add(regNum);
    }

    public Map<Person, TreeSet<Document>> getRandomDocs(int count){
        Map<Person, TreeSet<Document>> result = new TreeMap<Person, TreeSet<Document>>();
        Document d = null;
        for (int i =0; i < count; i++){
            d = df.getDocument(docClasses.get(rand.nextInt(docClasses.size())));
            try {
                registerDocument(d);
                TreeSet<Document> documents = result.get(d.getAuthor());
                if (documents == null) {
                    documents = new TreeSet<Document>();
                    result.put(d.getAuthor(), documents);
                }
                documents.add(d);
                LOGGER.info("Registered: Document " + d.getRegistrationNumber());
            } catch (DocumentExistsException e){
                LOGGER.warn("Document already exists", e);
            }
        }
        return result;
    }

    public Map<Person, TreeSet<Document>> getDocs(){
        if(documents == null){
            documents = getRandomDocs(200);
        }
        return documents;
    }

    private List<Class> getDocumentClasses(){
        List<Class> result = new ArrayList<Class>();

        try {
            ClassPath classpath = ClassPath.from(Thread.currentThread().getContextClassLoader());
            Class c = null;
            for (ClassPath.ClassInfo classInfo : classpath.getTopLevelClassesRecursive("ru.vasya.model.document")) {
                c = classInfo.load();
                for (Annotation annotation:c.getAnnotations()) {
                    if(annotation.annotationType().equals(SedItem.class)){
                        result.add(c);
                    }
                }
            }
        } catch (IOException e){
            LOGGER.warn("Couldn not retrieve document classes", e);
        }
        return result;
    }
}