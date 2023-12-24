package ru.netology.model;


public class Post {
    private long id;
    private String content;

    public Post() {
    }

    public Post(long id, String content) {
        this.id = id;
        this.content = content;
    }

    public long getId() {
        return id;
    }


    public String getContent() {
        return content;
    }


    @Override
    public String toString() {

        return "id: " + this.id + ", content: " + this.content;
    }
}