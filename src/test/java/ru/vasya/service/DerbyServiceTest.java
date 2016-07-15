package ru.vasya.service;

import org.junit.Test;
import ru.vasya.model.staff.Person;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by dyapparov on 01.07.2016.
 */
public class DerbyServiceTest {
    private DerbyService<Person> dBs = new DerbyService<Person>();

    private PersonServiceImpl ps = PersonServiceImpl.getInstance();

    private List<Person> persons = ps.getPersonList();

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
    /*
    @Test
    public void updateItem() throws Exception {
        persons.get(1).setPost("Master");
        dBs.updateItem(persons.get(1));
        System.out.println(dBs.getAll(Person.class));
    }
    */
    @Test
    public void deleteItem() throws Exception {
        dBs.deleteItem(persons.get(1));
        System.out.println(dBs.getAll(Person.class));
        dBs.insertItem(persons.get(1));
        System.out.println(dBs.getAll(Person.class));
    }

    @Test
    public void containsTest() throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("firstName", "Auwesab");
        System.out.println(dBs.contains(Person.class, map));
    }

}