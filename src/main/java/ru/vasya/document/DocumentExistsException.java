package ru.vasya.document;


public class DocumentExistsException extends Exception{
    public DocumentExistsException(String message){
        super(message);
    }
}