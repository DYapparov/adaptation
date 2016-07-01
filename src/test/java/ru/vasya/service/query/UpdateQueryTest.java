package ru.vasya.service.query;

import org.junit.Test;
import ru.vasya.service.query.parts.FieldToSelect;
import ru.vasya.service.query.parts.FieldsPart;
import ru.vasya.service.query.parts.LogicalOperation;
import ru.vasya.service.query.parts.Table;

/**
 * Created by dyapparov on 30.06.2016.
 */
public class UpdateQueryTest {
    @Test
    public void testBuildQuery() throws Exception {
        UpdateQuery updateQuery = UpdateQuery.builder()
                .setTable(new Table("Users", "users"))
                .addUpdateField(new FieldsPart(new FieldToSelect("name", "name"), "Petya", LogicalOperation.EQUALS))
                .addUpdateField(new FieldsPart(new FieldToSelect("surname", "surname"), "Guslin", LogicalOperation.EQUALS))
                .addWherePart(new FieldsPart(new FieldToSelect("id", "id"), 2, LogicalOperation.EQUALS))
                .build();

        System.out.println(QueryToSqlConverter.convert(updateQuery));
    }
}