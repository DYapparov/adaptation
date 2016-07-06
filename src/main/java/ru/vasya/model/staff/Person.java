package ru.vasya.model.staff;

import ru.vasya.model.document.Storable;

import javax.persistence.Entity;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.util.Date;

@Entity
@XmlRootElement
@XmlType
public class Person extends Staff implements Comparable, Storable {
    private String lastName;
    private String firstName;
    private String middleName;
    private String post;
    private Date birthday;
    private String photoURL;

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

    public String getMiddleName() {
        return middleName;
    }

    @XmlElement
    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getPost() {
        return post;
    }
    @XmlElement
    public void setPost(String post) {
        this.post = post;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getPhotoURL() {
        return photoURL;
    }

    public void setPhotoURL(String photoURL) {
        this.photoURL = photoURL;
    }

    public int compareTo(Object o) {
        Person p = (Person) o;
        int result = lastName.compareTo(p.lastName);
        if (result==0){
            result = firstName.compareTo(p.firstName);
            if (result == 0){
                result = middleName.compareTo(p.middleName);
            }
        }
        return result;
    }

    public String getTable() {
        return this.getClass().getSimpleName();
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + "(id " + getId() + "): " + getLastName() + " " + getFirstName() + " " + getMiddleName() + ", " + getPost() + ", birthday: " + getBirthday();
    }
}
