package ru.vasya.service;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.vasya.dao.Mapper;
import ru.vasya.db.DerbyConnection;
import ru.vasya.model.document.Storable;
import ru.vasya.model.staff.Person;
import ru.vasya.model.staff.Post;
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

public class DerbyService {
    private static final Logger LOGGER = LoggerFactory.getLogger(DerbyService.class);

    private Mapper mapper;

    public DerbyService(){
        mapper = new Mapper();
    }

    public TreeSet<Storable> executeQuery(SelectQuery query){
        Connection conn = DerbyConnection.getConnection();
        ResultSet rs = null;
        String queryString= "";
        TreeSet<Storable> items = new TreeSet<Storable>();
        try {
            queryString = QueryToSqlConverter.convert(query);
            PreparedStatement ps = conn.prepareStatement(queryString);
            rs = ps.executeQuery();
            while (rs.next()){
                items.add((Storable)mapper.writeObject(query.getObjectClass(), rs));
            }
        } catch (SQLException e){
            LOGGER.error("Could not executeQuery " + queryString, e);
        } catch (Exception e){
            LOGGER.error("Query convertion to SQL failed ", e);
        } finally {
            closeConnection(conn);
        }
        return items;
    }

    public void execute(Query query){
        Connection conn = DerbyConnection.getConnection();
        String queryString= "";
        try {
            queryString = QueryToSqlConverter.convert(query);
            PreparedStatement ps = conn.prepareStatement(queryString);
            ps.execute();
        } catch (SQLException e){
            LOGGER.error("Could not executeQuery " + queryString, e);
        } catch (Exception e){
            LOGGER.error("Query convertion to SQL failed ", e);
        } finally {
            closeConnection(conn);
        }
    }

    public void closeConnection(Connection conn){
        try {
            if (conn!=null&&!conn.isClosed())
                conn.close();
            conn=null;
        } catch (SQLException e){
            LOGGER.error("Could not close database connection", e);
        }
    }
}
