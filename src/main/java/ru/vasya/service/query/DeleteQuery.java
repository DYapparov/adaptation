package ru.vasya.service.query;


import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class DeleteQuery implements Query {
    private Class clazz;
    private List criterias = new LinkedList();

    public DeleteQuery(Class c){
        clazz = c;
    }

    public DeleteQuery addCriteria(Criteria c){
        criterias.add(c);
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

    public String toSQL(){
        return "DELETE FROM " + clazz.getSimpleName() + generateWhereClause();
    }
}
