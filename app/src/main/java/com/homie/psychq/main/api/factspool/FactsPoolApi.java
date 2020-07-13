package com.homie.psychq.main.api.factspool;

import com.homie.psychq.main.models.factspool.DateFact;
import com.homie.psychq.main.models.factspool.NumberFact;
import com.homie.psychq.main.models.factspool.YearFact;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;


/*End-Points for getting facts about numbers,years,dates*/

public interface FactsPoolApi {

    @GET("{number}?json")
    Call<NumberFact> getNumbersFact(@Path("number") int number);

    @GET("{year}/year?json")
    Call<YearFact> getYearsFact(@Path("year") int year);

    @GET("{month}/{day}/date?json")
    Call<DateFact> getDateFact(@Path("month") int month, @Path("day") int day);

}
