package ru.vasya.rest;

import ru.vasya.model.document.Document;
import ru.vasya.model.staff.Person;
import ru.vasya.service.DocService;
import ru.vasya.service.PersonService;
import ru.vasya.util.JAXBDocumentCollection;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.Set;

@Path("/ecm")
public class EmployeesController {

    @GET
    @Path("/employees")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Person> getEmployees(){
        return PersonService.getInstance().getPersonList();
    }

    @GET
    @Path("/employees/{id}")
    @Produces(MediaType.APPLICATION_XML)
    public JAXBDocumentCollection getPerson(@PathParam("id") int id){
        Set<Document> docs = DocService.getInstance().getRandomDocs(100).get(PersonService.getInstance().getPersonList().get(id));
        return new JAXBDocumentCollection(docs);
    }
}
