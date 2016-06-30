package ru.vasya.service.query;


import java.util.*;

public class UpdateQuery implements Query {
    private Class clazz;
    private List criterias = new LinkedList();
    private Set values = new HashSet();

    public UpdateQuery(Class c){
        clazz = c;
    }

    public UpdateQuery addCriteria(Criteria c){
        criterias.add(c);
        return this;
    }

    public UpdateQuery addUpdateValue(String field, Object value){
        values.add(Criteria.equals(field, value));
        return this;
    }

    private String generateWhereClause(){
        StringBuilder result = new StringBuilder();
        for(Iterator it = criterias.iterator(); it.hasNext();){
            Criteria c = (Criteria) it.next();
            if (result.length()!=0){
                result.append(" AND ");
            } else {
                result.append(" WHERE ");
            }
            result.append(c.toSQL());
        }
        return result.toString();
    }

    private String generateUpdateClause(){
        StringBuilder result = new StringBuilder();
        for(Iterator it = values.iterator(); it.hasNext();){
            Criteria c = (Criteria) it.next();
            if (result.length()!=0){
                result.append(", ");
            } else {
                result.append(" SET ");
            }
            result.append(c.toSQL());
        }
        return result.toString();
    }

    public String toSQL(){
        return "UPDATE " + clazz.getSimpleName() + generateUpdateClause() + generateWhereClause();
    }
}
