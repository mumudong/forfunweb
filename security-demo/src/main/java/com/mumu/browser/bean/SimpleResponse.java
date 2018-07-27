package com.mumu.browser.bean;

public class SimpleResponse {
    private Object content;
    public SimpleResponse(Object object){
        this.content = object;
    }

    public Object getContent() {
        return content;
    }

    public void setContent(Object content) {
        this.content = content;
    }
}
