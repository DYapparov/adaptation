package ru.vasya.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.vasya.model.staff.Staff;

import javax.enterprise.context.ApplicationScoped;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.File;
import java.io.InputStream;
import java.util.List;


public class XMLSerializator {
    private static final Logger LOGGER = LoggerFactory.getLogger(XMLSerializator.class);
    private static XMLSerializator instance;

    private XMLSerializator(){}

    public static XMLSerializator getInstance(){
        if (instance==null){
            instance = new XMLSerializator();
        }
        return instance;
    }

    public void marshal(List list, File file) {
        JAXBStaffList jaxbStaffList = new JAXBStaffList<Staff>(list);
        try {
            JAXBContext context = JAXBContext.newInstance(JAXBStaffList.class);
            Marshaller m = context.createMarshaller();
            m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            m.marshal(jaxbStaffList, file);
        } catch (JAXBException e){
            LOGGER.error("Could not marshal into file: " + file.getAbsolutePath(), e);
        }
    }

    public List unmarshal(File file) {
        List result = null;
        try {
            JAXBContext context = JAXBContext.newInstance(JAXBStaffList.class);
            Object intermediateResult = context.createUnmarshaller().unmarshal(file);
            result = ((JAXBStaffList)intermediateResult).getItems();
        } catch (JAXBException e){
            LOGGER.error("Could not unmarshal file: " + file.getAbsolutePath(), e);
        }
        return result;
    }

    public List unmarshal(InputStream inputStream) {
        List result = null;
        try {
            JAXBContext context = JAXBContext.newInstance(JAXBStaffList.class);
            Object intermediateResult = context.createUnmarshaller().unmarshal(inputStream);
            result = ((JAXBStaffList)intermediateResult).getItems();
        } catch (JAXBException e){
            LOGGER.error("Could not unmarshal file: " + inputStream, e);
        }
        return result;
    }
}
