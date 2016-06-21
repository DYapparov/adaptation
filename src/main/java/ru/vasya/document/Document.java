package ru.vasya.document;


import java.text.SimpleDateFormat;
import java.util.Date;

public abstract class Document implements Comparable{

    private static final String DATE_PATTERN = "HH:mm:ss dd.MM.yyyy";

    protected SimpleDateFormat sdf = new SimpleDateFormat(DATE_PATTERN);

    private int id;
    private String docName;
    private String text;
    private Integer registerNumber;
    private Date registerDate;
    private String author;

    public Document(){

    }

    public Document(int id, String docName, String text, Integer registerNumber, Date registerDate, String author) {
        this.id = id;
        this.docName = docName;
        this.text = text;
        this.registerNumber = registerNumber;
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

    public Integer getRegisterNumber() {
        return registerNumber;
    }

    public void setRegisterNumber(Integer registerNumber) {
        this.registerNumber = registerNumber;
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
                ", author: " + author + ", registration number " + registerNumber + ", registration date " + sdf.format(registerDate);
    }

    public String toStringForReport(){
        return this.getClass().getSimpleName() + " №" + registerNumber + " created " + sdf.format(registerDate) + ". " + docName;
                //Петров Петр Петрович:
                //Поручение №1 от 10.10.2010. Название документа

    }

    public int compareTo(Object o) {
        Document d = (Document) o;
        int result = author.compareTo(d.author);
        if (result==0){
            return registerNumber.compareTo(d.registerNumber);
        }
        return result;
    }
}
