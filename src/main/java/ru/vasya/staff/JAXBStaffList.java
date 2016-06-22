package ru.vasya.staff;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement(name = "stafflist")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlSeeAlso({Person.class, Organization.class, Department.class})
public class JAXBStaffList<T> {
    @XmlElement(name = "item")
    private List<T> items;

    public JAXBStaffList(){
        items = new ArrayList<T>();
    }

    public JAXBStaffList(List<T> items) {
        this.items = items;
    }

    public List<T> getItems() {
        return items;
    }

    public void setItems(List<T> items) {
        this.items = items;
    }
}
