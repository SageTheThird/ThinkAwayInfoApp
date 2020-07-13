package com.homie.psychq.main.models.crashcourses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class ResultsArticles implements Serializable {

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
    List<Article> articles;


    public ResultsArticles() {
    }

    public ResultsArticles(int count, String next, String previous, List<Article> articles) {
        this.count = count;
        this.next = next;
        this.previous = previous;
        this.articles = articles;
    }

    @Override
    public String toString() {
        return "ResultsArticles{" +
                "count=" + count +
                ", next='" + next + '\'' +
                ", previous='" + previous + '\'' +
                ", articles=" + articles +
                '}';
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

    public List<Article> getArticles() {
        return articles;
    }

    public void setArticles(List<Article> articles) {
        this.articles = articles;
    }
}
