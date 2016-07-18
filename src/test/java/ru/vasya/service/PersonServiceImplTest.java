package ru.vasya.service;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by dyapparov on 18.07.2016.
 */
public class PersonServiceImplTest {

    PersonServiceImpl ps = new PersonServiceImpl();
    @Test
    public void getPersonList() throws Exception {
        ps.getPersonList();
    }

    @Test
    public void getRandomPersonList() throws Exception {
        System.out.println(ps.getRandomPersonList(5));

    }

    @Test
    public void getRandomOrganizationList() throws Exception {

    }

    @Test
    public void getRandomDepartmentList() throws Exception {

    }

}