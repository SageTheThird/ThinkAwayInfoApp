package com.homie.psychq.main.models.crashcourses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class ResultsCrashCourses implements Serializable {

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
    List<CrashCourseUnit> crashCourses;


    public ResultsCrashCourses() {
    }

    public ResultsCrashCourses(int count, String next, String previous, List<CrashCourseUnit> crashCourses) {
        this.count = count;
        this.next = next;
        this.previous = previous;
        this.crashCourses = crashCourses;
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

    public List<CrashCourseUnit> getCrashCourses() {
        return crashCourses;
    }

    public void setCrashCourses(List<CrashCourseUnit> crashCourses) {
        this.crashCourses = crashCourses;
    }

    @Override
    public String toString() {
        return "ResultsCrashCourses{" +
                "count=" + count +
                ", next='" + next + '\'' +
                ", previous='" + previous + '\'' +
                ", crashCourses=" + crashCourses +
                '}';
    }
}
