package ru.vasya.service.db.query;

import org.junit.Test;
import ru.vasya.service.db.query.parts.FieldToSelect;
import ru.vasya.service.db.query.parts.FieldsPart;
import ru.vasya.service.db.query.parts.LogicalOperation;
import ru.vasya.service.db.query.parts.Table;

/**
 * Created by dyapparov on 30.06.2016.
 */
public class SelectQueryTest {
    @Test
    public void testBuildQuery() throws Exception {
        SelectQuery selectQuery = SelectQuery.builder()
                .setTable(new Table("Users", "users"))
                .addField(new FieldToSelect("name", "user"))
                .addField(new FieldToSelect("surname", "user"))
                .addWherePart(new FieldsPart(new FieldToSelect("name", "user"), "Vasya", LogicalOperation.EQUALS))
                .build();

        System.out.println(QueryToSqlConverter.convert(selectQuery));
    }
}