package ru.vasya.util;

import ru.vasya.staff.JAXBStaffList;
import ru.vasya.staff.Staff;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.File;
import java.util.List;

public class XMLSerializator {
    private static XMLSerializator instance;

    private XMLSerializator(){}

    public static XMLSerializator getInstance(){
        if (instance==null){
            instance = new XMLSerializator();
        }
        return instance;
    }
    /*
    public void marshal(List list, File file) throws JAXBException{
        JAXBStaffList jaxbStaffList = new JAXBStaffList<Staff>(list);
        JAXBContext context = JAXBContext.newInstance(JAXBStaffList.class);
        Marshaller m = context.createMarshaller();
        m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        m.marshal(jaxbStaffList, file);
    }
    */

    public List unmarshal(File file) throws JAXBException{
        JAXBContext context = JAXBContext.newInstance(JAXBStaffList.class);
        Object intermediateResult = context.createUnmarshaller().unmarshal(file);
        List result = ((JAXBStaffList)intermediateResult).getItems();
        return result;
    }
}
