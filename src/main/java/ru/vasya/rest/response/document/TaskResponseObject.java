package ru.vasya.rest.response.document;

import ru.vasya.model.document.Task;
import ru.vasya.rest.response.ResponseObject;

public class TaskResponseObject extends ResponseObject {

    private Task model;

    public TaskResponseObject(Task model, String template, String tabTitle, String target, String prefix) {
        super(template, tabTitle, target, prefix);
        this.model = model;
    }

    public TaskResponseObject() {
    }

    public Task getModel() {
        return model;
    }

    public void setModel(Task model) {
        this.model = model;
    }
}
