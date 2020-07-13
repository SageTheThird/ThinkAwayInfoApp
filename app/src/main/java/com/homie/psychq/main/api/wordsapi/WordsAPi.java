package com.homie.psychq.main.api.wordsapi;


import com.homie.psychq.main.api.wordsapi.models.WordResults;

import io.reactivex.Flowable;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface WordsAPi {


//      @Headers("Authorization : Basic YWRtaW46ZWxpdGVtZWR1bGEwOTAw")
        @GET("{word}/definitions")
        Flowable<WordResults> getDefinitionsOfWord(@Path("word") String word);



}
