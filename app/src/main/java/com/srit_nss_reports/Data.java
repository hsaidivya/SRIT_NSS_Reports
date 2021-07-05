package com.srit_nss_reports;

public class Data {
    String EventName;
    String Description ;
    String Date;
    int ts;

    public int getTs() {
        return ts;
    }

    public void Data(){

    }

    public Data(String EventName, String Date, String Description, int ts) {
        this.EventName =EventName;
        this.Date= Date;
        this.Description=Description;
        this.ts=ts;
    }

    public String getTitle() {
        return EventName;
    }

    public String getDate() {
        return Date;
    }

    public String getDes() {
        return Description;
    }
}
