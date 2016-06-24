package ru.vasya.rest;

import ru.vasya.model.staff.Person;
import ru.vasya.service.PersonService;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Path("/ecm")
public class EmployeesController {

    @GET
    @Path("/employees")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Person> getEmployees(){
        return getRandomPersonList(10);
    }

    //------------------temporary DEL
    public static List<Person> getRandomPersonList(int count){
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
