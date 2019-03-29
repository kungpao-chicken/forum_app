package com.example.wangzhixiang.librarytest.control;

public class MessageEvent {
    private String message;
    private Object object;

    public MessageEvent(Object object) {
        this.object = object;
    }

    public MessageEvent(String message){
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }
}
