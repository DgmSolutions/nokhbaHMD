package com.example.nokhbahmd.Model;

public class Notification {
   private String to;
   private Data data;

    public Notification() {
    }

    public Notification(String to, Data data) {
        this.to = to;
        this.data = data;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }
}
