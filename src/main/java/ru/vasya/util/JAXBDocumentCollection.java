package ru.vasya.util;



import ru.vasya.model.document.Document;
import ru.vasya.model.document.Incoming;
import ru.vasya.model.document.Outgoing;
import ru.vasya.model.document.Task;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import java.util.ArrayList;
import java.util.Collection;

@XmlRootElement(name = "documents")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlSeeAlso({Task.class, Incoming.class, Outgoing.class})
public class JAXBDocumentCollection {
    private Collection<Document> docs;
    public JAXBDocumentCollection(){
        docs = new ArrayList<Document>();
    }

    public JAXBDocumentCollection(Collection<Document> docs){
        this.docs = docs;
    }

    public Collection<Document> getDocs() {
        return docs;
    }

    public void setDocs(Collection<Document> docs) {
        this.docs = docs;
    }
}
