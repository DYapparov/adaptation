package ru.vasya.service.db.query.parts;

/**
 * Created by dyapparov on 30.06.2016.
 */
public class Table {
    private String name;
    private String alias;

    public Table() {
    }

    public Table(String name, String alias) {
        this.name = name;
        this.alias = alias;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }
}
