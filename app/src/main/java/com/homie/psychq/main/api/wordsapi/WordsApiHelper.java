package com.homie.psychq.main.api.wordsapi;

import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.homie.psychq.R;
import com.homie.psychq.main.api.wordsapi.models.WordResults;
import com.homie.psychq.utils.BottomSheetWordsMeanings;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class WordsApiHelper {
    private static final String TAG = "WordsApiHelper";



    private WordsAPi wordsAPi;
    private AppCompatActivity activity;
    private BottomSheetWordsMeanings bottomSheetWordsMeanings;


    public WordsApiHelper(AppCompatActivity activity,WordsAPi wordsAPi) {
        this.activity=activity;
        this.wordsAPi=wordsAPi;

    }

    public void showDefinitionsForWord(String word){


        wordsAPi.getDefinitionsOfWord(word)
                .toObservable()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<WordResults>() {
                    @Override
                    public void onSubscribe(Disposable d) {
//                        mDisposibles.add(d);
                    }

                    @Override
                    public void onNext(WordResults wordResults) {

                        if(wordResults != null){
                            Log.d(TAG, "onNext: "+wordResults.getDefinitions().toString());

                            if(!activity.isFinishing()){

                                FragmentManager fm = activity.getSupportFragmentManager();
                                FragmentTransaction ft = fm.beginTransaction();



                                bottomSheetWordsMeanings = new BottomSheetWordsMeanings();
                                bottomSheetWordsMeanings.setLayout(R.layout.bottom_sheet_words_layout);
                                bottomSheetWordsMeanings.provideContext(activity);
                                bottomSheetWordsMeanings.setData(wordResults);
                                bottomSheetWordsMeanings.show(ft,"BottomSheetWordsMeanings");


                            }

                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                        Log.d(TAG, "onError: Error While Fetching Word : "+e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
