package ru.vasya.util;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by dyapparov on 22.07.2016.
 */
public class TemplateLoaderTest {
    @Test
    public void getTemplate() throws Exception {
        String template = TemplateLoader.getTemplate("PersonInfo");
        assertNotNull(template);
    }

}