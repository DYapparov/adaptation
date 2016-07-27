package ru.vasya.rest;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import ru.vasya.dao.PersonDAO;
import ru.vasya.dao.PostDAO;
import ru.vasya.model.document.Document;
import ru.vasya.model.staff.Person;
import ru.vasya.model.staff.Post;
import ru.vasya.rest.response.person.PersonResponseObject;
import ru.vasya.rest.response.person.PersonsResponseObject;
import ru.vasya.rest.response.gridx.GridxColumn;
import ru.vasya.service.DocService;
import ru.vasya.util.JAXBDocumentCollection;
import ru.vasya.util.TemplateLoader;

import javax.ejb.EJB;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.*;
import java.util.*;


@Path("/ecm")
public class EmployeesController {

    @EJB
    DocService ds;

    @EJB
    PersonDAO personDAO;

    @EJB
    PostDAO postDAO;

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

    @GET
    @Path("/employees/employee/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public PersonResponseObject getPersonTabParams(@PathParam("id") int id){
        Person model = personDAO.getByID(Person.class, id);
        String template = TemplateLoader.getTemplate("Person_VIEW.html");
        PersonResponseObject resp = new PersonResponseObject(model, template, "Сотрудник " + model.getId(), "NEW_TAB", "ecm/employees/employee/");
        return resp;
    }

    @GET
    @Path("/employees/employee/edit/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public PersonResponseObject getEditPersonTabParams(@PathParam("id") int id){
        Person model = personDAO.getByID(Person.class, id);
        String template = TemplateLoader.getTemplate("Person_EDIT.html");
        Set<Post> posts = postDAO.getAll(Post.class);
        StringBuilder postOptions = new StringBuilder();
        for(Post p : posts){
            postOptions.append("<option value='").append(p.getId()).append("'>").append(p.getName()).append("</option>");
        }
        template = template.replace("###REPLACE_FOR_OPTIONS###", postOptions.toString());
        PersonResponseObject resp = new PersonResponseObject(model, template, "Сотрудник " + model.getId(), "THIS_TAB", "ecm/employees/employee/");
        return resp;
    }

    @POST
    @Path("/employees/employee/update/")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public PersonResponseObject savePerson(Person p){
        personDAO.update(p);
        p = personDAO.getByID(Person.class, p.getId());
        String template = TemplateLoader.getTemplate("Person_VIEW.html");
        return new PersonResponseObject(p, template, "Сотрудник " + p.getId(), "THIS_TAB", "ecm/employees/employee/");
    }

    @POST
    @Path("/employees/employee/update/photo/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response savePhoto(@PathParam("id") int id, @Context HttpServletRequest req){
        String fileName = null;
        boolean isCorrectFormat = false;
        String contextRoot = req.getServletContext().getRealPath("/");

        if(!ServletFileUpload.isMultipartContent(req)){
            return Response.status(520).build();
        }

        FileItemFactory itemFactory = new DiskFileItemFactory();
        ServletFileUpload upload = new ServletFileUpload(itemFactory);
        try {
            List<FileItem> items = upload.parseRequest(req);
            for (FileItem item: items){
                if (item.getFieldName().equals("uploadedfiles[]")){
                    if(!"image/png".equals(item.getContentType())){
                        return Response.status(520).build();
                    }
                    File uploadDir = new File(contextRoot + "img/avatars");
                    File file = File.createTempFile("avatar_", ".png", uploadDir);
                    item.write(file);
                    fileName = file.getName();
                    isCorrectFormat = true;
                }
            }
        } catch (FileUploadException e){
            e.printStackTrace();
        } catch (Exception e){
            e.printStackTrace();
        }
        if(isCorrectFormat){
            Person p = (Person) personDAO.getByID(Person.class, id);
            p.setPhotoURL("img/avatars/" + fileName);
            personDAO.update(p);
        }
        return Response.ok().build();
    }
}
