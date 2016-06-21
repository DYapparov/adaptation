package ru.vasya.document;


import java.text.SimpleDateFormat;
import java.util.Date;

public abstract class Document implements Comparable, IStorable{

    private static final String DATE_PATTERN = "HH:mm:ss dd.MM.yyyy";

    protected SimpleDateFormat sdf = new SimpleDateFormat(DATE_PATTERN);

    private int id;
    private String docName;
    private String text;
    private Integer registrationNumber;
    private Date registerDate;
    private String author;

    public Document(){

    }

    public Document(int id, String docName, String text, Integer registerNumber, Date registerDate, String author) {
        this.id = id;
        this.docName = docName;
        this.text = text;
        this.registrationNumber = registerNumber;
        this.registerDate = registerDate;
        this.author = author;
    }

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
                ", author: " + author + ", registration number " + registrationNumber + ", registration date " + sdf.format(registerDate);
    }

    public String toStringForReport(){
        return this.getClass().getSimpleName() + " №" + registrationNumber + " created " + sdf.format(registerDate) + ". " + docName;
    }

    public int compareTo(Object o) {
        Document d = (Document) o;
        int result = author.compareTo(d.author);
        if (result==0){
            return registrationNumber.compareTo(d.registrationNumber);
        }
        return result;
    }

    public String getTable() {
        return this.getClass().getSimpleName();
    }
}
