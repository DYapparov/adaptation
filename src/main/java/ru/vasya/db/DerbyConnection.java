package ru.vasya.db;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DerbyConnection {
    private static final Logger LOGGER = LoggerFactory.getLogger(DerbyConnection.class);

    public static Connection getConnection(){
        return getConnection("jdbc:derby:newdb", "true", "admin", "admin");
    }

    public static Connection getConnection(String url, String create, String username, String password){
        Connection conn = null;
        try {
            Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
            Properties prop = new Properties();
            prop.put("create", create);
            conn = DriverManager.getConnection(url, prop);
        } catch (SQLException e){
            LOGGER.error("Could not establish connection to derby database", e);
        }catch (ClassNotFoundException e){
            LOGGER.error("Could not instantiate database Driver", e);
        }
        return conn;
    }
}
