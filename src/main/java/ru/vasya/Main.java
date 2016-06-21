package ru.vasya;

import ru.vasya.document.*;
import ru.vasya.report.ReportManager;
import ru.vasya.service.DocService;

import java.util.*;

public class Main {

    public static void main(String[] args) {
        List<Document> docs;
        DocService ds = DocService.getInstance();

        docs = ds.getRandomDocs(100);

        String report = ReportManager.getReport(docs);

        System.out.println(report);
    }
}
