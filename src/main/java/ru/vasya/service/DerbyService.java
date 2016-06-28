package ru.vasya.service;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.vasya.db.DerbyConnection;
import ru.vasya.model.staff.Person;
import ru.vasya.model.staff.Staff;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class DerbyService {
    private static final Logger LOGGER = LoggerFactory.getLogger(DerbyService.class);

    private static DerbyService instance;

    private DerbyService(){

    }

    public static DerbyService getInstance(){
        if(instance==null){
            return new DerbyService();
        }
        return instance;
    }

    public void createTable(Class c){
        Connection conn = DerbyConnection.getConnection();
        StringBuilder createTableSQL = new StringBuilder();
        createTableSQL.append("CREATE TABLE ")
                    .append(c.getSimpleName())
                    .append("(id INTEGER PRIMARY KEY NOT NULL");
        for(Field f: c.getDeclaredFields()){
            createTableSQL.append(",");
            createTableSQL.append(f.getName());
            createTableSQL.append(" VARCHAR(30)");
        }
        createTableSQL.append(")");
        LOGGER.info("SQL: " + createTableSQL.toString());
        try{
            conn.createStatement().execute("DROP TABLE " + c.getSimpleName());
            conn.createStatement().execute(createTableSQL.toString());
            LOGGER.info("Created table: " + c.getSimpleName());
            LOGGER.info("SQL: " + createTableSQL.toString());
        } catch (SQLException e){
            LOGGER.error("Could not create table for " + c.getSimpleName(), e);
        } finally {
            closeConnection(conn);
        }
    }

    public void addItem(Staff item){
        Connection conn = DerbyConnection.getConnection();
        StringBuilder addItemSQL = new StringBuilder();
        addItemSQL.append("INSERT INTO ")
                .append(item.getClass().getSimpleName())
                .append(" VALUES (?"); //this is for id
        for (Field f:item.getClass().getDeclaredFields()){
            addItemSQL.append(", ?");
        }
        addItemSQL.append(")");
        try {
            PreparedStatement st = conn.prepareStatement(addItemSQL.toString());
            st.setInt(1, item.getId());
            int counter = 2;
            for (Field f: item.getClass().getDeclaredFields()){
                f.setAccessible(true);
                st.setString(counter++, f.get(item).toString());
            }
        } catch (SQLException e){
            LOGGER.error("Could not insert new item in table " + item.getClass().getSimpleName(), e);
        } catch (IllegalAccessException e){
            LOGGER.error("Read attributes of " + item, e);
        } finally {
            closeConnection(conn);
        }
    }

    public static void main(String[] args) {
        DerbyService dBs = getInstance();
        PersonService ps = PersonService.getInstance();
        dBs.createTable(Person.class);
        List<Person> persons = ps.getPersonList();
        for (Person p : persons){
            dBs.addItem(p);
        }
        try {
            ResultSet rs = DerbyConnection.getConnection().createStatement().executeQuery("SELECT * FROM person");
            System.out.println(rs);

        } catch (SQLException e){
            LOGGER.error("Could not getData ", e);
        }
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
