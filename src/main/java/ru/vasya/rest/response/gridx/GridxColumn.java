package ru.vasya.rest.response.gridx;

/**
 * Created by dyapparov on 27.07.2016.
 */
public class GridxColumn {

    private String field;
    private String name;
    private String width;
    private String formatter;

    public GridxColumn() {
    }

    public GridxColumn(String field, String name, String width, String formatter) {
        this.field = field;
        this.name = name;
        this.width = width;
        this.formatter = formatter;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getWidth() {
        return width;
    }

    public void setWidth(String width) {
        this.width = width;
    }

    public String getFormatter() {
        return formatter;
    }

    public void setFormatter(String formatter) {
        this.formatter = formatter;
    }
}
