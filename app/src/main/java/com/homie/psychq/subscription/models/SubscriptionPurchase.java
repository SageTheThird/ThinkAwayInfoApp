package com.homie.psychq.subscription.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.reactivex.schedulers.Schedulers;

public class SubscriptionPurchase {


    /*This kind represents a subscriptionPurchase object in the androidpublisher service.*/
    @SerializedName("kind")
    @Expose
    private String kind;


    /*Time at which the subscription was granted, in milliseconds since the Epoch.*/
    @SerializedName("startTimeMillis")
    @Expose
    private long startTimeMillis;


    /*Time at which the subscription will expire, in milliseconds since the Epoch.*/
    @SerializedName("expiryTimeMillis")
    @Expose
    private long expiryTimeMillis;


    /*Time at which the subscription will be automatically resumed, in milliseconds since the Epoch. Only present if the user has requested to pause the subscription.*/
    @SerializedName("autoResumeTimeMillis")
    @Expose
    private long autoResumeTimeMillis;

    @SerializedName("autoRenewing")
    @Expose
    private boolean autoRenewing;

    @SerializedName("priceCurrencyCode")
    @Expose
    private String priceCurrencyCode;

    @SerializedName("priceAmountMicros")
    @Expose
    private long priceAmountMicros;


    @SerializedName("introductoryPriceInfo")
    @Expose
    private IntroductoryPriceInfo introductoryPriceInfo;


    /*ISO 3166-1 alpha-2 billing country/region code of the user at the time the subscription was granted.*/
    @SerializedName("countryCode")
    @Expose
    private String countryCode;


    /*A developer-specified string that contains supplemental information about an order.*/
    @SerializedName("developerPayload")
    @Expose
    private String developerPayload;


    /*The payment state of the subscription. Possible values are:
        0 : Payment pending
        1 : Payment received
        2 : Free trial
        3 : Pending deferred upgrade/downgrade*/
    @SerializedName("paymentState")
    @Expose
    private int paymentState;


    /*The reason why a subscription was canceled or is not auto-renewing. Possible values are:
        0 : User canceled the subscription
        1 : Subscription was canceled by the system, for example because of a billing problem
        2 : Subscription was replaced with a new subscription
        3 : Subscription was canceled by the developer*/
    @SerializedName("cancelReason")
    @Expose
    private int cancelReason;


    /*The time at which the subscription was canceled by the user, in milliseconds since the epoch. Only present if cancelReason is 0.*/
    @SerializedName("userCancellationTimeMillis")
    @Expose
    private long userCancellationTimeMillis;


    /*Information provided by the user when they complete the subscription cancellation flow (cancellation reason survey).*/
    @SerializedName("cancelSurveyResult")
    @Expose
    private CancelSurveyResult cancelSurveyResult;

    @SerializedName("orderId")
    @Expose
    private String orderId;

    @SerializedName("linkedPurchaseToken")
    @Expose
    private String linkedPurchaseToken;

    @SerializedName("purchaseType")
    @Expose
    private int purchaseType;

    @SerializedName("priceChange")
    @Expose
    private PriceChange priceChange;



    @SerializedName("profileName")
    @Expose
    private String profileName;

    @SerializedName("emailAddress")
    @Expose
    private String emailAddress;

    @SerializedName("givenName")
    @Expose
    private String givenName;

    @SerializedName("familyName")
    @Expose
    private String familyName;

    @SerializedName("profileId")
    @Expose
    private String profileId;



    /*0 : yet to be acknowledged
    * 1 : Acknowledged*/
    @SerializedName("acknowledgementState")
    @Expose
    private int acknowledgementState;


    public SubscriptionPurchase() {
    }

    public SubscriptionPurchase(String kind, long startTimeMillis, long expiryTimeMillis, long autoResumeTimeMillis, boolean autoRenewing, String priceCurrencyCode, long priceAmountMicros, IntroductoryPriceInfo introductoryPriceInfo, String countryCode, String developerPayload, int paymentState, int cancelReason, long userCancellationTimeMillis, CancelSurveyResult cancelSurveyResult, String orderId, String linkedPurchaseToken, int purchaseType, PriceChange priceChange, String profileName, String emailAddress, String givenName, String familyName, String profileId, int acknowledgementState) {
        this.kind = kind;
        this.startTimeMillis = startTimeMillis;
        this.expiryTimeMillis = expiryTimeMillis;
        this.autoResumeTimeMillis = autoResumeTimeMillis;
        this.autoRenewing = autoRenewing;
        this.priceCurrencyCode = priceCurrencyCode;
        this.priceAmountMicros = priceAmountMicros;
        this.introductoryPriceInfo = introductoryPriceInfo;
        this.countryCode = countryCode;
        this.developerPayload = developerPayload;
        this.paymentState = paymentState;
        this.cancelReason = cancelReason;
        this.userCancellationTimeMillis = userCancellationTimeMillis;
        this.cancelSurveyResult = cancelSurveyResult;
        this.orderId = orderId;
        this.linkedPurchaseToken = linkedPurchaseToken;
        this.purchaseType = purchaseType;
        this.priceChange = priceChange;
        this.profileName = profileName;
        this.emailAddress = emailAddress;
        this.givenName = givenName;
        this.familyName = familyName;
        this.profileId = profileId;
        this.acknowledgementState = acknowledgementState;
    }

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public long getStartTimeMillis() {
        return startTimeMillis;
    }

    public void setStartTimeMillis(long startTimeMillis) {
        this.startTimeMillis = startTimeMillis;
    }

    public long getExpiryTimeMillis() {
        return expiryTimeMillis;
    }

    public void setExpiryTimeMillis(long expiryTimeMillis) {
        this.expiryTimeMillis = expiryTimeMillis;
    }

    public long getAutoResumeTimeMillis() {
        return autoResumeTimeMillis;
    }

    public void setAutoResumeTimeMillis(long autoResumeTimeMillis) {
        this.autoResumeTimeMillis = autoResumeTimeMillis;
    }

    public boolean isAutoRenewing() {
        return autoRenewing;
    }

    public void setAutoRenewing(boolean autoRenewing) {
        this.autoRenewing = autoRenewing;
    }

    public String getPriceCurrencyCode() {
        return priceCurrencyCode;
    }

    public void setPriceCurrencyCode(String priceCurrencyCode) {
        this.priceCurrencyCode = priceCurrencyCode;
    }

    public long getPriceAmountMicros() {
        return priceAmountMicros;
    }

    public void setPriceAmountMicros(long priceAmountMicros) {
        this.priceAmountMicros = priceAmountMicros;
    }

    public IntroductoryPriceInfo getIntroductoryPriceInfo() {
        return introductoryPriceInfo;
    }

    public void setIntroductoryPriceInfo(IntroductoryPriceInfo introductoryPriceInfo) {
        this.introductoryPriceInfo = introductoryPriceInfo;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getDeveloperPayload() {
        return developerPayload;
    }

    public void setDeveloperPayload(String developerPayload) {
        this.developerPayload = developerPayload;
    }

    public int getPaymentState() {
        return paymentState;
    }

    public void setPaymentState(int paymentState) {
        this.paymentState = paymentState;
    }

    public int getCancelReason() {
        return cancelReason;
    }

    public void setCancelReason(int cancelReason) {
        this.cancelReason = cancelReason;
    }

    public long getUserCancellationTimeMillis() {
        return userCancellationTimeMillis;
    }

    public void setUserCancellationTimeMillis(long userCancellationTimeMillis) {
        this.userCancellationTimeMillis = userCancellationTimeMillis;
    }

    public CancelSurveyResult getCancelSurveyResult() {
        return cancelSurveyResult;
    }

    public void setCancelSurveyResult(CancelSurveyResult cancelSurveyResult) {
        this.cancelSurveyResult = cancelSurveyResult;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getLinkedPurchaseToken() {
        return linkedPurchaseToken;
    }

    public void setLinkedPurchaseToken(String linkedPurchaseToken) {
        this.linkedPurchaseToken = linkedPurchaseToken;
    }

    public int getPurchaseType() {
        return purchaseType;
    }

    public void setPurchaseType(int purchaseType) {
        this.purchaseType = purchaseType;
    }

    public PriceChange getPriceChange() {
        return priceChange;
    }

    public void setPriceChange(PriceChange priceChange) {
        this.priceChange = priceChange;
    }

    public String getProfileName() {
        return profileName;
    }

    public void setProfileName(String profileName) {
        this.profileName = profileName;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getGivenName() {
        return givenName;
    }

    public void setGivenName(String givenName) {
        this.givenName = givenName;
    }

    public String getFamilyName() {
        return familyName;
    }

    public void setFamilyName(String familyName) {
        this.familyName = familyName;
    }

    public String getProfileId() {
        return profileId;
    }

    public void setProfileId(String profileId) {
        this.profileId = profileId;
    }

    public int getAcknowledgementState() {
        return acknowledgementState;
    }

    public void setAcknowledgementState(int acknowledgementState) {
        this.acknowledgementState = acknowledgementState;
    }

    @Override
    public String toString() {
        return "SubscriptionPurchase{" +
                "kind='" + kind + '\'' +
                ", startTimeMillis=" + startTimeMillis +
                ", expiryTimeMillis=" + expiryTimeMillis +
                ", autoResumeTimeMillis=" + autoResumeTimeMillis +
                ", autoRenewing=" + autoRenewing +
                ", priceCurrencyCode='" + priceCurrencyCode + '\'' +
                ", priceAmountMicros=" + priceAmountMicros +
                ", introductoryPriceInfo=" + introductoryPriceInfo +
                ", countryCode='" + countryCode + '\'' +
                ", developerPayload='" + developerPayload + '\'' +
                ", paymentState=" + paymentState +
                ", cancelReason=" + cancelReason +
                ", userCancellationTimeMillis=" + userCancellationTimeMillis +
                ", cancelSurveyResult=" + cancelSurveyResult +
                ", orderId='" + orderId + '\'' +
                ", linkedPurchaseToken='" + linkedPurchaseToken + '\'' +
                ", purchaseType=" + purchaseType +
                ", priceChange=" + priceChange +
                ", profileName='" + profileName + '\'' +
                ", emailAddress='" + emailAddress + '\'' +
                ", givenName='" + givenName + '\'' +
                ", familyName='" + familyName + '\'' +
                ", profileId='" + profileId + '\'' +
                ", acknowledgementState=" + acknowledgementState +
                '}';
    }
}
