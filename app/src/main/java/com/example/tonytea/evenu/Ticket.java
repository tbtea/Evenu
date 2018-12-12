package com.example.tonytea.evenu;

public class Ticket {

    private String ticket_id;
    private String atendee_id;
    private String event_id;

    public Ticket(String tid, String aid, String eid){
        this.ticket_id = tid;
        this.atendee_id = aid;
        this.event_id = eid;
    }

    public String getTicket_id() {
        return ticket_id;
    }

    public void setTicket_id(String ticket_id) {
        this.ticket_id = ticket_id;
    }

    public String getAtendee_id() {
        return atendee_id;
    }

    public void setAtendee_id(String atendee_id) {
        this.atendee_id = atendee_id;
    }

    public String getEvent_id() {
        return event_id;
    }

    public void setEvent_id(String event_id) {
        this.event_id = event_id;
    }
}
