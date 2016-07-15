package ru.vasya.rest;

import ru.vasya.dao.PersonDAO;
import ru.vasya.model.document.Document;
import ru.vasya.model.staff.Person;
import ru.vasya.service.DocService;
import ru.vasya.service.PersonService;
import ru.vasya.util.JAXBDocumentCollection;

import javax.ejb.EJB;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.*;


@Path("/ecm")
public class EmployeesController {

    @EJB
    PersonService ps;

    @EJB
    DocService ds;

    @EJB
    PersonDAO personDAO;

    @GET
    @Path("/employees")
    @Produces(MediaType.APPLICATION_JSON)
    public Set<Person> getEmployees(){
        return personDAO.getAll(Person.class);
    }

    @GET
    @Path("/employees/{id}")
    @Produces(MediaType.APPLICATION_XML)
    public JAXBDocumentCollection getPersonDocs(@PathParam("id") int id){
        Map<Person, TreeSet<Document>> docs = ds.getDocuments();
        for (Person p : docs.keySet()){
            if (p.getId()==id){
                return new JAXBDocumentCollection(docs.get(p));
            }
        }
        return null;
    }
}
