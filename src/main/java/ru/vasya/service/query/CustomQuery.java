package ru.vasya.service.query;

/**
 * Created by dyapparov on 15.07.2016.
 */
public class CustomQuery extends Query {
    String query;
    public CustomQuery(String query){
        this.query = query;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }
}
