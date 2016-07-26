package ru.vasya.rest.response;

import ru.vasya.model.staff.Person;

import java.util.Set;

public class PersonsResponseObject {

    private Set<Person> model;
    private String template;
    private String action;
    private String name;

    public PersonsResponseObject(Set<Person> model, String template, String action, String name) {
        this.model = model;
        this.template = template;
        this.action = action;
        this.name = name;
    }

    public PersonsResponseObject() {
    }

    public Set<Person> getModel() {
        return model;
    }

    public void setModel(Set<Person> model) {
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
