package com.homie.psychq.room2;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;


/*Most important class which handles all the transactions with database*/

public class DatabaseTransactions {
    private static final String TAG = "DatabaseTransactions";

    private Context context;
    private LocalDataSource mLocalDataSource;
    private FavouriteViewModel mFavouriteViewModel;
    private CompositeDisposable mDisposibles=new CompositeDisposable();

    public DatabaseTransactions(Context context) {
        this.context = context;

        mLocalDataSource=new LocalDataSource(context,CreateAndOpenCallBack);
        mFavouriteViewModel =new FavouriteViewModel(mLocalDataSource);
    }

    public Completable addPostFav(final PostFavEntity song){

        Log.d(TAG, "addPostFav: adding new note to database");

        return mFavouriteViewModel.addPostFav(song)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<List<PostFavEntity>> getAllPostFav(){
        return mFavouriteViewModel
                .getAllPostFav()
                .subscribeOn(Schedulers.io());
    }

    public Completable deletePostFav(final String title){

        return mFavouriteViewModel.deletePostFav(title)
                .subscribeOn(Schedulers.io());

    }

    public Completable updatePostFav(PostFavEntity postFavEntity){
        return mFavouriteViewModel.
                updatePostFav(postFavEntity)
                .subscribeOn(Schedulers.io());
    }

    public List<PostFavEntity> getPostFavIfPresent(String title){
        return mFavouriteViewModel
                .getPostFavIfPresent(title);
    }


    /*
    * Article Database Transaction Methods
    * */

    public Completable addArticleFav(final ArticleFavEntity articleFavEntity){

        Log.d(TAG, "addPostFav: adding new note to database");

        return mFavouriteViewModel.addArticleFav(articleFavEntity)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<List<ArticleFavEntity>> getAllArticlesFav(){
        return mFavouriteViewModel
                .getAllArticleFav()
                .subscribeOn(Schedulers.io());
    }

    public List<ArticleFavEntity> getArticleFavIfPresent(String title){
        return mFavouriteViewModel
                .getArticleFavIfPresent(title);
    }





    public Completable deleteArticleFav(final String title){

        return mFavouriteViewModel.deleteArticleFav(title)
                .subscribeOn(Schedulers.io());

    }

    public Completable updateArticleFav(ArticleFavEntity articleFavEntity){
        return mFavouriteViewModel.
                updateArticleFav(articleFavEntity)
                .subscribeOn(Schedulers.io());
    }


    /*Post-Read Entity*/
    public List<PostReadEntity> isPostAlreadyRead(String post_id){
        return mFavouriteViewModel
                .isPostAlreadyRead(post_id);
    }

    public List<PostReadEntity> getAllPostsRead(){
        return mFavouriteViewModel
                .getAllPostsRead();
    }


    public Completable addPostReadId(final PostReadEntity postReadEntity){

        Log.d(TAG, "addPostFav: adding new note to database");

        return mFavouriteViewModel.addPostReadId(postReadEntity)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }


    RoomDatabase.Callback CreateAndOpenCallBack=new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);

            Log.d("CallBack", "onCreate: database callBack onCreate");

        }

        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);

            //calls everytime database is opened


        }

    };






}
