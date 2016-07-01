package ru.vasya.service.query.parts;

/**
 * Created by dyapparov on 30.06.2016.
 */
public class FieldsPart {
    private FieldToSelect leftPart;
    private Object compareTo;
    private LogicalOperation operation;

    public FieldToSelect getLeftPart() {
        return leftPart;
    }

    public FieldsPart(FieldToSelect leftPart, Object compareTo, LogicalOperation operation) {
        this.leftPart = leftPart;
        this.compareTo = compareTo;
        this.operation = operation;
    }

    public void setLeftPart(FieldToSelect leftPart) {
        this.leftPart = leftPart;
    }

    public Object getCompareTo() {
        return compareTo;
    }

    public void setCompareTo(Object compareTo) {
        this.compareTo = compareTo;
    }

    public LogicalOperation getOperation() {
        return operation;
    }

    public void setOperation(LogicalOperation operation) {
        this.operation = operation;
    }
}
