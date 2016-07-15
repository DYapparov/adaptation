package ru.vasya.dao;

import org.junit.Test;
import ru.vasya.model.staff.Person;
import ru.vasya.model.staff.Post;
import ru.vasya.service.DerbyService;
import ru.vasya.service.PersonService;
import ru.vasya.service.PersonServiceImpl;

import static org.junit.Assert.*;

/**
 * Created by dyapparov on 15.07.2016.
 */
public class PersonDAOTest {

    PersonDAO pdao = new PersonDAO();
    DerbyService ds = new DerbyService();
    PostDAO postDao = new PostDAO();
    PersonService ps = new PersonServiceImpl();

    @Test
    public void createTable() throws Exception {

    }

    @Test
    public void dropTable() throws Exception {

    }

    @Test
    public void getByID() throws Exception {

    }

    @Test
    public void getByValues() throws Exception {

    }

    @Test
    public void getAll() throws Exception {
        pdao.dropTable(Person.class);
        pdao.createTable(Person.class);
        postDao.dropTable(Post.class);
        postDao.createTable(Post.class);
        Post post = new Post();
        post.setName("Another");
        postDao.create(post);
        post.setName("Another1");
        postDao.create(post);
        post.setName("Another2");
        postDao.create(post);
        for (Person p: ps.getPersonList()){
            pdao.create(p);
        }
        System.out.println(pdao.getAll(Person.class));
        System.out.println(postDao.getAll(Post.class));
        System.out.println(pdao.getByID(Person.class,2));
    }

    @Test
    public void create() throws Exception {

    }

    @Test
    public void update() throws Exception {

    }

    @Test
    public void delete() throws Exception {
    }

}