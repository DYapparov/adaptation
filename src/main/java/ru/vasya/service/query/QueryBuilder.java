package ru.vasya.service.query;


import ru.vasya.model.staff.Staff;

import java.lang.reflect.Field;

public class QueryBuilder {
    private QueryIMPl queryObject;
    private String prefix;

    private QueryBuilder(QueryIMPl q){
        this.queryObject = q;
    }

    public static QueryBuilder insertQuery(Object o) {
        QueryIMPl q = new QueryIMPl();
        q.addCriteria(Criteria.insertValue("id", ((Staff)o).getId()));
        try {
            for (Field f : o.getClass().getDeclaredFields()){
                f.setAccessible(true);
                q.addCriteria(Criteria.insertValue(f.getName(), f.get(o)));
            }
        } catch (IllegalAccessException e){
            e.printStackTrace();
        }
        QueryBuilder builder = new QueryBuilder(q);
        builder.prefix = "INSERT INTO " + o.getClass().getSimpleName();
        return builder;
    }

    public static QueryBuilder selectQuery(Class c) {
        QueryIMPl q = new QueryIMPl();
        QueryBuilder builder = new QueryBuilder(q);
        builder.prefix = "SELECT * FROM " + c.getSimpleName();
        return builder;
    }

    public static QueryBuilder updateQuery(Object o) {
        QueryIMPl q = new QueryIMPl();
        try {
            for (Field f : o.getClass().getDeclaredFields()){
                f.setAccessible(true);
                q.addCriteria(Criteria.updateValue(f.getName(), f.get(o)));
            }
        } catch (IllegalAccessException e){
            e.printStackTrace();
        }
        QueryBuilder builder = new QueryBuilder(q);
        builder.prefix = "UPDATE " + o.getClass().getSimpleName();
        return builder;
    }

    public static QueryBuilder deleteQuery(Object o) {
        QueryIMPl q = new QueryIMPl();
        QueryBuilder builder = new QueryBuilder(q);
        builder.prefix = "DELETE FROM " + o.getClass().getSimpleName();
        return builder;
    }

    public void addCriteria(Criteria c){
        queryObject.addCriteria(c);
    }

    public String toSQL(){
        StringBuilder where = new StringBuilder();
        StringBuilder update = new StringBuilder();
        StringBuilder insert = new StringBuilder();

        for(Criteria c : queryObject.getCriterias()){
            if (c instanceof WhereCriteria){
                if(where.length()==0){
                    where.append(" WHERE ");
                } else{
                    where.append(", ");
                }
                where.append(c.toSQL());
            } else if(c instanceof UpdateCriteria){
                if(update.length()==0){
                    update.append(" SET ");
                } else{
                    update.append(", ");
                }
                update.append(c.toSQL());
            } else if(c instanceof InsertCriteria){
                if(insert.length()==0){
                    where.append(" (");
                    insert.append(" VALUES (");
                } else{
                    where.append(", ");
                    insert.append(", ");
                }
                where.append(c.getField());
                insert.append(c.getValue());
            }

        }
        if (insert.length()!=0){
            where.append(") ");
            insert.append(") ");
        }

        return prefix + update.toString() + where.toString() + insert.toString();
    }
}
