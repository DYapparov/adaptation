package ru.vasya.model.staff;


import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;

@Entity
public abstract class Staff implements Serializable {
    @Id
    private int id;

    public int getId(){
        return this.id;
    }

    public void setId(int id){
        this.id = id;
    }
}
