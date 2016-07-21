package ru.vasya.jsf;

import ru.vasya.dao.PersonDAO;
import ru.vasya.model.staff.Person;

import javax.faces.bean.ManagedBean;
import javax.ejb.EJB;
import javax.faces.bean.RequestScoped;
import javax.faces.component.html.HtmlDataTable;
import java.util.Random;
import java.util.Set;

/**
 * Created by dyapparov on 18.07.2016.
 */
@ManagedBean(name = "PersonsBean", eager = true)
@RequestScoped
public class PersonsBean {

    private HtmlDataTable personsTable;

    private Set<Person> persons;

    @EJB
    PersonDAO pdao;

    public PersonsBean(){

    }

    public Set<Person> getPersons() {
        return persons;
    }

    public void setPersons(Set<Person> persons) {
        this.persons = persons;
    }

    public HtmlDataTable getPersonsTable() {
        return personsTable;
    }

    public void setPersonsTable(HtmlDataTable personsTable) {
        this.personsTable = personsTable;
    }

    public Set<Person> getAll(){
        if (persons==null) {
            persons = pdao.getAll(Person.class);
        }
        return persons;
    }

    public void click(){
        Person p = (Person) personsTable.getRowData();
        System.out.println(p);
    }

    public int getData(){
        return new Random().nextInt();
    }

}
