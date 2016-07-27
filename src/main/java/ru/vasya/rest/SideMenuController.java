package ru.vasya.rest;

import ru.vasya.dao.PersonDAO;
import ru.vasya.model.document.Document;
import ru.vasya.model.document.Incoming;
import ru.vasya.model.document.Outgoing;
import ru.vasya.model.document.Task;
import ru.vasya.model.staff.Person;
import ru.vasya.rest.response.document.IncomingsResponseObject;
import ru.vasya.rest.response.document.OutgoingsResponseObject;
import ru.vasya.rest.response.document.TasksResponseObject;
import ru.vasya.rest.response.gridx.GridxColumn;
import ru.vasya.rest.response.person.PersonsResponseObject;
import ru.vasya.service.DocService;
import ru.vasya.util.TemplateLoader;

import javax.ejb.EJB;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.*;

@Path("/list")
public class SideMenuController {

    @EJB
    PersonDAO pdao;

    @EJB
    DocService ds;

    @GET
    @Path("/persons")
    @Produces(MediaType.APPLICATION_JSON)
    public PersonsResponseObject getPersonsTabParams(){
        Set<Person> model = pdao.getAll(Person.class);
        String template = TemplateLoader.getTemplate("Persons_VIEW.html");
        List<GridxColumn> columns = new ArrayList<GridxColumn>();
        columns.add(new GridxColumn("lastName", "Lastname", "25%", null));
        columns.add(new GridxColumn("firstName", "Firstname", "20%", null));
        columns.add(new GridxColumn("middleName", "Middlename", "25%", null));
        columns.add(new GridxColumn("post", "Position", "15%", "function (value, rowId) {return value.post.name;}"));
        columns.add(new GridxColumn("birthday", "Birthday", "15%", "function (value, rowId) {var date = stamp.fromISOString(value.birthday); return locale.format(date, {selector:'date', datePattern:'dd-MM-yyyy'});}"));
        PersonsResponseObject resp = new PersonsResponseObject(model, columns, template, "Сотрудники", "NEW_TAB", "ecm/employees/employee/","app/GridxScript");
        return resp;
    }

    @GET
    @Path("/tasks")
    @Produces(MediaType.APPLICATION_JSON)
    public TasksResponseObject getTasksTabParams(){
        Map<Person, TreeSet<Document>> docs = ds.getDocuments();
        Set<Task> model = new TreeSet<Task>();
        for(Person p : docs.keySet()){
            for(Document d : docs.get(p)){
                if (d instanceof Task){
                    model.add((Task)d);
                }
            }
        }
        String template = TemplateLoader.getTemplate("Persons_VIEW.html");
        List<GridxColumn> columns = new ArrayList<GridxColumn>();
        columns.add(new GridxColumn("docName", "Наименование", "15%", null));
        columns.add(new GridxColumn("registrationNumber", "Регистрационный №", "5%", null));
        columns.add(new GridxColumn("registerDate", "Дата регистрации", "10%", "function (value, rowId) {var date = stamp.fromISOString(value.registerDate); return locale.format(date, {selector:'date', datePattern:'dd-MM-yyyy'});}"));
        columns.add(new GridxColumn("author", "Автор", "15%", "function (value, rowId) {return value.author.lastName + ' ' + value.author.firstName + ' ' + value.author.middleName + ', ' +  value.author.post.name;}"));
        columns.add(new GridxColumn("deliveryDate", "Дата получения", "10%", "function (value, rowId) {var date = stamp.fromISOString(value.deliveryDate); return locale.format(date, {selector:'date', datePattern:'dd-MM-yyyy'});}"));
        columns.add(new GridxColumn("finishDate", "Дата получения", "10%", "function (value, rowId) {var date = stamp.fromISOString(value.finishDate); return locale.format(date, {selector:'date', datePattern:'dd-MM-yyyy'});}"));
        columns.add(new GridxColumn("performer", "Исполнитель", "15%", "function (value, rowId) {return value.performer.lastName + ' ' + value.performer.firstName + ' ' + value.performer.middleName + ', ' +  value.performer.post.name;}"));
        columns.add(new GridxColumn("controlTag", "Контрольность", "5%", "function (value, rowId) {if (value.controlTag==true) return 'Да'; else return 'Нет';}"));
        columns.add(new GridxColumn("controller", "Контролирующий", "15%", "function (value, rowId) {if (value.controlTag==true) return value.controller.lastName + ' ' + value.controller.firstName + ' ' + value.controller.middleName + ', ' +  value.controller.post.name; else return '';}"));
        TasksResponseObject resp = new TasksResponseObject(model, columns, template, "Поручения", "NEW_TAB", "ecm/employees/employee/","app/GridxScript");
        return resp;
    }

    @GET
    @Path("/incomings")
    @Produces(MediaType.APPLICATION_JSON)
    public IncomingsResponseObject getIncomingsTabParams(){
        Map<Person, TreeSet<Document>> docs = ds.getDocuments();
        Set<Incoming> model = new TreeSet<Incoming>();
        for(Person p : docs.keySet()){
            for(Document d : docs.get(p)){
                if (d instanceof Incoming){
                    model.add((Incoming)d);
                }
            }
        }
        String template = TemplateLoader.getTemplate("Persons_VIEW.html");
        List<GridxColumn> columns = new ArrayList<GridxColumn>();
        columns.add(new GridxColumn("docName", "Наименование", "15%", null));
        columns.add(new GridxColumn("registrationNumber", "Регистрационный №", "5%", null));
        columns.add(new GridxColumn("registerDate", "Дата регистрации", "15%", "function (value, rowId) {var date = stamp.fromISOString(value.registerDate); return locale.format(date, {selector:'date', datePattern:'dd-MM-yyyy'});}"));
        columns.add(new GridxColumn("author", "Автор", "15%", "function (value, rowId) {return value.author.lastName + ' ' + value.author.firstName + ' ' + value.author.middleName + ', ' +  value.author.post.name;}"));
        columns.add(new GridxColumn("origination", "От кого", "15%", "function (value, rowId) {return value.origination.lastName + ' ' + value.origination.firstName + ' ' + value.origination.middleName + ', ' +  value.origination.post.name;}"));
        columns.add(new GridxColumn("destination", "Кому", "15%", "function (value, rowId) {return value.destination.lastName + ' ' + value.destination.firstName + ' ' + value.destination.middleName + ', ' +  value.destination.post.name;}"));
        columns.add(new GridxColumn("outgoingDate", "Исходящая дата", "15%", "function (value, rowId) {var date = stamp.fromISOString(value.outgoingDate); return locale.format(date, {selector:'date', datePattern:'dd-MM-yyyy'});}"));
        columns.add(new GridxColumn("outgoingNumber", "Исходящий №", "5%", null));
        IncomingsResponseObject resp = new IncomingsResponseObject(model, columns, template, "Входящие", "NEW_TAB", "ecm/employees/employee/","app/GridxScript");
        return resp;
    }

    @GET
    @Path("/outgoings")
    @Produces(MediaType.APPLICATION_JSON)
    public OutgoingsResponseObject getOutgoingsTabParams(){
        Map<Person, TreeSet<Document>> docs = ds.getDocuments();
        Set<Outgoing> model = new TreeSet<Outgoing>();
        for(Person p : docs.keySet()){
            for(Document d : docs.get(p)){
                if (d instanceof Outgoing){
                    model.add((Outgoing)d);
                }
            }
        }
        String template = TemplateLoader.getTemplate("Persons_VIEW.html");
        List<GridxColumn> columns = new ArrayList<GridxColumn>();
        columns.add(new GridxColumn("docName", "Наименование", "15%", null));
        columns.add(new GridxColumn("registrationNumber", "Регистрационный №", "5%", null));
        columns.add(new GridxColumn("registerDate", "Дата регистрации", "20%", "function (value, rowId) {var date = stamp.fromISOString(value.registerDate); return locale.format(date, {selector:'date', datePattern:'dd-MM-yyyy'});}"));
        columns.add(new GridxColumn("author", "Автор", "25%", "function (value, rowId) {return value.author.lastName + ' ' + value.author.firstName + ' ' + value.author.middleName + ', ' +  value.author.post.name;}"));
        columns.add(new GridxColumn("destination", "Кому", "25%", "function (value, rowId) {return value.destination.lastName + ' ' + value.destination.firstName + ' ' + value.destination.middleName + ', ' +  value.destination.post.name;}"));
        columns.add(new GridxColumn("deliveryMethod", "Способ доставки", "10%", null));
        OutgoingsResponseObject resp = new OutgoingsResponseObject(model, columns, template, "Исходящие", "NEW_TAB", "ecm/employees/employee/","app/GridxScript");
        return resp;
    }


}
