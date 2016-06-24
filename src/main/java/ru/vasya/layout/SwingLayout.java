package ru.vasya.layout;


import ru.vasya.model.document.Document;
import ru.vasya.service.DocService;
import ru.vasya.model.staff.Person;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import java.awt.*;
import java.util.Map;
import java.util.TreeSet;

public class SwingLayout {

    public SwingLayout(Map<Person, TreeSet<Document>> content){
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        JFrame reportWindow = new JFrame();
        reportWindow.setTitle("Swing made me");
        reportWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        DefaultMutableTreeNode root = new DefaultMutableTreeNode("Report:");
        for(Person p: content.keySet()) {
            DefaultMutableTreeNode personNode = new DefaultMutableTreeNode(p.toString());
            for (Document d: content.get(p)){
                DefaultMutableTreeNode documentNode = new DefaultMutableTreeNode(d.toString());
                personNode.add(documentNode);
            }
            root.add(personNode);
        }
        JTree tree = new JTree(root);
        JScrollPane scrollPane = new JScrollPane(tree);

        reportWindow.add(scrollPane);
        reportWindow.pack();
        reportWindow.setSize(reportWindow.getWidth()+20, reportWindow.getHeight());
        reportWindow.setLocation(screenSize.width/2-reportWindow.getWidth()/2, screenSize.height/2-reportWindow.getHeight()/2);
        reportWindow.setVisible(true);
    }

    public static void main(String[] args) {
        DocService ds = DocService.getInstance();

        final Map<Person, TreeSet<Document>> docs = ds.getRandomDocs(100);

        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new SwingLayout(docs);
            }
        });
    }
}
