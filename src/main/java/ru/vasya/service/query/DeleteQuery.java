package ru.vasya.service.query;

import ru.vasya.service.query.parts.FieldsPart;
import ru.vasya.service.query.parts.Table;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by dyapparov on 30.06.2016.
 */
public class DeleteQuery extends Query {
    private Table table;
    private Collection<FieldsPart> whereFields = new ArrayList<FieldsPart>();

    public Table getTable() {
        return table;
    }

    public void setTable(Table table) {
        this.table = table;
    }

    public Collection<FieldsPart> getWhereFields() {
        return whereFields;
    }

    public void setWhereFields(Collection<FieldsPart> whereFields) {
        this.whereFields = whereFields;
    }

    public static Builder builder(){
        return new DeleteQuery().new Builder();
    }

    public class Builder{
        public Builder setTable(Table table){
            DeleteQuery.this.setTable(table);
            return this;
        }

        public Builder addWherePart(FieldsPart where){
            DeleteQuery.this.whereFields.add(where);
            return this;
        }

        public DeleteQuery build(){
            return DeleteQuery.this;
        }
    }

}
