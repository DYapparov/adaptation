package ru.vasya;

import ru.vasya.document.*;
import ru.vasya.report.ReportManager;

import java.util.*;

public class Main {
    private static Class[] docClasses = {Task.class, Outgoing.class, Incoming.class};

    public static void main(String[] args) {
        Random rand = new Random();
        ArrayList<Document> docs = new ArrayList<Document>();
        DocumentFactory df = DocumentFactory.getDocumentFactory();

        //Populating "docs" list
        for(int i = 0; i<100; i++){
            try {
                docs.add(df.getDocument(docClasses[rand.nextInt(3)]));
            } catch (DocumentExistsException e){
                System.out.println(e);
            }
        }

        //Generating report
        String report = ReportManager.getReport(docs);
        System.out.println(report);
    }
}
