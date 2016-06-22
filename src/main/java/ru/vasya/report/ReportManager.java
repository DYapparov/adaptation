package ru.vasya.report;


import ru.vasya.document.Document;

import java.util.*;

public class ReportManager {
    public static String getReport(Map<String, TreeSet<Document>> docs){
        StringBuilder sb = new StringBuilder();
        sb.append("\n");
        sb.append("--------------------------------------------------------------------------\n");
        sb.append("|                             The Report                                 |\n");
        sb.append("--------------------------------------------------------------------------\n");
        for(String author: docs.keySet()){
            sb.append(author).append(":\n");
            for(Document d: docs.get(author)){
                sb.append("  - ").append(d.toStringForReport()).append("\n");
            }
        }
        return sb.toString();
    }
}
