package ru.vasya.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.vasya.model.document.Storable;
import ru.vasya.model.staff.Staff;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Set;
import java.util.TreeSet;

/**
 * Created by dyapparov on 15.07.2016.
 */
public class Mapper {
    private static final Logger LOGGER = LoggerFactory.getLogger(Mapper.class);

    public Object writeObject(Class c, ResultSet rs){

        if(Staff.class.isAssignableFrom(c)){
            Object item = null;
            try {
                item = c.newInstance();
                ((Storable)item).setId(rs.getInt("id"));
                for (Field f: c.getDeclaredFields()){
                    f.setAccessible(true);
                    if(Storable.class.isAssignableFrom(f.getType())){
                        f.set(item, new PersonDAO().getByID(f.getType(), (Integer)rs.getObject(f.getName())));
                    }  else if (Date.class.equals(f.getType())) {
                        f.set(item, new SimpleDateFormat("yyyy-MM-dd").parse(rs.getString(f.getName())));
                    } else {
                        f.set(item, rs.getObject(f.getName()));
                    }
                }
            } catch (ParseException e) {
                LOGGER.error("Could not parse date for " + c.getSimpleName(), e);
            } catch (SQLException e) {
                LOGGER.error("Could not get data from " + c.getSimpleName(), e);
            } catch (InstantiationException e) {
                LOGGER.error("Could not instantiate new object " + c.getSimpleName(), e);
            } catch (IllegalAccessException e) {
                LOGGER.error("Could not instantiate new object " + c.getSimpleName(), e);
            }
            return item;
        }
        return null;
    }
}
