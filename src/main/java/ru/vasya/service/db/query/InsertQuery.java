package ru.vasya.service.db.query;

import ru.vasya.service.db.query.parts.FieldsPart;
import ru.vasya.service.db.query.parts.Table;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by dyapparov on 30.06.2016.
 */
public class InsertQuery extends Query {
    private Table into;
    private Collection<FieldsPart> values = new ArrayList<FieldsPart>();

    public Table getInto() {
        return into;
    }

    public void setInto(Table into) {
        this.into = into;
    }

    public Collection<FieldsPart> getValues() {
        return values;
    }

    public void setValues(Collection<FieldsPart> values) {
        this.values = values;
    }

    public static Builder builder(){
        return new InsertQuery().new Builder();
    }

    public class Builder {
        public Builder setTable(Table table){
            InsertQuery.this.setInto(table);
            return this;
        }
        public Builder addValue(FieldsPart value){
            InsertQuery.this.values.add(value);
            return this;
        }
        public InsertQuery build(){
            return InsertQuery.this;
        }
    }
}
