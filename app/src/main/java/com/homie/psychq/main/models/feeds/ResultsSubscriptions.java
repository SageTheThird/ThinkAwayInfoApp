package com.homie.psychq.main.models.feeds;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class ResultsSubscriptions implements Serializable {



    @SerializedName("results")
    @Expose
    List<Subscription> subscriptions;


    public ResultsSubscriptions() {
    }

    public ResultsSubscriptions(List<Subscription> subscriptions) {
        this.subscriptions = subscriptions;
    }

    public List<Subscription> getSubscriptions() {
        return subscriptions;
    }

    public void setSubscriptions(List<Subscription> subscriptions) {
        this.subscriptions = subscriptions;
    }

    @Override
    public String toString() {
        return "ResultsSubscriptions{" +
                "subscriptions=" + subscriptions +
                '}';
    }
}
