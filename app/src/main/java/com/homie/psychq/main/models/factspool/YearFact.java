package com.homie.psychq.main.models.factspool;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;


/*Model Class For Fetching Year Facts*/

public class YearFact implements Serializable {

    @SerializedName("date")
    @Expose
    String date;


    @SerializedName("text")
    @Expose
    String text;


    @SerializedName("number")
    @Expose
    int number;

    @SerializedName("found")
    @Expose
    boolean found;

    @SerializedName("type")
    @Expose
    String type;

    public YearFact() {
    }

    @Override
    public String toString() {
        return "YearFact{" +
                "date='" + date + '\'' +
                ", text='" + text + '\'' +
                ", number=" + number +
                ", found=" + found +
                ", type='" + type + '\'' +
                '}';
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public boolean isFound() {
        return found;
    }

    public void setFound(boolean found) {
        this.found = found;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public YearFact(String date, String text, int number, boolean found, String type) {
        this.date = date;
        this.text = text;
        this.number = number;
        this.found = found;
        this.type = type;
    }
}
