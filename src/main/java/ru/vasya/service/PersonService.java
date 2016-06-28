package ru.vasya.service;


import ru.vasya.model.staff.Department;
import ru.vasya.model.staff.Organization;
import ru.vasya.model.staff.Person;
import ru.vasya.util.XMLSerializator;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

//----------------------Delete...or not
@ApplicationScoped
public class PersonService {
    private static PersonService instance;

    XMLSerializator xmlSerializator;

    private List<Person> persons;

    public PersonService(){
        xmlSerializator = XMLSerializator.getInstance();
    }

    public static PersonService getInstance(){
        if (instance==null){
            instance = new PersonService();
        }
        return instance;
    }

    public List<Person> getPersonList(){
        if (persons == null) {
            //File personsFile = new File(Person.class.getSimpleName() + ".xml");
            InputStream inputPersonFile = this.getClass().getResourceAsStream("/" + Person.class.getSimpleName() + ".xml");
            persons = xmlSerializator.unmarshal(inputPersonFile);
            try {
                inputPersonFile.close();
            } catch (IOException e){
                e.printStackTrace();
            }
        }
        return persons;
    }

    //Used for generating xml files.
    public List<Person> getRandomPersonList(int count){
        List<Person> result = new ArrayList<Person>();
        for(int i = 0; i <count; i++) {
            Person p = new Person();
            p.setId(i);
            p.setFirstName(randomWord(6));
            p.setLastName(randomWord(8));
            p.setMidleName(randomWord(10));
            p.setPosition("Slave");
            result.add(p);
        }
        return result;
    }
    public List<Organization> getRandomOrganizationList(int count){
        List<Organization> result = new ArrayList<Organization>();
        for(int i = 0; i <count; i++) {
            Organization o = new Organization();
            o.setId(i);
            o.setFullName(randomWord(6));
            o.setHead(getPersonList().get(new Random().nextInt(getPersonList().size())));
            o.setContacts(new Random().nextInt(90) + "");
            result.add(o);
        }
        return result;
    }
    public List<Department> getRandomDepartmentList(int count){
        List<Department> result = new ArrayList<Department>();
        for(int i = 0; i <count; i++) {
            Department d = new Department();
            d.setId(i);
            d.setFullName(randomWord(6));
            d.setHead(getPersonList().get(new Random().nextInt(getPersonList().size())));
            d.setContacts(new Random().nextInt(90) + "");
            result.add(d);
        }
        return result;
    }

    private static String randomWord(int length){
        String vowels = "aeyuio";
        String consonants = "qwrtpsdfghjklzxcvbnm";
        Random rand = new Random();
        String result = "" + (consonants+vowels).charAt(rand.nextInt(vowels.length()+consonants.length()));
        result = result.toUpperCase();
        for (int i = 0; i < length; i++){
            result += (vowels.indexOf(result.charAt(result.length()-1))!=-1)?consonants.charAt(rand.nextInt(consonants.length())):vowels.charAt(rand.nextInt(vowels.length()));
        }
        return result;
    }
}
