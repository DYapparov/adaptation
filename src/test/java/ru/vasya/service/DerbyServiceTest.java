package ru.vasya.service;

import org.junit.Test;
import ru.vasya.model.staff.Person;

import java.util.List;

/**
 * Created by dyapparov on 01.07.2016.
 */
public class DerbyServiceTest {
    DerbyService dBs = new DerbyService();
    PersonServiceImpl ps = PersonServiceImpl.getInstance();
    List<Person> persons = ps.getPersonList();

    @Test
    public void createTable() throws Exception {
        dBs.createTable(Person.class);
    }

    @Test
    public void insertItem() throws Exception {
        for (Person p : persons){
            dBs.insertItem(p);
        }
    }

    @Test
    public void getAll() throws Exception {
        System.out.println(dBs.getAll(Person.class));
    }

    @Test
    public void updateItem() throws Exception {
        persons.get(1).setPost("Master");
        dBs.updateItem(persons.get(1));
        System.out.println(dBs.getAll(Person.class));
    }

    @Test
    public void deleteItem() throws Exception {
        dBs.deleteItem(persons.get(1));
        System.out.println(dBs.getAll(Person.class));
        dBs.insertItem(persons.get(1));
        System.out.println(dBs.getAll(Person.class));
    }

}