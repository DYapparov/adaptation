package ru.vasya.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.vasya.model.document.Storable;
import ru.vasya.model.staff.Post;
import ru.vasya.service.db.DerbyService;
import ru.vasya.service.db.query.*;
import ru.vasya.service.db.query.parts.FieldToSelect;
import ru.vasya.service.db.query.parts.FieldsPart;
import ru.vasya.service.db.query.parts.LogicalOperation;
import ru.vasya.service.db.query.parts.Table;

import java.lang.reflect.Field;
import java.util.*;

/**
 * Created by dyapparov on 15.07.2016.
 */
public class DAOimpl<T extends Storable> implements DAO<T>{

    private static final Logger LOGGER = LoggerFactory.getLogger(DAOimpl.class);

    private DerbyService ds;

    public DAOimpl(){
        ds = new DerbyService();
    }

    @Override
    public void createTable(Class c) {
        StringBuilder createTableSQL = new StringBuilder();
        createTableSQL.append("CREATE TABLE ")
                .append(c.getSimpleName())
                .append("(id INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1)");
        for(Field f: c.getDeclaredFields()){
            createTableSQL.append(", ");
            createTableSQL.append(f.getName());
            if(Storable.class.isAssignableFrom(f.getType())){
                createTableSQL.append(" INTEGER");
            } else if (Post.class.equals(f.getType())){
                createTableSQL.append(" INTEGER");
            } else if (Date.class.equals(f.getType())){
                createTableSQL.append(" DATE");
            } else {
                createTableSQL.append(" VARCHAR(50)");
            }
        }
        createTableSQL.append(")");
        Query query = new CustomQuery(createTableSQL.toString());
        ds.execute(query);
        LOGGER.info("SQL: " + createTableSQL.toString());
        LOGGER.info("Created table: " + c.getSimpleName());

    }

    @Override
    public void dropTable(Class c) {
        try {
            Query query = new CustomQuery("DROP  TABLE " + ((T) c.newInstance()).getTable());
            ds.execute(query);
        } catch (InstantiationException e){
            LOGGER.error("Could not instantiate new object " + c.getSimpleName(), e);
        } catch (IllegalAccessException e){
            LOGGER.error("Could not read object fields for " + c.getSimpleName(), e);
        }
    }

    @Override
    public T getByID(Class c, Integer id) {
        T item=null;
        try{
            item = (T) c.newInstance();
        } catch (InstantiationException e){
            LOGGER.error("Could not instantiate new object " + c.getSimpleName(), e);
        }  catch (IllegalAccessException e){
            LOGGER.error("Could not read object fields for " + c.getSimpleName(), e);
        }

        SelectQuery.Builder builder = SelectQuery.builder();
        //Set table name
        builder.setTable(new Table(item.getTable(), item.getTable()));
        //Set selected object class
        builder.setType(item.getClass());
        //Set columns to select
        builder.addField(new FieldToSelect("id", "ID"));
        for (Field f: c.getDeclaredFields()){
            builder.addField(new FieldToSelect(f.getName().toUpperCase(), f.getName()));
        }
        //Set where cause
        builder.addWherePart(new FieldsPart(new FieldToSelect("id", "ID"), id, LogicalOperation.EQUALS));
        //Build final query
        SelectQuery query = builder.build();

        Set<T> items  = (Set<T>) ds.executeQuery(query);
        if(items.iterator().hasNext()){
            item = items.iterator().next();
        }
        return item;
    }

    @Override
    public Set<T> getByValues(Class c, Map<String, Object> values) {
        T item=null;
        try{
            item = (T) c.newInstance();
        } catch (InstantiationException e){
            LOGGER.error("Could not instantiate new object " + c.getSimpleName(), e);
        }  catch (IllegalAccessException e){
            LOGGER.error("Could not read object fields for " + c.getSimpleName(), e);
        }

        SelectQuery.Builder builder = SelectQuery.builder();
        //Set table name
        builder.setTable(new Table(item.getTable(), item.getTable()));
        //Set selected object class
        builder.setType(item.getClass());
        //Set columns to select
        builder.addField(new FieldToSelect("id", "ID"));
        for (Field f: c.getDeclaredFields()){
            builder.addField(new FieldToSelect(f.getName().toUpperCase(), f.getName()));
        }
        //Set where cause
        for (String fieldName: values.keySet()){
            builder.addWherePart(new FieldsPart(new FieldToSelect(fieldName, fieldName), values.get(fieldName), LogicalOperation.EQUALS));
        }
        //Build final query
        SelectQuery query = builder.build();

        Set<T> items  = (Set<T>) ds.executeQuery(query);
        return items;
    }

    @Override
    public Set<T> getAll(Class c) {
        T item=null;
        try{
            item = (T) c.newInstance();
        } catch (InstantiationException e){
            LOGGER.error("Could not instantiate new object " + c.getSimpleName(), e);
        }  catch (IllegalAccessException e){
            LOGGER.error("Could not read object fields for " + c.getSimpleName(), e);
        }

        SelectQuery.Builder builder = SelectQuery.builder();
        //Set table name
        builder.setTable(new Table(item.getTable(), item.getTable()));
        //Set selected object class
        builder.setType(item.getClass());
        //Set columns to select
        builder.addField(new FieldToSelect("id", "ID"));
        for (Field f: c.getDeclaredFields()){
            builder.addField(new FieldToSelect(f.getName(), f.getName().toUpperCase()));
        }

        //Build final query
        SelectQuery query = builder.build();
        Set<T> items  = (Set<T>) ds.executeQuery(query);
        return items;
    }

    @Override
    public void create(Storable item) {
        try {
            InsertQuery.Builder builder = InsertQuery.builder();
            builder.setTable(new Table(item.getTable(), item.getTable()));
            for (Field f : item.getClass().getDeclaredFields()) {
                f.setAccessible(true);
                builder.addValue(new FieldsPart(new FieldToSelect(f.getName(), f.getName().toUpperCase()), f.get(item), LogicalOperation.EQUALS));
            }
            Query query = builder.build();
            ds.execute(query);
        } catch (IllegalAccessException e) {
            LOGGER.error("Could not read values of " + item, e);
        }
    }

    @Override
    public void update(Storable item) {
        try {
            UpdateQuery.Builder builder = UpdateQuery.builder();
            builder.setTable(new Table(item.getTable(), item.getTable()));
            builder.addWherePart(new FieldsPart(new FieldToSelect("id", "ID"), item.getId(), LogicalOperation.EQUALS));
            for (Field f: item.getClass().getDeclaredFields()){
                f.setAccessible(true);
                builder.addUpdateField(new FieldsPart(new FieldToSelect(f.getName(), f.getName().toUpperCase()), f.get(item), LogicalOperation.EQUALS));
            }
            Query query = builder.build();
            ds.execute(query);
        }  catch (IllegalAccessException e){
            LOGGER.error("Could not read object fields for " + item, e);
        }
    }

    @Override
    public void delete(Storable item) {
        DeleteQuery.Builder builder = DeleteQuery.builder();
        builder.setTable(new Table(item.getTable(), item.getTable()));
        builder.addWherePart(new FieldsPart(new FieldToSelect("id", "ID"), item.getId(), LogicalOperation.EQUALS));
        Query query = builder.build();
        ds.execute(query);
    }

}
