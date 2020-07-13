package com.homie.psychq.room2;


import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Observable;


/*
 * Data Access Object for posts read by user in database
 * */

@Dao
public interface PostReadDAO {
    //This will determine whether the post is already been read or not

    @Insert
    Completable addPostReadId(PostReadEntity postReadEntity);

    @Query("select * from PostRead where postId ==:post_id ")
    List<PostReadEntity> isPostAlreadyRead(String post_id);

    @Query("select * from PostRead")
    List<PostReadEntity> getAllPostsRead();
}
