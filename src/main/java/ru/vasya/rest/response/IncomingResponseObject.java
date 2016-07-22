package ru.vasya.rest.response;

import ru.vasya.model.document.Incoming;

public class IncomingResponseObject {

    private Incoming model;
    private String template;
    private String action;
    private String name;

    public IncomingResponseObject(Incoming model, String template, String action, String name) {
        this.model = model;
        this.template = template;
        this.action = action;
        this.name = name;
    }

    public IncomingResponseObject() {
    }

    public Incoming getModel() {
        return model;
    }

    public void setModel(Incoming model) {
        this.model = model;
    }

    public String getTemplate() {
        return template;
    }

    public void setTemplate(String template) {
        this.template = template;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
