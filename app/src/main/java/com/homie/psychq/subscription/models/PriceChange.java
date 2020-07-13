package com.homie.psychq.subscription.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PriceChange {


    /*The latest price change information available. This is present only when there is an upcoming price change for the subscription yet to be applied.

Once the subscription renews with the new price or the subscription is canceled, no price change information will be returned.*/

    @SerializedName("newPrice")
    @Expose
    private NewPrice newPrice;

    @SerializedName("state")
    @Expose
    private int state;


    public PriceChange() {
    }

    public PriceChange(NewPrice newPrice, int state) {
        this.newPrice = newPrice;
        this.state = state;
    }

    public NewPrice getNewPrice() {
        return newPrice;
    }

    public void setNewPrice(NewPrice newPrice) {
        this.newPrice = newPrice;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return "PriceChange{" +
                "newPrice=" + newPrice +
                ", state=" + state +
                '}';
    }
}
