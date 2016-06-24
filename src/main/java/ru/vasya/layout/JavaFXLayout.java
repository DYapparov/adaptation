package ru.vasya.layout;


import javafx.application.Application;
import javafx.geometry.Orientation;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;
import ru.vasya.model.document.Document;
import ru.vasya.report.ReportManager;
import ru.vasya.service.DocService;
import ru.vasya.model.staff.Person;

import java.util.Map;
import java.util.TreeSet;

public class JavaFXLayout extends Application {

    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("JavaFX made me");
        FlowPane root = new FlowPane(Orientation.VERTICAL);
        Scene scene = new Scene(root,300, 500);
        primaryStage.setScene(scene);
        Label textArea = new Label(getParameters().getUnnamed().iterator().next());
        ScrollPane scrollPane = new ScrollPane(textArea);
        scrollPane.setPrefViewportHeight(scene.getHeight());
        scrollPane.setPrefViewportWidth(scene.getWidth());
        scrollPane.setPannable(true);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        root.getChildren().add(scrollPane);
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    public static void main(String[] args) {
        DocService ds = DocService.getInstance();

        Map<Person, TreeSet<Document>> docs = ds.getRandomDocs(100);

        final String report = ReportManager.getReport(docs);

        launch(report);
    }
}
