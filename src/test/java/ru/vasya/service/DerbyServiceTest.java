package ru.vasya.service;

import org.junit.Test;
import ru.vasya.db.DBProperties;
import ru.vasya.model.staff.Person;
import ru.vasya.service.db.DerbyService;
import ru.vasya.service.db.query.SelectQuery;
import ru.vasya.service.db.query.parts.FieldToSelect;
import ru.vasya.service.db.query.parts.Table;
import ru.vasya.util.XMLSerializator;

import javax.sql.RowSet;
import java.util.List;

/**
 * Created by dyapparov on 01.07.2016.
 */
public class DerbyServiceTest {
    private DerbyService dBs = new DerbyService();

    private PersonServiceImpl ps = PersonServiceImpl.getInstance();

    private List<Person> persons = ps.getPersonList();

    @Test
    public void getRowSetMap(){
        System.out.println();
        System.out.println("------------- Selecting into Map -------------");
        SelectQuery.Builder builder = SelectQuery.builder();
        builder.setTable(new Table("Person", "PERSON"));
        builder.setType(RowSet.class);
        builder.addField(new FieldToSelect("lastName", "LASTNAME"))
                .addField(new FieldToSelect("firstName", "FIRSTNAME"))
                .addField(new FieldToSelect("middleName", "MIDDLENAME"));
        Iterable i = dBs.executeQuery(builder.build());
        for (Object o : i){
            System.out.println(o);
        }
    }

    @Test
    public void getObjectSet(){
        System.out.println();
        System.out.println("------------- Selecting into Object -------------");
        SelectQuery.Builder builder = SelectQuery.builder();
        builder.setTable(new Table("Person", "PERSON"));
        builder.setType(Person.class);
        builder.addField(new FieldToSelect("id", "ID"))
                .addField(new FieldToSelect("lastName", "LASTNAME"))
                .addField(new FieldToSelect("firstName", "FIRSTNAME"))
                .addField(new FieldToSelect("middleName", "MIDDLENAME"))
                .addField(new FieldToSelect("post", "POST"))
                .addField(new FieldToSelect("birthday", "BIRTHDAY"))
                .addField(new FieldToSelect("photoURL", "photoURL"));

        Iterable i = dBs.executeQuery(builder.build());
        for (Object o : i){
            System.out.println(o);
        }
    }

}