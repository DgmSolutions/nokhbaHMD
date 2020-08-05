package com.nokhba.nokhbahmd.Model;

public class Data {
    private String  title,body,date;

    public Data(String title, String body) {
        this.title = title;
        this.body = body;
    }

    public Data() {
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
