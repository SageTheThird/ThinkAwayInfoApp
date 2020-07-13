package com.homie.psychq.main.models.factspool;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;


/*Model Class For Fetching Number Facts*/

public class NumberFact implements Serializable {


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

    @Override
    public String toString() {
        return "NumberFact{" +
                "text='" + text + '\'' +
                ", number=" + number +
                ", found=" + found +
                ", type='" + type + '\'' +
                '}';
    }

    public NumberFact() {
    }

    public NumberFact(String text, int number, boolean found, String type) {
        this.text = text;
        this.number = number;
        this.found = found;
        this.type = type;
    }
}
