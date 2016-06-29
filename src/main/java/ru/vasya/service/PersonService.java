package ru.vasya.service;

import ru.vasya.model.staff.Person;

import javax.ejb.Local;
import java.util.List;

@Local
public interface PersonService {
    List<Person> getPersonList();
}
