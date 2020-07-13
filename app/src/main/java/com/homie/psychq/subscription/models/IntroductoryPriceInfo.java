package com.homie.psychq.subscription.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class IntroductoryPriceInfo {


    /*Introductory price information of the subscription. This is only present when the subscription was purchased with an introductory price.

        This field does not indicate the subscription is currently in introductory price period.*/



    /*ISO 4217 currency code for the introductory subscription price. For example, if the price is specified in British pounds sterling, price_currency_code is "GBP".*/
    @SerializedName("introductoryPriceCurrencyCode")
    @Expose
    private String introductoryPriceCurrencyCode;


    /*Introductory price of the subscription, not including tax. The currency is the same as price_currency_code. Price is expressed in micro-units, where 1,000,000 micro-units represents one unit of the currency. For example, if the subscription price is €1.99, price_amount_micros is 1990000.*/
    @SerializedName("introductoryPriceAmountMicros")
    @Expose
    private long introductoryPriceAmountMicros;


    /*Introductory price period, specified in ISO 8601 format. Common values are (but not limited to) "P1W" (one week), "P1M" (one month), "P3M" (three months), "P6M" (six months), and "P1Y" (one year).*/
    @SerializedName("introductoryPricePeriod")
    @Expose
    private String introductoryPricePeriod;


    /*The number of billing period to offer introductory pricing.	*/
    @SerializedName("introductoryPriceCycles")
    @Expose
    private int introductoryPriceCycles;


    public IntroductoryPriceInfo() {
    }

    public IntroductoryPriceInfo(String introductoryPriceCurrencyCode, long introductoryPriceAmountMicros, String introductoryPricePeriod, int introductoryPriceCycles) {
        this.introductoryPriceCurrencyCode = introductoryPriceCurrencyCode;
        this.introductoryPriceAmountMicros = introductoryPriceAmountMicros;
        this.introductoryPricePeriod = introductoryPricePeriod;
        this.introductoryPriceCycles = introductoryPriceCycles;
    }

    public String getIntroductoryPriceCurrencyCode() {
        return introductoryPriceCurrencyCode;
    }

    public void setIntroductoryPriceCurrencyCode(String introductoryPriceCurrencyCode) {
        this.introductoryPriceCurrencyCode = introductoryPriceCurrencyCode;
    }

    public long getIntroductoryPriceAmountMicros() {
        return introductoryPriceAmountMicros;
    }

    public void setIntroductoryPriceAmountMicros(long introductoryPriceAmountMicros) {
        this.introductoryPriceAmountMicros = introductoryPriceAmountMicros;
    }

    public String getIntroductoryPricePeriod() {
        return introductoryPricePeriod;
    }

    public void setIntroductoryPricePeriod(String introductoryPricePeriod) {
        this.introductoryPricePeriod = introductoryPricePeriod;
    }

    public int getIntroductoryPriceCycles() {
        return introductoryPriceCycles;
    }

    public void setIntroductoryPriceCycles(int introductoryPriceCycles) {
        this.introductoryPriceCycles = introductoryPriceCycles;
    }

    @Override
    public String toString() {
        return "IntroductoryPriceInfo{" +
                "introductoryPriceCurrencyCode='" + introductoryPriceCurrencyCode + '\'' +
                ", introductoryPriceAmountMicros=" + introductoryPriceAmountMicros +
                ", introductoryPricePeriod='" + introductoryPricePeriod + '\'' +
                ", introductoryPriceCycles=" + introductoryPriceCycles +
                '}';
    }
}
