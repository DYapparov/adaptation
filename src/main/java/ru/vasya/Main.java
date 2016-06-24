package ru.vasya;

import ru.vasya.model.document.*;
import ru.vasya.report.ReportManager;
import ru.vasya.service.DocService;
import ru.vasya.model.staff.Person;

import java.util.*;

public class Main {

    public static void main(String[] args) {
        DocService ds = DocService.getInstance();

        Map<Person, TreeSet<Document>> docs = ds.getRandomDocs(100);

        String report = ReportManager.getReport(docs); //Task 1 report
        System.out.println(report);

        ReportManager.saveDocsToJSON(docs);            //Task 2 report to json
    }
}
