package ru.vasya.rest;

import ru.vasya.model.document.Document;
import ru.vasya.model.staff.Person;
import ru.vasya.service.DocService;
import ru.vasya.util.JAXBDocumentCollection;

import javax.ejb.EJB;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.*;

@Path("/documents")
public class DocumentsController {

    @EJB
    DocService ds;

    @GET
    @Path("/all")
    @Produces(MediaType.APPLICATION_XML)
    public JAXBDocumentCollection getDocumentsXML(){
        Map<Person, TreeSet<Document>> docs = ds.getDocuments();
        List<Document> result = new ArrayList<Document>();
        for(Person p : docs.keySet()){
            result.addAll(docs.get(p));
        }
        return new JAXBDocumentCollection(result);
    }

    @GET
    @Path("/alljson")
    @Produces(MediaType.APPLICATION_JSON)
    public Set<Document> getDocumentsJSON(){
        Map<Person, TreeSet<Document>> docs = ds.getDocuments();
        Set<Document> result = new TreeSet<Document>();
        for(Person p : docs.keySet()){
            result.addAll(docs.get(p));
        }
        return result;
    }

    @GET
    @Path("/document/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Document getDocument(@PathParam("id") int id){
        Map<Person, TreeSet<Document>> docs = ds.getDocuments();
        for(Person p : docs.keySet()){
            for(Document d: docs.get(p)){
                if(d.getId()==id){
                    return d;
                }
            }
        }
        return null;
    }
}
