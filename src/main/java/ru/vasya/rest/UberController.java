package ru.vasya.rest;

import ru.vasya.dao.PersonDAO;
import ru.vasya.model.document.Document;
import ru.vasya.model.document.Incoming;
import ru.vasya.model.document.Outgoing;
import ru.vasya.model.document.Task;
import ru.vasya.model.staff.Person;
import ru.vasya.rest.response.*;
import ru.vasya.service.DocService;
import ru.vasya.util.TemplateLoader;

import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Map;
import java.util.TreeSet;

@Path("/what_should_i_do")
public class UberController {

    @EJB
    DocService ds;

    @EJB
    PersonDAO personDAO;

    @GET
    @Path("/docItem/{id}")
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
            String template = TemplateLoader.getTemplate("Task_VIEW");
            TaskResponseObject resp = new TaskResponseObject((Task)document, template, "TODO", "ENUM?");
            return Response.ok().entity(resp).build();
        } else if(document instanceof Outgoing){
            String template = TemplateLoader.getTemplate("Outgoing_VIEW");
            OutgoingResponseObject resp = new OutgoingResponseObject((Outgoing)document, template, "TODO", "ENUM?");
            return Response.ok().entity(resp).build();
        } else if(document instanceof Incoming){
            String template = TemplateLoader.getTemplate("Incoming_VIEW");
            IncomingResponseObject resp = new IncomingResponseObject((Incoming)document, template, "TODO", "ENUM?");
            return Response.ok().entity(resp).build();
        }
        return null;
    }

    @GET
    @Path("/personItem/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public PersonResponseObject getPersonTabParams(@PathParam("id") int id){
        Person model = personDAO.getByID(Person.class, id);
        String template = TemplateLoader.getTemplate("Person_VIEW");
        PersonResponseObject resp = new PersonResponseObject(model, template, "TODO", "ENUM?");
        return resp;
    }

    @GET
    @Path("/edit/personItem/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public PersonResponseObject getEditPersonTabParams(@PathParam("id") int id){
        Person model = personDAO.getByID(Person.class, id);
        String template = TemplateLoader.getTemplate("Person_EDIT");
        PersonResponseObject resp = new PersonResponseObject(model, template, "TODO", "ENUM?");
        return resp;
    }

    @POST
    @Path("/update/person")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public PersonResponseObject savePerson(Person p){
        personDAO.update(p);
        String template = TemplateLoader.getTemplate("Person_VIEW");
        return new PersonResponseObject(p, template, "TODO", "ENUM?");
    }
}
