package com.homie.psychq.room2;



import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Observable;


public interface PostFavDataSource {


    Observable<List<PostFavEntity>> getAllPostFav();

    Completable addPostFav(PostFavEntity postFavEntity);

    Completable updatePostFav(PostFavEntity postFavEntity);

    Completable deletePostFav(String name);

    Observable<PostFavEntity> getPostFav(int id);

    List<PostFavEntity> getPostFavIfPresent(String title);

}
