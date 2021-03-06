package com.example.tonytea.evenu;

import java.io.Serializable;
import java.util.ArrayList;

public class Event implements Serializable {

    private String eventTitle;
    private String eventLocation;
    private String eventDate;
    private String eventTime;
    private String eventDescription;
    private ArrayList<String> keywords;
    private String eventID;

    public Event(){}

    public Event(String name, String eventLocation, String eventDate, String eventTime, String eventDescription,
                 String eventID, ArrayList<String> ks){
        this.eventTitle = name;
        this.eventLocation = eventLocation;
        this.eventDate = eventDate;
        this.eventTime = eventTime;
        this.eventDescription = eventDescription;
        this.keywords = new ArrayList<String>(ks);
        this.eventID = eventID;
    }

    public String getEventTitle(){
        return eventTitle;
    }


    public String getEventLocation() {
        return eventLocation;
    }

    public String getEventDate() {
        return eventDate;
    }

    public String getEventTime() {
        return eventTime;
    }

    public String getEventDescription() {
        return eventDescription;
    }

    public String getEventID() {
        return eventID;
    }

    public ArrayList<String> getKeywords() {
        return keywords;
    }

    public void setKeywords(ArrayList<String> keywords) {
        this.keywords = keywords;
    }
}


