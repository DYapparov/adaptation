package ru.vasya.service.query;


import java.util.*;

public class QueryIMPl {
    private List<Criteria> criterias = new LinkedList<Criteria>();

    public QueryIMPl addCriteria(Criteria c){
        criterias.add(c);
        return this;
    }

    public List<Criteria> getCriterias(){
        return criterias;
    }
}
