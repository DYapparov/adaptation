package ru.vasya.db;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DerbyConnection {
    private static final Logger LOGGER = LoggerFactory.getLogger(DerbyConnection.class);

    static DBProperties properties;

    public static Connection getConnection(){
        if(properties==null){
            properties = DBProperties.loadProperties("db_config.xml");
        }
        return getConnection(properties.getUrl(), properties.getCreate(), properties.getUsername(), properties.getPassword(), properties.getDriver());

    }
    public static Connection getConnection(String propsPath){
        properties = DBProperties.loadProperties("db_config.xml");
        return getConnection(properties.getUrl(), properties.getCreate(), properties.getUsername(), properties.getPassword(), properties.getDriver());

    }

    public static Connection getConnection(String url, String create, String username, String password, String driver){
        Connection conn = null;
        try {
            Class.forName(driver);
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
