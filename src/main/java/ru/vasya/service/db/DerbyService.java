package ru.vasya.service.db;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.vasya.dao.Mapper;
import ru.vasya.db.DerbyConnection;
import ru.vasya.model.document.Storable;
import ru.vasya.service.db.query.*;

import javax.sql.RowSet;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

public class DerbyService {
    private static final Logger LOGGER = LoggerFactory.getLogger(DerbyService.class);

    private Mapper mapper;

    public DerbyService(){
        mapper = new Mapper();
    }

    public Iterable executeQuery(SelectQuery query){
        Connection conn = DerbyConnection.getConnection();
        ResultSet rs = null;
        String queryString  = "";
        Iterable items = null;
        try {
            queryString = QueryToSqlConverter.convert(query);
            PreparedStatement ps = conn.prepareStatement(queryString);
            rs = ps.executeQuery();
            LOGGER.info("Executing query: " + queryString);//----------------------------------------------------
            if(query.getType().equals(RowSet.class)){
                items = new HashSet<Map<String, Object>>();
                while (rs.next()){
                    ((Set)items).add(mapper.writeMap(rs));
                }
            } else {
                items = new TreeSet<Storable>();
                while (rs.next()){
                    ((Set)items).add(mapper.writeObject(query.getType(), rs));
                }
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
            LOGGER.info("Executing query: " + queryString);//----------------------------------------------------
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
