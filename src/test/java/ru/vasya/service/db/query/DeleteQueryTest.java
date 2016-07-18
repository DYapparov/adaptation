package ru.vasya.service.db.query;

import org.junit.Test;
import ru.vasya.service.db.query.parts.FieldToSelect;
import ru.vasya.service.db.query.parts.FieldsPart;
import ru.vasya.service.db.query.parts.LogicalOperation;
import ru.vasya.service.db.query.parts.Table;

/**
 * Created by dyapparov on 30.06.2016.
 */
public class DeleteQueryTest {
    @Test
    public void testBuildQuery() throws Exception {
        DeleteQuery deleteQuery = DeleteQuery.builder()
                .setTable(new Table("Users", "users"))
                .addWherePart(new FieldsPart(new FieldToSelect("id", "id"), 2, LogicalOperation.EQUALS))
                .build();

        System.out.println(QueryToSqlConverter.convert(deleteQuery));
    }
}