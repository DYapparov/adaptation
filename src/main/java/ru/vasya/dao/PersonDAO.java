package ru.vasya.dao;


import ru.vasya.model.staff.Person;

import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;
import java.util.List;

@Stateless
public class PersonDAO implements DAO<Person> {

    @PersistenceContext
    private EntityManager em;
    @Resource
    private UserTransaction ut;

    public Person getByID(Class c, Integer id) {
        return (Person)em.find(c, id);
    }

    public List<Person> getAll(Class c) {
        return em.createQuery("SELECT * FROM " + c.getSimpleName()).getResultList();
    }

    public void create(Person person) {
        try {
            ut.begin();
            em.persist(person);
            ut.commit();
        } catch (Exception e){
            try {
                ut.rollback();
            } catch (SystemException ex){
                ex.printStackTrace();
            }
            e.printStackTrace();
        }
    }

    public void update(Person person) {
        try {
            ut.begin();
            em.merge(person);
            ut.commit();
        } catch (Exception e){
            try {
                ut.rollback();
            } catch (SystemException ex){
                ex.printStackTrace();
            }
            e.printStackTrace();
        }
    }

    public void delete(Person person) {
        em.remove(person);
    }
}
