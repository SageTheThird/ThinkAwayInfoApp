package com.homie.psychq.room2;



import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Observable;


public interface PostReadDataSource {


    Completable addPostReadId(PostReadEntity postReadEntity);

    List<PostReadEntity> isPostAlreadyRead(String post_id);

    List<PostReadEntity> getAllPostsRead();

}
