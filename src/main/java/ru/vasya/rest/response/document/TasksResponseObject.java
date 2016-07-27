package ru.vasya.rest.response.document;

import ru.vasya.model.document.Task;
import ru.vasya.rest.response.ResponseObject;
import ru.vasya.rest.response.gridx.GridxColumn;

import java.util.List;
import java.util.Set;

/**
 * Created by dyapparov on 27.07.2016.
 */
public class TasksResponseObject extends ResponseObject {
    private Set<Task> model;
    private List<GridxColumn> columns;
    private String jsScript;

    public TasksResponseObject(Set<Task> model, List<GridxColumn> columns, String template, String tabTitle, String target, String prefix, String jsScript) {
        super(template, tabTitle, target, prefix);
        this.model = model;
        this.jsScript = jsScript;
        this.columns = columns;
    }

    public TasksResponseObject() {
    }

    public Set<Task> getModel() {
        return model;
    }

    public void setModel(Set<Task> model) {
        this.model = model;
    }

    public String getJsScript() {
        return jsScript;
    }

    public void setJsScript(String jsScript) {
        this.jsScript = jsScript;
    }

    public List<GridxColumn> getColumns() {
        return columns;
    }

    public void setColumns(List<GridxColumn> columns) {
        this.columns = columns;
    }
}
