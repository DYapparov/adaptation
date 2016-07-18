package ru.vasya.service.db.query.parts;

/**
 * Created by dyapparov on 30.06.2016.
 */
public enum LogicalOperation {
    EQUALS("="),
    GT(">"),
    LT("<"),
    LE("<="),
    GE(">="),
    NE("<>")
    ;

    private String operation;

    LogicalOperation(String operation) {
        this.operation = operation;
    }

    public String getOperation() {
        return operation;
    }
}
