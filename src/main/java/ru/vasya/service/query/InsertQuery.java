package ru.vasya.service.query;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;


public class InsertQuery implements Query {
    private Class clazz;
    private Set valuesSet = new HashSet();

    public InsertQuery(Class c){
        clazz = c;
    }

    public InsertQuery addUpdateValue(String field, Object value){
        valuesSet.add(Criteria.equals(field, value));
        return this;
    }

    private String generateUpdateClause(){
        StringBuilder fields = new StringBuilder();
        StringBuilder values = new StringBuilder();
        for(Iterator it = valuesSet.iterator(); it.hasNext();){
            Criteria c = (Criteria) it.next();
            if (fields.length()!=0){
                fields.append(", ");
                values.append(", ");
            } else {
                fields.append(" (");
                values.append(" VALUES (");
            }
            fields.append(c.getField());
            values.append(c.getValue());
        }
        fields.append(") ");
        values.append(") ");
        return fields.toString() + values.toString();
    }

    public String toSQL(){
        return "INSERT INTO " + clazz.getSimpleName() + generateUpdateClause();
    }
}