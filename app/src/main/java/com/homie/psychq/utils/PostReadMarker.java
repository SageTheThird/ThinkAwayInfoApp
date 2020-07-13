package com.homie.psychq.utils;

import android.util.Log;

import com.homie.psychq.room2.DatabaseTransactions;
import com.homie.psychq.room2.PostReadEntity;

import java.util.List;

import io.reactivex.CompletableObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class PostReadMarker {

    /*
    * Class is responsible for checking and marking the posts which user have read
    * */

    private static final String TAG = "PostReadMarker";

    private DatabaseTransactions databaseTransactions;

    public PostReadMarker(DatabaseTransactions databaseTransactions) {
        this.databaseTransactions = databaseTransactions;
    }

    public void checkIfPostIsReadAlready(String id, int customOrdering) {
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {


                List<PostReadEntity> postReadEntityList = databaseTransactions.isPostAlreadyRead(id);

                if(postReadEntityList.size() > 0){
                    //id is already entered, Post Is Already Been Read
                }else {
                    //here we enter the id to db, Post Is Not Read Before
                    markPostAsReadInDB(new PostReadEntity(customOrdering,id));
                }

            }
        });
    }

    private void markPostAsReadInDB(PostReadEntity postReadEntity) {

        databaseTransactions.addPostReadId(postReadEntity)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onComplete() {
                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                });
    }

}
