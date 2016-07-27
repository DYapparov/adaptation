package ru.vasya.util;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;

public class TemplateLoader {
    private static final Logger LOGGER = LoggerFactory.getLogger(TemplateLoader.class);

    public TemplateLoader(){

    }

    public static String getTemplate(String name){
        InputStream in = TemplateLoader.class.getClassLoader().getResourceAsStream("HTML templates/" + name);
        String result = null;
        try {
             result = IOUtils.toString(in, "UTF-8");
        } catch (IOException e) {
            LOGGER.error("Failed to load template file: " + name, e);
        }
        return result;
    }
}
