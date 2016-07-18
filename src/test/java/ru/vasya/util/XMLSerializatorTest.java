package ru.vasya.util;

import org.junit.Test;
import ru.vasya.db.DBProperties;

import static org.junit.Assert.*;

/**
 * Created by dyapparov on 18.07.2016.
 */
public class XMLSerializatorTest {
    @Test
    public void unmarshal() throws Exception {
        XMLSerializator s = XMLSerializator.getInstance();
        DBProperties result = (DBProperties) s.unmarshal(getClass().getClassLoader().getResourceAsStream("db_config.xml"), DBProperties.class);
        System.out.println(result.getUrl());
    }

}