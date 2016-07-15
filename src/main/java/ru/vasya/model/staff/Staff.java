package ru.vasya.model.staff;


import ru.vasya.model.document.Storable;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;

@Entity
public abstract class Staff implements Serializable, Storable {
    @Id
    private int id;

    public int getId(){
        return this.id;
    }

    public void setId(int id){
        this.id = id;
    }

    @Override
    public String getTable() {
        return getClass().getSimpleName();
    }
}
