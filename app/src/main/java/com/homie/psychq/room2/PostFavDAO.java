package com.homie.psychq.room2;


import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Observable;


/*
 * Data Access Object for bookmarked posts in database
 * */

@Dao
public interface PostFavDAO {
    //Data Access Object
    //annotations do most of the job for inserting, just needs contact

    @Insert
    Completable addPostFav(PostFavEntity postFavEntity);

    @Update
    Completable updatePostFav(PostFavEntity postFavEntity);

    @Query("DELETE FROM PostFav WHERE title = :title")
    Completable deletePostFav(String title);

    @Query("select * from PostFav")
    Observable<List<PostFavEntity>> getAllPostFav();


    @Query("select * from PostFav where id ==:id")
    Observable<PostFavEntity> getPostFav(int id);

    @Query("select * from PostFav where title ==:title ")
    List<PostFavEntity> getPostIfPresent(String title);

}
