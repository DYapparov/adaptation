package ru.vasya.service;

import com.google.common.reflect.ClassPath;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.vasya.document.*;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.util.*;

public class DocService {
    private static final int MAX_REGISTRATION_NUMBER = 100;
    private static final Logger LOGGER = LoggerFactory.getLogger(DocService.class);

    private static DocService instance;
    private DocumentFactory df;
    private Set<Integer> registeredDocNumbers;
    private List<Class> docClasses;
    private Random rand;

    private DocService(){
        df = DocumentFactory.getInstance();
        registeredDocNumbers = new HashSet<Integer>();
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
        int regNum = rand.nextInt(MAX_REGISTRATION_NUMBER);
        d.setRegistrationNumber(regNum);
        d.setRegisterDate(new Date());
        if(registeredDocNumbers.contains(regNum))
            throw new DocumentExistsException("Registration number already exists - " + regNum);
        registeredDocNumbers.add(regNum);
    }

    public Collection<Document> getRandomDocs(int count){
        Collection<Document> result = new TreeSet<Document>();
        for (int i =0; i < count; i++){
            Document d = df.getDocument(docClasses.get(rand.nextInt(docClasses.size())));
            try {
                registerDocument(d);
                result.add(d);
                LOGGER.info("Created: Document " + d.getRegistrationNumber());
            } catch (DocumentExistsException e){
                LOGGER.warn("Document already exists", e);
            }
        }
        return result;
    }

    private List<Class> getDocumentClasses(){
        List<Class> result = new ArrayList<Class>();

        try {
            ClassPath classpath = ClassPath.from(Thread.currentThread().getContextClassLoader());
            for (ClassPath.ClassInfo classInfo : classpath.getTopLevelClassesRecursive("ru.vasya.document")) {
                Class c = classInfo.load();
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