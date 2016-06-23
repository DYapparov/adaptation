package ru.vasya;

import ru.vasya.document.*;
import ru.vasya.report.ReportManager;
import ru.vasya.service.DocService;
import ru.vasya.staff.Person;
import ru.vasya.service.PersonService;
import ru.vasya.util.JSONSerializator;

import java.io.File;
import java.util.*;

public class Main {

    public static List<Person> persons;

    public static void main(String[] args) {
        DocService ds = DocService.getInstance();
        JSONSerializator jsonSerializator = JSONSerializator.getInstance();
        persons = PersonService.getInstance().getPersonList();

        Map<Person, TreeSet<Document>> docs;
        docs = ds.getRandomDocs(100);

        String report = ReportManager.getReport(docs);

        System.out.println(report);
        for(Person p: persons){
            File f = new File(p.getLastName() + " " + p.getFirstName() + " " + p.getMidleName() + ".json");
            jsonSerializator.marshal(docs.get(p), f);
        }
    }
}
