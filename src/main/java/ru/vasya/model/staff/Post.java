package ru.vasya.model.staff;

import ru.vasya.model.document.Storable;

/**
 * Created by dyapparov on 07.07.2016.
 */
public class Post extends Staff implements Storable, Comparable {

    private String name;

    public Post(){

    }

    public Post(int id, String name){
        setId(id);
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + ": " + getId() + ". " + getName();
    }

    @Override
    public int compareTo(Object o) {
        return getName().compareTo(((Post)o).getName());
    }

    @Override
    public String getTable() {
        return this.getClass().getSimpleName();
    }
}
