package ru.vasya;

import ru.vasya.document.*;

import java.util.*;

public class Main {
    private static Class[] docClasses = {Task.class, Outgoing.class, Incoming.class};

    public static void main(String[] args) {
        Random rand = new Random();
        ArrayList<Document> docs = new ArrayList<Document>();
        DocumentFactory df = DocumentFactory.getDocumentFactory();
        for(int i = 0; i<100; i++){
            try {
                docs.add(df.getDocument(docClasses[rand.nextInt(3)]));
            } catch (DocumentExistsException e){
                System.out.println(e);
            }
        }

        Collections.sort(docs);
        System.out.println(getReport(docs));


    }

    public static String getReport(List<Document> list){
        String authorCash=null;
        StringBuilder sb = new StringBuilder();
        Iterator<Document> i = list.iterator();

        while(i.hasNext()){
            Document d =i.next();
            if (!d.getAuthor().equals(authorCash)){
                authorCash = d.getAuthor();
                sb.append("\n");
                sb.append(d.getAuthor());
                sb.append("\n---");
                sb.append(d.toStringForReport());
            } else
                sb.append("\n---" + d.toStringForReport());

        }
        return sb.toString();
    }
}
