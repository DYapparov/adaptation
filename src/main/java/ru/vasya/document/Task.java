package ru.vasya.document;

import java.util.Date;

public class Task extends  Document {
    private Date deliveryDate;
    private Date finishDate;
    private String performer;
    private boolean controlTag;
    private String controller;

    public Task(){

    }

    public Task(int id, String docName, String text, Integer registerNumber, Date registerDate, String author, Date deliveryDate, Date finishDate, String performer, boolean controlTag, String controller) {
        super(id, docName, text, registerNumber, registerDate, author);
        this.deliveryDate = deliveryDate;
        this.finishDate = finishDate;
        this.performer = performer;
        this.controlTag = controlTag;
        this.controller = controller;
    }

    public Date getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(Date deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public Date getFinishDate() {
        return finishDate;
    }

    public void setFinishDate(Date finishDate) {
        this.finishDate = finishDate;
    }

    public String getPerformer() {
        return performer;
    }

    public void setPerformer(String performer) {
        this.performer = performer;
    }

    public boolean isControlTag() {
        return controlTag;
    }

    public void setControlTag(boolean controlTag) {
        this.controlTag = controlTag;
    }

    public String getController() {
        return controller;
    }

    public void setController(String controller) {
        this.controller = controller;
    }

    @Override
    public String toString(){
        return super.toString() + ", delivery date " + sdf.format(deliveryDate) + ", finish date " +
                sdf.format(finishDate) + ", performer :" + performer + ", control tag " + controlTag + (controlTag?", controller " + controller:"");
    }
}
