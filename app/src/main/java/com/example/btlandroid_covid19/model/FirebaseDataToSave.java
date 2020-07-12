package com.example.btlandroid_covid19.model;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class FirebaseDataToSave {
    public String title;
    public String link;
    public String source;
    public String uid;
    public long time;

    public FirebaseDataToSave() {
    }

    public FirebaseDataToSave(String title, String link, String source, String uid, long time) {
        this.title = title;
        this.link = link;
        this.source = source;
        this.uid = uid;
        this.time = time;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "FirebaseDataToSave{" +
                "title='" + title + '\'' +
                ", link='" + link + '\'' +
                ", source='" + source + '\'' +
                ", uid='" + uid + '\'' +
                ", time=" + time +
                '}';
    }
}
