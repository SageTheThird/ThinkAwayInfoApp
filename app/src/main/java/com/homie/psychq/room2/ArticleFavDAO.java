package com.homie.psychq.room2;


import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Observable;


/*
* Data Access Object for bookmarked articles in database
* */

@Dao
public interface ArticleFavDAO {
    //Data Access Object
    //annotations do most of the job for inserting, just needs contact

    @Insert
    Completable addArticleFav(ArticleFavEntity articleFavEntity);

    @Update
    Completable updateArticleFav(ArticleFavEntity articleFavEntity);

    @Query("DELETE FROM ArticleFav WHERE title = :title")
    Completable deleteArticleFav(String title);

    @Query("select * from ArticleFav")
    Observable<List<ArticleFavEntity>> getAllArticlesFav();


    @Query("select * from ArticleFav where id ==:id")
    Observable<ArticleFavEntity> getArticleFav(int id);

    @Query("select * from ArticleFav where title ==:title ")
    List<ArticleFavEntity> getArticleIfPresent(String title);

}
