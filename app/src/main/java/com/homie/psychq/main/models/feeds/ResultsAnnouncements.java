package com.homie.psychq.main.models.feeds;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class ResultsAnnouncements implements Serializable {

    @SerializedName("count")
    @Expose
    int count;


    @SerializedName("next")
    @Expose
    String next;

    @SerializedName("previous")
    @Expose
    String previous;

    @SerializedName("results")
    @Expose
    List<Announcement> announcements;


    public ResultsAnnouncements() {
    }

    public ResultsAnnouncements(int count, String next, String previous, List<Announcement> announcements) {
        this.count = count;
        this.next = next;
        this.previous = previous;
        this.announcements = announcements;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getNext() {
        return next;
    }

    public void setNext(String next) {
        this.next = next;
    }

    public String getPrevious() {
        return previous;
    }

    public void setPrevious(String previous) {
        this.previous = previous;
    }

    public List<Announcement> getAnnouncements() {
        return announcements;
    }

    public void setAnnouncements(List<Announcement> announcements) {
        this.announcements = announcements;
    }

    @Override
    public String toString() {
        return "ResultsAnnouncements{" +
                "count=" + count +
                ", next='" + next + '\'' +
                ", previous='" + previous + '\'' +
                ", announcements=" + announcements +
                '}';
    }
}
