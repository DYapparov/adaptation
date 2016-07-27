package ru.vasya.rest.response.person;

import ru.vasya.model.staff.Person;
import ru.vasya.rest.response.ResponseObject;
import ru.vasya.rest.response.gridx.GridxColumn;

import java.util.List;
import java.util.Set;

public class PersonsResponseObject extends ResponseObject {

    private Set<Person> model;
    private List<GridxColumn> columns;
    private String jsScript;

    public PersonsResponseObject(Set<Person> model, List<GridxColumn> columns, String template, String tabTitle, String target, String prefix, String jsScript) {
        super(template, tabTitle, target, prefix);
        this.model = model;
        this.jsScript = jsScript;
        this.columns = columns;
    }

    public PersonsResponseObject() {
    }

    public Set<Person> getModel() {
        return model;
    }

    public void setModel(Set<Person> model) {
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
