package ru.vasya.document;

import java.util.Date;

@SedItem
public class Task extends  Document {
    private Date deliveryDate;
    private Date finishDate;
    private String performer;
    private boolean controlTag;
    private String controller;

    public Task(){

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
        return super.toString() + ", delivery date " + dateFormat.format(deliveryDate) + ", finish date " +
                dateFormat.format(finishDate) + ", performer :" + performer + ", control tag " + controlTag + (controlTag?", controller " + controller:"");
    }
}
