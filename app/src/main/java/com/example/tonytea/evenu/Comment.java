package com.example.tonytea.evenu;

public class Comment {

    private String message;
    private String author;
    private String id;

    public Comment(String message, String author, String i) {
        this.message = message;
        this.author = author;
        this.id = i;
    }

    public Comment() {
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
}
