package ru.vasya.rest.response;

/**
 * Created by dyapparov on 25.07.2016.
 */
public enum Action {

    VIEW("View"),
    EDIT("Edit"),
    SOMETHING_ELSE("Some action");

    private String data;

    Action(String data){
        this.data = data;
    }

    public String getData(){
        return data;
    }
}
