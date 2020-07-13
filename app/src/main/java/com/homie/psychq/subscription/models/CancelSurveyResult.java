package com.homie.psychq.subscription.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CancelSurveyResult {

    /*The cancellation reason the user chose in the survey. Possible values are:
        0 : Other
        1 : I don't use this service enough
        2 : Technical issues
        3 : Cost-related reasons
        4 : I found a better app*/

    @SerializedName("cancelSurveyReason")
    @Expose
    private int cancelSurveyReason;


    /*The customized input cancel reason from the user. Only present when cancelReason is 0.*/
    @SerializedName("userInputCancelReason")
    @Expose
    private String userInputCancelReason;


    public CancelSurveyResult() {
    }

    public CancelSurveyResult(int cancelSurveyReason, String userInputCancelReason) {
        this.cancelSurveyReason = cancelSurveyReason;
        this.userInputCancelReason = userInputCancelReason;
    }

    public int getCancelSurveyReason() {
        return cancelSurveyReason;
    }

    public void setCancelSurveyReason(int cancelSurveyReason) {
        this.cancelSurveyReason = cancelSurveyReason;
    }

    public String getUserInputCancelReason() {
        return userInputCancelReason;
    }

    public void setUserInputCancelReason(String userInputCancelReason) {
        this.userInputCancelReason = userInputCancelReason;
    }

    @Override
    public String toString() {
        return "CancelSurveyResult{" +
                "cancelSurveyReason=" + cancelSurveyReason +
                ", userInputCancelReason='" + userInputCancelReason + '\'' +
                '}';
    }
}
