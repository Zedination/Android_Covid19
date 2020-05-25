package com.example.btlandroid_covid19.model;

public class NewsCrawler {
    private String link;
    private String time;
    private String source;
    private String title;

    public NewsCrawler(String title,String link, String time, String source) {
        this.link = link;
        this.time = time;
        this.source = source;
        this.title = title;
    }

    public NewsCrawler() {
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
