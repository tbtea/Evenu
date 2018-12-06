package com.example.tonytea.evenu;

public class User {

    private String user_id;
    private String email;
    private String user_name;

    public User(String id, String e, String name){
        this.user_id = id;
        this.email = e;
        this.user_name = name;
    }

    public User(){}

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }
}
