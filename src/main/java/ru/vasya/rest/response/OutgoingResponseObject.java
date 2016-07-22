package ru.vasya.rest.response;

import ru.vasya.model.document.Outgoing;

public class OutgoingResponseObject {

    private Outgoing model;
    private String template;
    private String action;
    private String name;

    public OutgoingResponseObject(Outgoing model, String template, String action, String name) {
        this.model = model;
        this.template = template;
        this.action = action;
        this.name = name;
    }

    public OutgoingResponseObject() {
    }

    public Outgoing getModel() {
        return model;
    }

    public void setModel(Outgoing model) {
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
