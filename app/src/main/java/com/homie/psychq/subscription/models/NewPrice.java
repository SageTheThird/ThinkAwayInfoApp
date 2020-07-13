package com.homie.psychq.subscription.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class NewPrice {

    @SerializedName("priceMicros")
    @Expose
    private String priceMicros;

    @SerializedName("currency")
    @Expose
    private String currency;


    public NewPrice() {
    }

    public NewPrice(String priceMicros, String currency) {
        this.priceMicros = priceMicros;
        this.currency = currency;
    }

    public String getPriceMicros() {
        return priceMicros;
    }

    public void setPriceMicros(String priceMicros) {
        this.priceMicros = priceMicros;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    @Override
    public String toString() {
        return "NewPrice{" +
                "priceMicros='" + priceMicros + '\'' +
                ", currency='" + currency + '\'' +
                '}';
    }
}
