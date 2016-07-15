package ru.vasya.dao;

import ru.vasya.model.document.Storable;

import javax.ejb.Local;
import java.util.Map;
import java.util.Set;

/**
 * Created by dyapparov on 30.06.2016.
 */
public interface DAO<T extends Storable> {
    void createTable(Class c);
    void dropTable(Class c);
    T getByID(Class c, Integer id);
    Set<T> getByValues(Class c, Map<String, Object> values);
    Set<T> getAll(Class c);
    void create(T t);
    void update(T t);
    void delete(T t);
}
