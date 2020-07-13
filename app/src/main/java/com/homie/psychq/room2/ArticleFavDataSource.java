package com.homie.psychq.room2;



import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Observable;


/*A layer of abstraction on our DAO*/

public interface ArticleFavDataSource {


    Observable<List<ArticleFavEntity>> getAllArticlesFav();

    Completable addArticleFav(ArticleFavEntity articleFavEntity);

    Completable updateArticleFav(ArticleFavEntity articleFavEntity);

    Completable deleteArticleFav(String name);

    Observable<ArticleFavEntity> getArticleFav(int id);

    List<ArticleFavEntity> getArticleFavIfPresent(String title);


}
