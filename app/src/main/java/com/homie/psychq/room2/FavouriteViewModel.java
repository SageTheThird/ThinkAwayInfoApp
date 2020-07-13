package com.homie.psychq.room2;

import androidx.lifecycle.ViewModel;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Observable;

public class FavouriteViewModel extends ViewModel {


    private LocalDataSource mLocalDataSource;

    public FavouriteViewModel(LocalDataSource mLocalDataSource) {
        this.mLocalDataSource = mLocalDataSource;
    }

    public Observable<List<PostFavEntity>> getAllPostFav(){
        return mLocalDataSource.getAllPostFav();
    }

    public Completable updatePostFav(PostFavEntity postFavEntity){
        return mLocalDataSource.updatePostFav(postFavEntity);
    }

    public Observable<PostFavEntity> getPostFav(int id){
        return mLocalDataSource.getPostFav(id);
    }

    public Completable addPostFav(PostFavEntity postFavEntity){
        return mLocalDataSource.addPostFav(postFavEntity);
    }

    public Completable deletePostFav(String title){
        return mLocalDataSource.deletePostFav(title);
    }


    public Observable<List<ArticleFavEntity>> getAllArticleFav(){
        return mLocalDataSource.getAllArticlesFav();
    }

    public Completable updateArticleFav(ArticleFavEntity articleFavEntity){
        return mLocalDataSource.updateArticleFav(articleFavEntity);
    }

    public Observable<ArticleFavEntity> getArticleFav(int id){
        return mLocalDataSource.getArticleFav(id);
    }

    public Completable addArticleFav(ArticleFavEntity articleFavEntity){
        return mLocalDataSource.addArticleFav(articleFavEntity);
    }

    public Completable deleteArticleFav(String title){
        return mLocalDataSource.deleteArticleFav(title);
    }

    public List<ArticleFavEntity> getArticleFavIfPresent(String title){
        return mLocalDataSource.getArticleFavIfPresent(title);
    }


    public List<PostFavEntity> getPostFavIfPresent(String title){
        return mLocalDataSource.getPostFavIfPresent(title);
    }

    public List<PostReadEntity> isPostAlreadyRead(String post_id){
        return mLocalDataSource.isPostAlreadyRead(post_id);
    }

    public List<PostReadEntity> getAllPostsRead(){
        return mLocalDataSource.getAllPostsRead();
    }

    public Completable addPostReadId(PostReadEntity postReadEntity){
        return mLocalDataSource.addPostReadId(postReadEntity);
    }



}
