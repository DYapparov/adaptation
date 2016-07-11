package ru.vasya.service;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.vasya.db.DerbyConnection;
import ru.vasya.model.staff.Person;
import ru.vasya.model.staff.Staff;
import ru.vasya.service.query.*;
import ru.vasya.service.query.parts.FieldToSelect;
import ru.vasya.service.query.parts.FieldsPart;
import ru.vasya.service.query.parts.LogicalOperation;
import ru.vasya.service.query.parts.Table;

import javax.ejb.Stateless;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

@Stateless
public class DerbyService<T extends Staff> {
    private static final Logger LOGGER = LoggerFactory.getLogger(DerbyService.class);

    public void createTable(Class c){
        Connection conn = DerbyConnection.getConnection();
        StringBuilder createTableSQL = new StringBuilder();
        createTableSQL.append("CREATE TABLE ")
                .append(c.getSimpleName())
                .append("(id INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1)");
        for(Field f: c.getDeclaredFields()){
            createTableSQL.append(", ");
            createTableSQL.append(f.getName());
            if(Person.class.equals(f.getType())){
                createTableSQL.append(" INTEGER");
            } else if (Date.class.equals(f.getType())){
                createTableSQL.append(" DATE");
            } else {
                createTableSQL.append(" VARCHAR(50)");
            }
        }
        createTableSQL.append(")");
        try{
            conn.createStatement().execute(createTableSQL.toString());
            LOGGER.info("Created table: " + c.getSimpleName());
            LOGGER.info("SQL: " + createTableSQL.toString());
        } catch (SQLException e){
            LOGGER.error("Could not create table for " + c.getSimpleName(), e);
        } finally {
            closeConnection(conn);
        }
    }

    public void dropTable(Class c){
        Connection conn = DerbyConnection.getConnection();
        try{
            conn.createStatement().execute("DROP TABLE " + c.getSimpleName());
        } catch (SQLException e){
            LOGGER.error("Could not delete table " + c.getSimpleName(), e);
        } finally {
            closeConnection(conn);
        }
    }

    public void insertItem(Staff item){
        Connection conn = DerbyConnection.getConnection();
        try {
            InsertQuery.Builder builder = InsertQuery.builder().setTable(new Table(item.getClass().getSimpleName(), item.getClass().getSimpleName()));
            for (Field f: item.getClass().getDeclaredFields()){
                f.setAccessible(true);
                builder.addValue(new FieldsPart(new FieldToSelect(f.getName(), f.getName().toUpperCase()), f.get(item), LogicalOperation.EQUALS));
            }
            PreparedStatement st = conn.prepareStatement(QueryToSqlConverter.convert(builder.build()));
            st.execute();
            LOGGER.info("Inserting: " + QueryToSqlConverter.convert(builder.build()));
        } catch (SQLException e){
            LOGGER.error("Could not insert new item in table " + item.getClass().getSimpleName(), e);
        } catch (IllegalAccessException e) {
            LOGGER.error("Could not read values of " + item, e);
        } catch (Exception e){
            LOGGER.error("Convertion to SQL failed ", e);
        } finally {
            closeConnection(conn);
        }
    }

    public T getById(Class c, int id){
        T s = null;
        Connection conn = DerbyConnection.getConnection();
        try {
            SelectQuery.Builder builder = SelectQuery.builder().setTable(new Table(c.getSimpleName(), c.getSimpleName().toUpperCase()));
            for (Field f: c.getDeclaredFields()){
                builder.addField(new FieldToSelect(f.getName().toUpperCase(), f.getName()));
            }
            builder.addField(new FieldToSelect("id", "ID"));
            builder.addWherePart(new FieldsPart(new FieldToSelect("id", "ID"), id, LogicalOperation.EQUALS));
            s = (T)c.newInstance();
            SelectQuery query = builder.build();
            LOGGER.info(QueryToSqlConverter.convert(query));//----------------------------------------------------------
            PreparedStatement ps = conn.prepareStatement(QueryToSqlConverter.convert(query));
            ResultSet rs = ps.executeQuery();
            rs.next();
            s.setId(rs.getInt("id"));
            for (Field f: c.getDeclaredFields()){
                f.setAccessible(true);
                if(Person.class.equals(f.getType())){
                    f.set(s, getById(Person.class, (Integer)rs.getObject(f.getName())));
                } else if(Date.class.equals(f.getType())) {
                    f.set(s, new SimpleDateFormat("yyyy-MM-dd").parse(rs.getString(f.getName())));
                } else {
                    f.set(s, rs.getObject(f.getName()));
                }
            }
        } catch (SQLException e){
            LOGGER.error("Could not get " + c.getSimpleName() + " with id " + id, e);
        }  catch (InstantiationException e){
            LOGGER.error("Could not instantiate new object " + c.getSimpleName(), e);
        } catch (IllegalAccessException e){
            LOGGER.error("Could not instantiate new object " + c.getSimpleName(), e);
        } catch (Exception e){
            LOGGER.error("Convertion to SQL failed ", e);
        } finally {
            closeConnection(conn);
        }

        return s;
    }

    public Set<T> getAll(Class c){
        Set<T> staff = new TreeSet<T>();
        Connection conn = DerbyConnection.getConnection();
        try {
            SelectQuery.Builder builder = SelectQuery.builder().setTable(new Table(c.getSimpleName(), c.getSimpleName()));
            for (Field f: c.getDeclaredFields()){
                builder.addField(new FieldToSelect(f.getName(), f.getName().toUpperCase()));
            }
            builder.addField(new FieldToSelect("id", "ID"));
            SelectQuery query = builder.build();
            LOGGER.info(QueryToSqlConverter.convert(query));//----------------------------------------------------------
            ResultSet rs = conn.prepareStatement(QueryToSqlConverter.convert(query)).executeQuery();
            while (rs.next()){
                T s = (T) c.newInstance();
                s.setId(rs.getInt("id"));
                for (Field f: c.getDeclaredFields()){
                    f.setAccessible(true);
                    if(Person.class.equals(f.getType())){
                        f.set(s, getById(Person.class, (Integer)rs.getObject(f.getName())));
                    } else if (Date.class.equals(f.getType())) {
                        f.set(s, new SimpleDateFormat("yyyy-MM-dd").parse(rs.getString(f.getName())));
                    } else {
                        f.set(s, rs.getObject(f.getName()));
                    }
                }
                staff.add(s);
            }
        } catch (SQLException e){
            LOGGER.error("Could not get data from " + c.getSimpleName(), e);
        } catch (InstantiationException e){
            LOGGER.error("Could not instantiate new object " + c.getSimpleName(), e);
        } catch (IllegalAccessException e){
            LOGGER.error("Could not instantiate new object " + c.getSimpleName(), e);
        } catch (Exception e){
            LOGGER.error("Convertion to SQL failed ", e);
        } finally {
            closeConnection(conn);
        }

        return staff;
    }

    public void updateItem(Staff item){
        Connection conn = DerbyConnection.getConnection();
        try {
            UpdateQuery.Builder builder = UpdateQuery.builder().setTable(new Table(item.getClass().getSimpleName(), item.getClass().getSimpleName()));
            builder.addWherePart(new FieldsPart(new FieldToSelect("id", "ID"), item.getId(), LogicalOperation.EQUALS));
            for (Field f: item.getClass().getDeclaredFields()){
                f.setAccessible(true);
                builder.addUpdateField(new FieldsPart(new FieldToSelect(f.getName(), f.getName().toUpperCase()), f.get(item), LogicalOperation.EQUALS));
            }
            PreparedStatement st = conn.prepareStatement(QueryToSqlConverter.convert(builder.build()));
            st.executeUpdate();
        } catch (SQLException e){
            LOGGER.error("Could not update item in table " + item, e);
        } catch (IllegalAccessException e){
            LOGGER.error("Could not read object fields for " + item, e);
        } catch (Exception e){
            LOGGER.error("Convertion to SQL failed ", e);
        } finally {
            closeConnection(conn);
        }
    }

    public void deleteItem(Staff item){
        Connection conn = DerbyConnection.getConnection();
        try {
            DeleteQuery.Builder builder = DeleteQuery.builder().setTable(new Table(item.getClass().getSimpleName(), item.getClass().getSimpleName()));
            builder.addWherePart(new FieldsPart(new FieldToSelect("id", "ID"), item.getId(), LogicalOperation.EQUALS));
            PreparedStatement st = conn.prepareStatement(QueryToSqlConverter.convert(builder.build()));
            st.execute();
        } catch (SQLException e){
            LOGGER.error("Could not insert new item in table " + item.getClass().getSimpleName(), e);
        } catch (Exception e){
            LOGGER.error("Convertion to SQL failed ", e);
        } finally {
            closeConnection(conn);
        }
    }

    public boolean contains(Class c, Map<String, Object> values){
        Connection conn = DerbyConnection.getConnection();
        boolean result = false;
        try {
            SelectQuery.Builder builder = SelectQuery.builder().setTable(new Table(c.getSimpleName(), c.getSimpleName().toUpperCase()));
            for(String field: values.keySet()){
                builder.addWherePart(new FieldsPart(new FieldToSelect(field, field.toUpperCase()), values.get(field), LogicalOperation.EQUALS));
            }
            SelectQuery query = builder.build();
            LOGGER.info(QueryToSqlConverter.convert(query));//------------------------------------------------------------------
            PreparedStatement ps = conn.prepareStatement(QueryToSqlConverter.convert(query));
            ResultSet rs = ps.executeQuery();
            result = rs.next();
        } catch (SQLException e){
            LOGGER.error("Could not get " + c.getSimpleName() + " with specified parameters", e);
        } catch (Exception e){
            LOGGER.error("Convertion to SQL failed ", e);
        } finally {
            closeConnection(conn);
        }
        return result;
    }

    private void closeConnection(Connection conn){
        try {
            if (conn!=null&&!conn.isClosed())
                conn.close();
            conn=null;
        } catch (SQLException e){
            LOGGER.error("Could not close database connection", e);
        }
    }
}
