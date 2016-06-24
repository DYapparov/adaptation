package ru.vasya.model.document;


import ru.vasya.model.staff.Person;

import java.text.SimpleDateFormat;
import java.util.Date;

public abstract class Document implements Comparable, Storable {

    private static final String DATE_PATTERN = "dd.MM.yyyy";
    private static final String DATE_PATTERN_FOR_SORT = "yyyy.MM.dd";

    protected static final SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_PATTERN);
    protected static final SimpleDateFormat dateFormatForSort = new SimpleDateFormat(DATE_PATTERN_FOR_SORT);

    private int id;
    private String docName;
    private String text;
    private String registrationNumber;
    private Date registerDate;
    private Person author;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDocName() {
        return docName;
    }

    public void setDocName(String docName) {
        this.docName = docName;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public void setRegistrationNumber(String registerNumber) {
        this.registrationNumber = registerNumber;
    }

    public Date getRegisterDate() {
        return registerDate;
    }

    public void setRegisterDate(Date registerDate) {
        this.registerDate = registerDate;
    }

    public Person getAuthor() {
        return author;
    }

    public void setAuthor(Person author) {
        this.author = author;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() +": id " + id + ", document name: " + docName + ", text: " + text +
                ", author: " + author + ", registration number " + registrationNumber + ", registration date " + dateFormat.format(registerDate);
    }

    public String toStringForReport(){
        return this.getClass().getSimpleName() + " №" + registrationNumber + " created " + dateFormat.format(registerDate) + ". " + docName;
    }

    public int compareTo(Object o) {
        Document d = (Document) o;
        int result = dateFormatForSort.format(registerDate).compareTo(dateFormatForSort.format(d.registerDate));
        if (result==0){
            return registrationNumber.compareTo(d.registrationNumber);
        }
        return result;
    }

    public String getTable() {
        // --------------------------------  to do название таблицы
        return "";
    }
}
