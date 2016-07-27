package ru.vasya.rest.response.document;

import ru.vasya.model.document.Incoming;
import ru.vasya.rest.response.ResponseObject;
import ru.vasya.rest.response.gridx.GridxColumn;

import java.util.List;
import java.util.Set;

/**
 * Created by dyapparov on 27.07.2016.
 */
public class IncomingsResponseObject extends ResponseObject {
    private Set<Incoming> model;
    private List<GridxColumn> columns;
    private String jsScript;

    public IncomingsResponseObject(Set<Incoming> model, List<GridxColumn> columns, String template, String tabTitle, String target, String prefix, String jsScript) {
        super(template, tabTitle, target, prefix);
        this.model = model;
        this.jsScript = jsScript;
        this.columns = columns;
    }

    public IncomingsResponseObject() {
    }

    public Set<Incoming> getModel() {
        return model;
    }

    public void setModel(Set<Incoming> model) {
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