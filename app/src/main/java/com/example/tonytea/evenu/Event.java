package com.example.tonytea.evenu;

public class Event {

    private String eventTitle;
    private String eventLocation;
    private String eventDate;
    private String eventTime;
    private String eventDescription;
    private String eventKeywords;
    private String eventID;

    public Event(String name, String eventLocation, String eventDate, String eventTime, String eventDescription,
                 String eventKeywords, String eventID){
        this.eventTitle = name;
        this.eventLocation = eventLocation;
        this.eventDate = eventDate;
        this.eventTime = eventTime;
        this.eventDescription = eventDescription;
        this.eventKeywords = eventKeywords;

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

    public String getEventKeywords() {
        return eventKeywords;
    }

    public String getEventID() {
        return eventID;
    }
}


