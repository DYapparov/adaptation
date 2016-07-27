package ru.vasya.rest.response;

public abstract class ResponseObject {
    private String template;
    private String tabTitle;
    private String target;
    private String prefix;

    public ResponseObject() {

    }

    public ResponseObject(String template, String tabTitle, String target, String prefix) {
        this.template = template;
        this.tabTitle = tabTitle;
        this.target = target;
        this.prefix = prefix;
    }

    public String getTemplate() {
        return template;
    }

    public void setTemplate(String template) {
        this.template = template;
    }

    public String getTabTitle() {
        return tabTitle;
    }

    public void setTabTitle(String tabTitle) {
        this.tabTitle = tabTitle;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }
}
