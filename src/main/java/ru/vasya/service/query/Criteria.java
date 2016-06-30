package ru.vasya.service.query;


import ru.vasya.model.staff.Person;

public class Criteria {
    protected String sqlOperator;
    protected String field;
    protected Object value;

    private Criteria(){}

    protected Criteria(String sqlOperator, String field, Object value){
        this.sqlOperator = sqlOperator;
        this.field = field;
        this.value = value;
    }

    public static Criteria equals(String field, Object value){
        return new WhereCriteria("=", field, resolveValue(value));
    }

    public static Criteria insertValue(String field, Object value){
        return new InsertCriteria("=", field, resolveValue(value));
    }

    public static Criteria updateValue(String field, Object value){
        return new UpdateCriteria("=", field, resolveValue(value));
    }

    public String toSQL(){
        return field + sqlOperator + value;
    }

    public String toSQLforPS(){
        return field + sqlOperator + "?";
    }

    public Object getValue(){
        return value;
    }

    public String getField(){
        return field;
    }

    public static Object resolveValue(Object o){
        if (o instanceof Person){
            return ((Person) o).getId();
        }
        if (!(o instanceof Number)){
            return "'" + o + "'";
        }
        return o;
    }

    @Override
    public boolean equals(Object obj) {
        return field.equals(((Criteria)obj).field);
    }

}
class InsertCriteria extends Criteria{
    public InsertCriteria(String sqlOperator, String field, Object value) {
        super(sqlOperator, field, value);
    }
}

class UpdateCriteria extends Criteria{
    public UpdateCriteria(String sqlOperator, String field, Object value) {
        super(sqlOperator, field, value);
    }
}

class WhereCriteria extends Criteria{
    public WhereCriteria(String sqlOperator, String field, Object value) {
        super(sqlOperator, field, value);
    }
}