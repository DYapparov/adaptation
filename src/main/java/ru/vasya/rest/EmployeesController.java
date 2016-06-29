package ru.vasya.rest;

import ru.vasya.model.document.Document;
import ru.vasya.model.staff.Person;
import ru.vasya.service.DocService;
import ru.vasya.service.PersonService;
import ru.vasya.service.PersonServiceImpl;
import ru.vasya.util.JAXBDocumentCollection;

import javax.ejb.EJB;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.Set;


@Path("/ecm")
public class EmployeesController {

    @EJB
    PersonService ps;

    @EJB
    DocService ds;

    @GET
    @Path("/employees")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Person> getEmployees(){
        return ps.getPersonList();
    }

    @GET
    @Path("/employees/{id}")
    @Produces(MediaType.APPLICATION_XML)
    public JAXBDocumentCollection getPersonDocs(@PathParam("id") int id){
        Set<Document> docs = ds.getRandomDocs(100).get(ps.getPersonList().get(id));
        return new JAXBDocumentCollection(docs);
    }

    //Ne katit :
    //MessageBodyWriter not found for media type=application/xml,
    //type=class [Ljava.lang.Object;, genericType=class [Ljava.lang.Object;
    @GET
    @Path("/employees/array/{id}")
    @Produces(MediaType.APPLICATION_XML)
    public Document[] getPersonDocsAsArray(@PathParam("id") int id){
        Set<Document> docs = ds.getRandomDocs(100).get(ps.getPersonList().get(id));
        Document[] docsArr = new Document[100];
        docs.toArray(docsArr);
        return docsArr;
    }
}
