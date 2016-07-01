package ru.vasya.service.query;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import ru.vasya.model.staff.Person;
import ru.vasya.service.query.parts.FieldToSelect;
import ru.vasya.service.query.parts.FieldsPart;
import ru.vasya.service.query.parts.Table;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by dyapparov on 30.06.2016.
 */
public class QueryToSqlConverter {
    private static String convert(Table table) {
        return table.getAlias() + " as " + table.getName();
    }

    private static String convert(FieldToSelect field) {
        return field.getName();
    }

    private static String convert(Collection<FieldToSelect> fields) {
        Collection<String> fieldString = new ArrayList<String>();
        for (FieldToSelect field : fields) {
            fieldString.add(convert(field));
        }
        return StringUtils.join(fieldString, ", ");
    }

    private static String convert(FieldsPart fieldsPart){
        String whereValueString = convert(fieldsPart.getLeftPart());
        whereValueString += fieldsPart.getOperation().getOperation();
        whereValueString += convertValue(fieldsPart.getCompareTo());

        return whereValueString;
    }

    private static Object convertValue(Object value){
        Object result;
        if (value instanceof Person){
            result = ((Person)value).getId();
        } else if (value instanceof Number){
            result = value.toString();
        } else {
            result = "'" + value + "'";
        }
        return result;
    }

    public static String convert(Query query) throws Exception {
        StringBuilder stringBuilder = new StringBuilder();
        if (query instanceof SelectQuery) {
            stringBuilder.append("SELECT ");

            SelectQuery selectQuery = (SelectQuery) query;
            stringBuilder.append(convert(selectQuery.getFields()));
            stringBuilder.append(" FROM ");
            stringBuilder.append(convert(selectQuery.getFrom()));

            if (CollectionUtils.isNotEmpty(selectQuery.getFieldsParts())) {
                stringBuilder.append(" WHERE ");

                Collection<String> whereParts = new ArrayList<String>();
                for (FieldsPart fieldsPart : selectQuery.getFieldsParts()) {
                    whereParts.add(convert(fieldsPart));
                }

                stringBuilder.append(StringUtils.join(whereParts, " AND "));
            }
        } else if (query instanceof InsertQuery) {
            stringBuilder.append("INSERT INTO ");
            InsertQuery insertQuery = (InsertQuery) query;
            stringBuilder.append(insertQuery.getInto().getName());
            stringBuilder.append("(");

            Collection<String> fieldNames = new ArrayList<String>();
            Collection<Object> values = new ArrayList<Object>();

            for (FieldsPart value : insertQuery.getValues()) {
                fieldNames.add(value.getLeftPart().getName());
                values.add(convertValue(value.getCompareTo()));
            }
            stringBuilder.append(StringUtils.join(fieldNames, ", "));
            stringBuilder.append(") VALUES (");
            stringBuilder.append(StringUtils.join(values, ", "));
            stringBuilder.append(")");

        } else if (query instanceof UpdateQuery) {
            stringBuilder.append("UPDATE ");
            UpdateQuery updateQuery = (UpdateQuery) query;
            stringBuilder.append(updateQuery.getTable().getName());
            stringBuilder.append(" SET ");

            Collection<String> updateValues = new ArrayList<String>();
            for (FieldsPart updateValue : updateQuery.getUpdatefields()) {
                updateValues.add(convert(updateValue));
            }
            stringBuilder.append(StringUtils.join(updateValues, ", "));

            stringBuilder.append(" WHERE ");

            Collection<String> whereParts = new ArrayList<String>();
            for (FieldsPart fieldsPart : updateQuery.getWhereFields()) {
                whereParts.add(convert(fieldsPart));
            }
            stringBuilder.append(StringUtils.join(whereParts, ", "));

        } else if (query instanceof DeleteQuery) {
            stringBuilder.append("DELETE FROM ");
            DeleteQuery deleteQuery = (DeleteQuery) query;
            stringBuilder.append(deleteQuery.getTable().getName());
            stringBuilder.append(" WHERE ");

            Collection<String> whereParts = new ArrayList<String>();

            for (FieldsPart fieldsPart : deleteQuery.getWhereFields()) {
                whereParts.add(convert(fieldsPart));
            }

            stringBuilder.append(StringUtils.join(whereParts, ", "));
        } else {
            throw new Exception("Unknown operation type");
        }

        return stringBuilder.toString();
    }
}
