package ru.vasya.service;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.vasya.db.DerbyConnection;
import ru.vasya.model.staff.Department;
import ru.vasya.model.staff.Person;
import ru.vasya.model.staff.Staff;
import ru.vasya.service.query.*;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class DerbyService {
    private static final Logger LOGGER = LoggerFactory.getLogger(DerbyService.class);

    private static DerbyService instance;

    private DerbyService(){

    }

    public static DerbyService getInstance(){
        if(instance==null){
            instance = new DerbyService();
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
            createTableSQL.append(", ");
            createTableSQL.append(f.getName());
            if(Person.class.equals(f.getType())){
                createTableSQL.append(" INTEGER");
            } else {
                createTableSQL.append(" VARCHAR(30)");
            }
        }
        createTableSQL.append(")");
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

    public void insertItem(Staff item){
        Connection conn = DerbyConnection.getConnection();
        try {
            QueryBuilder q = QueryBuilder.insertQuery(item);
            System.out.println(q.toSQL());
            PreparedStatement st = conn.prepareStatement(q.toSQL());
            st.execute();
        } catch (SQLException e){
            LOGGER.error("Could not insert new item in table " + item.getClass().getSimpleName(), e);
        } finally {
            closeConnection(conn);
        }
    }

    public Staff getById(Class c, int id){
        Staff s = null;
        Connection conn = DerbyConnection.getConnection();
        try {
            QueryBuilder q = QueryBuilder.selectQuery(c);
            q.addCriteria(Criteria.equals("id", id));
            s = (Staff)c.newInstance();
            PreparedStatement ps = conn.prepareStatement(q.toSQL());
            ResultSet rs = ps.executeQuery();
            rs.next();
            s.setId(rs.getInt("id"));
            for (Field f: c.getDeclaredFields()){
                f.setAccessible(true);
                if(Person.class.equals(f.getType())){
                    f.set(s, getById(Person.class, (Integer)rs.getObject(f.getName())));
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
        }

        return s;
    }

    public Set<Staff> getAll(Class c){
        Set<Staff> staff = new TreeSet<Staff>();
        Connection conn = DerbyConnection.getConnection();
        try {
            QueryBuilder q = QueryBuilder.selectQuery(c);
            ResultSet rs = conn.prepareStatement(q.toSQL()).executeQuery();
            while (rs.next()){
                Staff s = (Staff) c.newInstance();
                s.setId(rs.getInt("id"));
                for (Field f: c.getDeclaredFields()){
                    f.setAccessible(true);
                    if(Person.class.equals(f.getType())){
                        f.set(s, getById(Person.class, (Integer)rs.getObject(f.getName())));
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
        }
        finally {
            closeConnection(conn);
        }

        return staff;
    }

    public void updateItem(Staff item){
        Connection conn = DerbyConnection.getConnection();
        try {
            QueryBuilder q = QueryBuilder.updateQuery(item);
            q.addCriteria(Criteria.equals("id", item.getId()));
            PreparedStatement st = conn.prepareStatement(q.toSQL());
            st.executeUpdate();
        } catch (SQLException e){
            LOGGER.error("Could not insert new item in table " + item.getClass().getSimpleName(), e);
        } finally {
            closeConnection(conn);
        }
    }

    public void deleteItem(Staff item){
        Connection conn = DerbyConnection.getConnection();
        try {
            QueryBuilder q = QueryBuilder.deleteQuery(item);
            q.addCriteria(Criteria.equals("id", item.getId()));
            PreparedStatement st = conn.prepareStatement(q.toSQL());
            st.execute();
        } catch (SQLException e){
            LOGGER.error("Could not insert new item in table " + item.getClass().getSimpleName(), e);
        } finally {
            closeConnection(conn);
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

    public static void main(String[] args) {
        DerbyService dBs = getInstance();
        dBs.createTable(Person.class);
        dBs.createTable(Department.class);

        PersonServiceImpl ps = PersonServiceImpl.getInstance();
        List<Person> persons = ps.getPersonList();
        for (Person p : persons){
            dBs.insertItem(p);
        }
        System.out.println(dBs.getAll(Person.class));
        persons.get(1).setPosition("Master");
        dBs.updateItem(persons.get(1));
        System.out.println(dBs.getAll(Person.class));
        dBs.deleteItem(persons.get(1));
        System.out.println(dBs.getAll(Person.class));
        dBs.insertItem(persons.get(1));
        System.out.println(dBs.getAll(Person.class));
        List<Department> depts = ps.getRandomDepartmentList(10);
        for (Department d : depts){
            dBs.insertItem(d);
        }
        System.out.println(dBs.getAll(Department.class));
        System.out.println(dBs.getById(Department.class, 4));
    }
}
