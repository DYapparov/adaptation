package ru.vasya.service.query;

import ru.vasya.service.query.parts.FieldsPart;
import ru.vasya.service.query.parts.Table;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by dyapparov on 30.06.2016.
 */
public class UpdateQuery extends  Query {
    private Collection<FieldsPart> updatefields = new ArrayList<FieldsPart>();
    private Table table;
    private Collection<FieldsPart> whereFields = new ArrayList<FieldsPart>();

    public Collection<FieldsPart> getUpdatefields() {
        return updatefields;
    }

    public void setUpdatefields(Collection<FieldsPart> updatefields) {
        this.updatefields = updatefields;
    }

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
        return new UpdateQuery().new Builder();
    }

    public class Builder{
        public Builder setTable(Table table){
            UpdateQuery.this.setTable(table);
            return this;
        }

        public Builder addUpdateField(FieldsPart field){
            UpdateQuery.this.updatefields.add(field);
            return this;
        }

        public Builder addWherePart(FieldsPart where){
            UpdateQuery.this.whereFields.add(where);
            return this;
        }

        public UpdateQuery build(){
            return UpdateQuery.this;
        }
    }

}
