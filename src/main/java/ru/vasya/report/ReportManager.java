package ru.vasya.report;


import ru.vasya.model.document.Document;
import ru.vasya.model.staff.Person;
import ru.vasya.util.JSONSerializator;

import java.io.File;
import java.util.*;

public class ReportManager {

    public static String getReport(Map<Person, TreeSet<Document>> docs){
        StringBuilder sb = new StringBuilder();
        sb.append("\n");
        sb.append("--------------------------------------------------------------------------\n");
        sb.append("|                             The Report                                 |\n");
        sb.append("--------------------------------------------------------------------------\n");
        for(Person author: docs.keySet()){
            sb.append(author).append(":\n");
            for(Document d: docs.get(author)){
                sb.append("  - ").append(d.toStringForReport()).append("\n");
            }
        }
        return sb.toString();
    }

    public static void saveDocsToJSON(Map<Person, TreeSet<Document>> docs){
        JSONSerializator jsonSerializator = JSONSerializator.getInstance();
        for(Person p: docs.keySet()){
            File f = new File(p.getLastName() + " " + p.getFirstName() + " " + p.getMidleName() + ".json");
            jsonSerializator.marshal(docs.get(p), f);
        }
    }
}
