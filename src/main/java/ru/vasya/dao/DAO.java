package ru.vasya.dao;

import java.util.List;

/**
 * Created by dyapparov on 30.06.2016.
 */
public interface DAO<T> {
    T getByID(Class c, Integer id);
    List<T> getAll(Class c);
    void create(T t);
    void update(T t);
    void delete(T t);
}
