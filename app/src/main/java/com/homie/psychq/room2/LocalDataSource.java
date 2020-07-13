package com.homie.psychq.room2;

import android.content.Context;

import androidx.room.RoomDatabase;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Observable;


/*Implements all the abstracted methods of all the tables*/

public class LocalDataSource implements PostFavDataSource,ArticleFavDataSource,PostReadDataSource {

    private PostFavDAO mPostFavDao;
    private ArticleFavDAO mArticleFavDao;
    private PostReadDAO postReadDAO;


    public LocalDataSource(Context context){
        FavouriteDatabase notesDatabase= FavouriteDatabase.getInstance(context);
        this.mPostFavDao =notesDatabase.getPostFavDAO();
        this.mArticleFavDao =notesDatabase.getArticleFavDAO();
        this.postReadDAO =notesDatabase.getPostReadDAO();
    }

    public LocalDataSource(Context context, RoomDatabase.Callback callback) {


        FavouriteDatabase notesDatabase = FavouriteDatabase.getInstance(context, callback);
        this.mPostFavDao = notesDatabase.getPostFavDAO();
        this.mArticleFavDao =notesDatabase.getArticleFavDAO();
        this.postReadDAO =notesDatabase.getPostReadDAO();
    }

    @Override
    public Observable<List<PostFavEntity>> getAllPostFav() {
        return mPostFavDao.getAllPostFav();
    }



    @Override
    public Completable addPostFav(PostFavEntity song) {
        return mPostFavDao.addPostFav(song);
    }

    @Override
    public Completable updatePostFav(PostFavEntity song) {
        return mPostFavDao.updatePostFav(song);
    }

    @Override
    public Completable deletePostFav(String title) {
        return mPostFavDao.deletePostFav(title);
    }

    @Override
    public Observable<PostFavEntity> getPostFav(int id) {
        return mPostFavDao.getPostFav(id);
    }

    @Override
    public List<PostFavEntity> getPostFavIfPresent(String title) {
        return mPostFavDao.getPostIfPresent(title);
    }


    @Override
    public Observable<List<ArticleFavEntity>> getAllArticlesFav() {
        return mArticleFavDao.getAllArticlesFav();
    }

    @Override
    public Completable addArticleFav(ArticleFavEntity articleFavEntity) {
        return mArticleFavDao.addArticleFav(articleFavEntity);
    }

    @Override
    public Completable updateArticleFav(ArticleFavEntity articleFavEntity) {
        return mArticleFavDao.updateArticleFav(articleFavEntity);
    }

    @Override
    public Completable deleteArticleFav(String name) {
        return mArticleFavDao.deleteArticleFav(name);
    }

    @Override
    public Observable<ArticleFavEntity> getArticleFav(int id) {
        return mArticleFavDao.getArticleFav(id);
    }

    @Override
    public List<ArticleFavEntity> getArticleFavIfPresent(String title) {
        return mArticleFavDao.getArticleIfPresent(title);
    }

    @Override
    public Completable addPostReadId(PostReadEntity postReadEntity) {
        return postReadDAO.addPostReadId(postReadEntity);
    }

    @Override
    public List<PostReadEntity> isPostAlreadyRead(String post_id) {
        return postReadDAO.isPostAlreadyRead(post_id);
    }

    @Override
    public List<PostReadEntity> getAllPostsRead() {
        return postReadDAO.getAllPostsRead();
    }
}
