package ru.vasya.service.query;


import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class SelectQuery implements Query {
    private Class clazz;
    private List criterias = new LinkedList();

    public SelectQuery(Class c){
        clazz = c;
    }

    public SelectQuery addCriteria(Criteria c){
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
        return "SELECT * FROM " + clazz.getSimpleName() + generateWhereClause();
    }
}


/*
abstract class QueryType{
}

class Select extends QueryType{

    private String[] fields;

    public Select(){
        fields = new String[]{"*"};
    }

    public Select(String... fields){
        this.fields = fields;
    }

    @Override
    public String toString(){
        return this.getClass().getSimpleName().toUpperCase() + " " + fields + " FROM ";
    }
}

class Delete extends QueryType{

    @Override
    public String toString(){
        return this.getClass().getSimpleName().toUpperCase() + " FROM ";
    }
}

class Update extends QueryType{

    private String[] fields;

    public Update(){
        fields = new String[]{"*"};
    }

    public Update(String... fields){
        this.fields = fields;
    }

    @Override
    public String toString(){
        return this.getClass().getSimpleName().toUpperCase() + " + " FROM ";
    }
}

class Insert extends QueryType{

    private String[] fields;

    public Select(){
        fields = new String[]{"*"};
    }

    public Select(String... fields){
        this.fields = fields;
    }

    @Override
    public String toString(){
        return this.getClass().getSimpleName().toUpperCase() + " " + fields + " FROM ";
    }
}
*/