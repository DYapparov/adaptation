package ru.vasya.rest.response.document;

import ru.vasya.model.document.Incoming;
import ru.vasya.rest.response.ResponseObject;

public class IncomingResponseObject extends ResponseObject {

    private Incoming model;

    public IncomingResponseObject(Incoming model, String template, String tabTitle, String target, String prefix) {
        super(template, tabTitle, target, prefix);
        this.model = model;
    }

    public IncomingResponseObject() {
    }

    public Incoming getModel() {
        return model;
    }

    public void setModel(Incoming model) {
        this.model = model;
    }
}
