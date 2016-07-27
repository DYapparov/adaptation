package ru.vasya.rest.response.person;

import ru.vasya.model.staff.Person;
import ru.vasya.rest.response.ResponseObject;

public class PersonResponseObject extends ResponseObject {

    private Person model;

    public PersonResponseObject(Person model, String template, String tabTitle, String target, String prefix) {
        super(template, tabTitle, target, prefix);
        this.model = model;
    }

    public PersonResponseObject() {
    }

    public Person getModel() {
        return model;
    }

    public void setModel(Person model) {
        this.model = model;
    }
}
