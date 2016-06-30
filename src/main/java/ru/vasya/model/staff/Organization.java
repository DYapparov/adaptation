package ru.vasya.model.staff;

import ru.vasya.model.document.Storable;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;

@XmlRootElement
@XmlType
public class Organization extends Staff implements Comparable, Storable {
    private String fullName;
    private String shortName;
    private Person head;
    private String contacts;

    public Organization(){

    }

    public String getFullName() {
        return fullName;
    }

    @XmlElement
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getShortName() {
        return shortName;
    }

    @XmlElement
    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public Person getHead() {
        return head;
    }

    @XmlElement
    public void setHead(Person head) {
        this.head = head;
    }

    public String getContacts() {
        return contacts;
    }

    @XmlElement
    public void setContacts(String contacts) {
        this.contacts = contacts;
    }

    public int compareTo(Object o) {
        return fullName.compareTo(((Organization)o).fullName);
    }

    public String getTable() {
        return this.getClass().getSimpleName();
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + "(id " + getId() + "): " + fullName + ". Head: " + head + ". Contacts: " + contacts;
    }
}
