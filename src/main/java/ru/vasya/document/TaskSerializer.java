package ru.vasya.document;



import com.google.gson.*;

import java.lang.reflect.Type;

public class TaskSerializer implements JsonSerializer<Task> {
    public JsonElement serialize(Task task, Type type, JsonSerializationContext jsonSerializationContext) {
        Gson g = new Gson();
        JsonObject result = (JsonObject) g.toJsonTree(task);
        if(result.get("controlTag").toString().equals("true")){
            result.addProperty("controlTag", "Controlled!");
        } else{
            result.addProperty("controlTag", "Not controlled!");
        }
        return result;
    }
}
