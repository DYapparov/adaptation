package ru.vasya.service.db.query;

import ru.vasya.service.db.query.parts.FieldToSelect;
import ru.vasya.service.db.query.parts.FieldsPart;
import ru.vasya.service.db.query.parts.Table;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by dyapparov on 30.06.2016.
 */
public class SelectQuery extends Query {
    private Collection<FieldToSelect> fields = new ArrayList<FieldToSelect>();
    private Table from;
    private Collection<FieldsPart> fieldsParts = new ArrayList<FieldsPart>();
    private Class type;

    public Collection<FieldToSelect> getFields() {
        return fields;
    }

    public void setFields(Collection<FieldToSelect> fields) {
        this.fields = fields;
    }

    public Table getFrom() {
        return from;
    }

    public void setFrom(Table from) {
        this.from = from;
    }

    public Collection<FieldsPart> getFieldsParts() {
        return fieldsParts;
    }

    public void setFieldsParts(Collection<FieldsPart> fieldsParts) {
        this.fieldsParts = fieldsParts;
    }

    public Class getType() {
        return type;
    }

    public void setType(Class type) {
        this.type = type;
    }

    public static Builder builder() {
        return new SelectQuery().new Builder();
    }

    public class Builder {
        public Builder setTable(Table table) {
            SelectQuery.this.setFrom(table);
            return this;
        }

        public Builder addField(FieldToSelect field) {
            SelectQuery.this.fields.add(field);
            return this;
        }

        public Builder addWherePart(FieldsPart fieldsPart) {
            SelectQuery.this.fieldsParts.add(fieldsPart);
            return this;
        }

        public Builder setType(Class c) {
            SelectQuery.this.setType(c);
            return this;
        }

        public SelectQuery build() {
            return SelectQuery.this;
        }
    }
}
