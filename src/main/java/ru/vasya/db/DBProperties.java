package ru.vasya.db;

import ru.vasya.util.XMLSerializator;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * Created by dyapparov on 18.07.2016.
 */

@XmlRootElement
@XmlType
public class DBProperties {

    private String url;
    private String username;
    private String password;
    private String create;
    private String driver;


    public DBProperties() {

    }

    public static DBProperties loadProperties(String fileName){
        XMLSerializator xmls = XMLSerializator.getInstance();
        DBProperties properties = (DBProperties) xmls.unmarshal(DBProperties.class.getClassLoader().getResourceAsStream(fileName), DBProperties.class);
        return properties;
    }

    public String getUrl() {
        return url;
    }

    @XmlElement
    public void setUrl(String url) {
        this.url = url;
    }

    public String getUsername() {
        return username;
    }

    @XmlElement
    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    @XmlElement
    public void setPassword(String password) {
        this.password = password;
    }

    public String getCreate() {
        return create;
    }

    @XmlElement
    public void setCreate(String create) {
        this.create = create;
    }

    public String getDriver() {
        return driver;
    }

    @XmlElement
    public void setDriver(String driver) {
        this.driver = driver;
    }
}
