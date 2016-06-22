package ru.vasya.staff;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement
@XmlType
public class Person extends Staff {
    private String lastName;
    private String firstName;
    private String midleName;
    private String position;

    public Person(){

    }

    public String getLastName() {
        return lastName;
    }

    @XmlElement
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    @XmlElement
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMidleName() {
        return midleName;
    }
    @XmlElement
    public void setMidleName(String midleName) {
        this.midleName = midleName;
    }

    public String getPosition() {
        return position;
    }
    @XmlElement
    public void setPosition(String position) {
        this.position = position;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + "(id " + getId() + "): " + lastName + " " + firstName + " " + midleName + ", " + position;
    }
}
