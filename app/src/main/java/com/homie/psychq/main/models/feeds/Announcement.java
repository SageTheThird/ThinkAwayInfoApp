package com.homie.psychq.main.models.feeds;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class Announcement {


    @SerializedName("id")
    @Expose
    private String id;

    @SerializedName("heading")
    @Expose
    private String heading;

    @SerializedName("message")
    @Expose
    private String message;


    public Announcement() {
    }

    public Announcement(String id, String heading, String message) {
        this.id = id;
        this.heading = heading;
        this.message = message;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getHeading() {
        return heading;
    }

    public void setHeading(String heading) {
        this.heading = heading;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "Announcement{" +
                "id='" + id + '\'' +
                ", heading='" + heading + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
