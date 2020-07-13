package com.homie.psychq.main.models.categories;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class ResultsCategories implements Serializable {

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
    List<CategoryFeatured> categories;


    public ResultsCategories() {
    }

    public ResultsCategories(int count, String next, String previous, List<CategoryFeatured> categories) {
        this.count = count;
        this.next = next;
        this.previous = previous;
        this.categories = categories;
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

    public List<CategoryFeatured> getCategories() {
        return categories;
    }

    public void setCategories(List<CategoryFeatured> categories) {
        this.categories = categories;
    }

    @Override
    public String toString() {
        return "ResultsCategories{" +
                "count=" + count +
                ", next='" + next + '\'' +
                ", previous='" + previous + '\'' +
                ", categories=" + categories +
                '}';
    }
}
