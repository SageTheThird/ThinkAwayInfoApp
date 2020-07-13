package com.homie.psychq.main.api.wordsapi.models;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class Definition {


    @SerializedName("definition")
    @Expose
    private String definition;

    @SerializedName("partOfSpeech")
    @Expose
    private String partOfSpeech;


    public Definition() {
    }

    public Definition(String definition, String partOfSpeech) {
        this.definition = definition;
        this.partOfSpeech = partOfSpeech;
    }

    public String getDefinition() {
        return definition;
    }

    public void setDefinition(String definition) {
        this.definition = definition;
    }

    public String getPartOfSpeech() {
        return partOfSpeech;
    }

    public void setPartOfSpeech(String partOfSpeech) {
        this.partOfSpeech = partOfSpeech;
    }

    @Override
    public String toString() {
        return "Definition{" +
                "definition='" + definition + '\'' +
                ", partOfSpeech='" + partOfSpeech + '\'' +
                '}';
    }
}
