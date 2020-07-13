package com.homie.psychq.main.models.factspool;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/*Model Class For Fetching Date Facts*/

public class DateFact implements Serializable {


    @SerializedName("text")
    @Expose
    String text;

    @SerializedName("year")
    @Expose
    int year;


    @SerializedName("number")
    @Expose
    int number;

    @SerializedName("found")
    @Expose
    boolean found;

    @SerializedName("type")
    @Expose
    String type;

    @Override
    public String toString() {
        return "DateFact{" +
                "text='" + text + '\'' +
                ", year=" + year +
                ", number=" + number +
                ", found=" + found +
                ", type='" + type + '\'' +
                '}';
    }

    public DateFact() {
    }

    public DateFact(String text, int year, int number, boolean found, String type) {
        this.text = text;
        this.year = year;
        this.number = number;
        this.found = found;
        this.type = type;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
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
}
