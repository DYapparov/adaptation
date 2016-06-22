package ru.vasya.document;


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
    private Integer registrationNumber;
    private Date registerDate;
    private String author;

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

    public Integer getRegistrationNumber() {
        return registrationNumber;
    }

    public void setRegistrationNumber(Integer registerNumber) {
        this.registrationNumber = registerNumber;
    }

    public Date getRegisterDate() {
        return registerDate;
    }

    public void setRegisterDate(Date registerDate) {
        this.registerDate = registerDate;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
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
        return this.getClass().getSimpleName();
    }
}
