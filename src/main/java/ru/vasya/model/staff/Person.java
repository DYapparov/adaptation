package ru.vasya.model.staff;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement
@XmlType
public class Person extends Staff implements Comparable {
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

    public int compareTo(Object o) {
        Person p = (Person) o;
        int result = lastName.compareTo(p.lastName);
        if (result==0){
            result = firstName.compareTo(p.firstName);
            if (result == 0){
                result = midleName.compareTo(p.midleName);
            }
        }
        return result;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + "(id " + getId() + "): " + lastName + " " + firstName + " " + midleName + ", " + position;
    }
}
