package ru.vasya.rest.response.document;

import ru.vasya.model.document.Outgoing;
import ru.vasya.rest.response.ResponseObject;

public class OutgoingResponseObject extends ResponseObject {

    private Outgoing model;

    public OutgoingResponseObject(Outgoing model, String template, String tabTitle, String target, String prefix) {
        super(template, tabTitle, target, prefix);
        this.model = model;
    }

    public OutgoingResponseObject() {
    }

    public Outgoing getModel() {
        return model;
    }

    public void setModel(Outgoing model) {
        this.model = model;
    }
}
