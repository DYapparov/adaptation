package ru.vasya.util;

import java.io.*;
import java.lang.reflect.Type;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.vasya.model.document.Task;
import ru.vasya.model.document.TaskSerializer;
import ru.vasya.model.staff.Department;
import ru.vasya.model.staff.Organization;
import ru.vasya.model.staff.Person;
import ru.vasya.model.staff.Staff;

public class JSONSerializator {
    private static final Logger LOGGER = LoggerFactory.getLogger(JSONSerializator.class);
    private static JSONSerializator instance;
    private Gson gson;

    private JSONSerializator(){
        gson = new GsonBuilder().setPrettyPrinting().registerTypeAdapter(Task.class, new TaskSerializer()).create();
    }

    public static JSONSerializator getInstance(){
        if (instance==null){
            instance = new JSONSerializator();
        }
        return instance;
    }

    public void marshal(Object o, File f){
        FileWriter writer = null;
        try {
            writer = new FileWriter(f);
            writer.write(gson.toJson(o));
            writer.flush();
        }catch (IOException e){
            LOGGER.error("Could not write file: " + f.getAbsolutePath(), e);
        } finally {
            if (writer!=null) {
                try {
                    writer.close();
                } catch (IOException e){
                    LOGGER.error("Could not close file: " + f.getAbsolutePath(), e);
                }
            }
        }
    }

    public List<Staff> unmarshal(Class c, File f){
        BufferedReader reader = null ;
        StringBuilder inputStringBuilder = new StringBuilder();
        try {
            reader = new BufferedReader(new FileReader(f));
            String in = null;
            while ((in=reader.readLine())!=null){
                inputStringBuilder.append(in);
            }
        } catch (FileNotFoundException e){
            LOGGER.error("File not found: " + f.getAbsolutePath(), e);
        } catch (IOException e){
            LOGGER.error("Could not read file: " + f.getAbsolutePath(), e);
        } finally {
            if (reader!=null) {
                try {
                    reader.close();
                } catch (IOException e){
                    LOGGER.error("Could not close file: " + f.getAbsolutePath(), e);
                }
            }
        }
        Type collectionType = null;
        if(Person.class.equals(c)) {
            collectionType = new TypeToken<List<Person>>(){}.getType();
        } else if(Department.class.equals(c)) {
            collectionType = new TypeToken<List<Department>>(){}.getType();
        } else if (Organization.class.equals(c)) {
            collectionType = new TypeToken<List<Organization>>(){}.getType();
        }
        List<Staff> result = gson.fromJson(inputStringBuilder.toString(),collectionType);
        return result;
    }

}
