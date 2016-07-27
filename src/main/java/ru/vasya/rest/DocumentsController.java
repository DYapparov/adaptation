package ru.vasya.rest;

import ru.vasya.model.document.Document;
import ru.vasya.model.document.Incoming;
import ru.vasya.model.document.Outgoing;
import ru.vasya.model.document.Task;
import ru.vasya.model.staff.Person;
import ru.vasya.rest.response.document.IncomingResponseObject;
import ru.vasya.rest.response.document.OutgoingResponseObject;
import ru.vasya.rest.response.document.TaskResponseObject;
import ru.vasya.service.DocService;
import ru.vasya.util.JAXBDocumentCollection;
import ru.vasya.util.TemplateLoader;

import javax.ejb.EJB;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
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
    @Path("/incomings")
    @Produces(MediaType.APPLICATION_JSON)
    public Set<Incoming> getIncomingsJSON(){
        Map<Person, TreeSet<Document>> docs = ds.getDocuments();
        Set<Incoming> result = new TreeSet<Incoming>();
        for(Person p : docs.keySet()){
            for(Document d : docs.get(p)){
                if (d instanceof Incoming){
                    result.add((Incoming)d);
                }
            }
        }
        return result;
    }

    @GET
    @Path("/outgoings")
    @Produces(MediaType.APPLICATION_JSON)
    public Set<Outgoing> getOutgoingsJSON(){
        Map<Person, TreeSet<Document>> docs = ds.getDocuments();
        Set<Outgoing> result = new TreeSet<Outgoing>();
        for(Person p : docs.keySet()){
            for(Document d : docs.get(p)){
                if (d instanceof Outgoing){
                    result.add((Outgoing)d);
                }
            }
        }
        return result;
    }

    @GET
    @Path("/tasks")
    @Produces(MediaType.APPLICATION_JSON)
    public Set<Task> getTasksJSON(){
        Map<Person, TreeSet<Document>> docs = ds.getDocuments();
        Set<Task> result = new TreeSet<Task>();
        for(Person p : docs.keySet()){
            for(Document d : docs.get(p)){
                if (d instanceof Task){
                    result.add((Task)d);
                }
            }
        }
        return result;
    }

    @GET
    @Path("/document/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getDocumentTabParams(@PathParam("id") int id){
        Document document = null;
        Map<Person, TreeSet<Document>> docs = ds.getDocuments();
        for(Person p : docs.keySet()){
            for(Document d: docs.get(p)){
                if(d.getId()==id){
                    document = d;
                }
            }
        }
        if(document instanceof Task){
            String template = TemplateLoader.getTemplate("Task_VIEW.html");
            TaskResponseObject resp = new TaskResponseObject((Task)document, template, "Поручение №" + document.getRegistrationNumber(), "NEW_TAB", "documents/document/");
            return Response.ok().entity(resp).build();
        } else if(document instanceof Outgoing){
            String template = TemplateLoader.getTemplate("Outgoing_VIEW.html");
            OutgoingResponseObject resp = new OutgoingResponseObject((Outgoing)document, template, "Исходящее №" + document.getRegistrationNumber(), "NEW_TAB", "documents/document/");
            return Response.ok().entity(resp).build();
        } else if(document instanceof Incoming){
            String template = TemplateLoader.getTemplate("Incoming_VIEW.html");
            IncomingResponseObject resp = new IncomingResponseObject((Incoming)document, template, "Входящее №" + document.getRegistrationNumber(), "NEW_TAB", "documents/document/");
            return Response.ok().entity(resp).build();
        }
        return null;
    }
}
