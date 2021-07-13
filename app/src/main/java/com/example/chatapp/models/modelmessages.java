package com.example.chatapp.models;

public class modelmessages {
    String message,senderid,time;

    public modelmessages(String message, String senderid, String time) {
        this.message = message;
        this.senderid = senderid;
        this.time = time;
    }

    public modelmessages() {

    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSenderid() {
        return senderid;
    }

    public void setSenderid(String senderid) {
        this.senderid = senderid;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
