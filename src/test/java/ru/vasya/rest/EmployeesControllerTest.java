package ru.vasya.rest;

import org.junit.Test;
import ru.vasya.dao.PersonDAO;

import static org.junit.Assert.*;

/**
 * Created by dyapparov on 22.07.2016.
 */
public class EmployeesControllerTest {
    @Test
    public void getEmployees() throws Exception {

    }

    @Test
    public void getPersonDocs() throws Exception {

    }

    @Test
    public void getPerson() throws Exception {
        EmployeesController ec = new EmployeesController();
        ec.personDAO = new PersonDAO();

        System.out.println(ec.getPerson(1));

    }

    @Test
    public void savePerson() throws Exception {

    }

    @Test
    public void savePhoto() throws Exception {

    }

}