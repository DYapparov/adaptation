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
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;

/**
 * Created by dyapparov on 11.07.2016.
 */

@Path("/documents")
public class DocumentsController {

    @EJB
    DocService ds;

    @GET
    @Path("/all")
    @Produces(MediaType.APPLICATION_XML)
    public JAXBDocumentCollection getDocuments(){
        Map<Person, TreeSet<Document>> docs = ds.getDocuments();
        List<Document> result = new ArrayList<Document>();
        for(Person p : docs.keySet()){
            result.addAll(docs.get(p));
        }
        return new JAXBDocumentCollection(result);
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
