package ru.vasya.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.vasya.model.document.Storable;
import ru.vasya.model.staff.Staff;

import javax.sql.RowSet;
import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by dyapparov on 15.07.2016.
 */
public class Mapper {
    private static final Logger LOGGER = LoggerFactory.getLogger(Mapper.class);

    public Mapper(){

    }

    public Object writeObject(Class c, ResultSet rs){

        if(Staff.class.isAssignableFrom(c)){
            Object item = null;
            try {
                item = c.newInstance();
                ((Storable)item).setId(rs.getInt("id"));
                for (Field f: c.getDeclaredFields()){
                    f.setAccessible(true);
                    if(Storable.class.isAssignableFrom(f.getType())){
                        f.set(item, new DAOimpl<Storable>().getByID(f.getType(), (Integer)rs.getObject(f.getName())));
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

    public Map<String, Object> writeMap(ResultSet rs){
        Map<String, Object> result = new TreeMap<String, Object>();
        try {
            ResultSetMetaData metaData = rs.getMetaData();
            for (int i = 1; i<metaData.getColumnCount()+1; i++){
                result.put(metaData.getColumnName(i), rs.getObject(i));
            }
        } catch (SQLException e){
            LOGGER.error("SQL exception ", e);
        }
        return result;
    }

}
