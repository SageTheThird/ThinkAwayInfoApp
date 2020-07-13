package com.homie.psychq.main.api.wordsapi.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class WordResults implements Serializable {

    @SerializedName("word")
    @Expose
    private String word;


    @SerializedName("definitions")
    @Expose
    List<Definition> definitions;


    public WordResults() {
    }

    public WordResults(String word, List<Definition> definitions) {
        this.word = word;
        this.definitions = definitions;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public List<Definition> getDefinitions() {
        return definitions;
    }

    public void setDefinitions(List<Definition> definitions) {
        this.definitions = definitions;
    }

    @Override
    public String toString() {
        return "Results{" +
                "word='" + word + '\'' +
                ", definitions=" + definitions +
                '}';
    }
}
