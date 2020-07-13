package com.homie.psychq.main.favourites;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.homie.psychq.R;
import com.homie.psychq.main.adapters.FavouritesAdapterArticles;
import com.homie.psychq.main.adapters.FavouritesAdapterPosts;
import com.homie.psychq.main.ui.crashcourses.ArticlePostClickActivity;
import com.homie.psychq.main.ui.PsychPostAfterClick2;
import com.homie.psychq.room2.ArticleFavEntity;
import com.homie.psychq.room2.DatabaseTransactions;
import com.homie.psychq.room2.PostFavEntity;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;

/*Favourites are either posts or articles, when either is clicked we check it here and populate recyclerView
* with that favourite type accordingly*/

public class FavouriteAfterClick extends AppCompatActivity implements FavouritesAdapterPosts.OnPostClickedListener, FavouritesAdapterArticles.OnArticleFavClickedListener {
    private static final String TAG = "FavouriteAfterClick";


    private DatabaseTransactions databaseTransactions;
    private RecyclerView recyclerView;
    private TextView count_tv;

    private LinearLayoutManager layoutManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.favourite_after_click_activity);

        //intent containing count of items
        //intent containing info about entity
        //fetch all items of related entities and set in recyclerview
        databaseTransactions=new DatabaseTransactions(this);
        recyclerView=findViewById(R.id.fav_recyclerView);
        count_tv=findViewById(R.id.count_fav);

        Intent intent=getIntent();

        if(intent.getStringExtra("type").equals("postfav")){
            //Posts Favourites

            loadDataForPostsFav();

        }

        if(intent.getStringExtra("type").equals("articlefav")){
            //Articles Favourites

            loadDataForArticlesFav();

        }


    }

    /*Loads users bookmarked articles*/
    private void loadDataForArticlesFav() {
        FavouritesAdapterArticles adapter=new FavouritesAdapterArticles(this,this);
        layoutManager=new LinearLayoutManager(FavouriteAfterClick.this);
        final StaggeredGridLayoutManager staggeredGridLayoutManager=new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        databaseTransactions.getAllArticlesFav()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<ArticleFavEntity>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(List<ArticleFavEntity> articleFavEntities) {
                        if(articleFavEntities != null){

                            String count = String.valueOf(articleFavEntities.size()) + " Posts";
                            count_tv.setText(count);

                            adapter.addPhotos(articleFavEntities);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                        Log.d(TAG, "onError: Error Fetching Articles From Database : "+e.getMessage());

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    /*Loads users bookmarked posts*/
    private void loadDataForPostsFav() {
        FavouritesAdapterPosts adapter=new FavouritesAdapterPosts(this,this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);

        databaseTransactions.getAllPostFav()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<PostFavEntity>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(List<PostFavEntity> postFavEntities) {
                        if(postFavEntities != null){

                            String count = String.valueOf(postFavEntities.size()) + " Posts";
                            count_tv.setText(count);

                            adapter.addPhotos(postFavEntities);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                        Log.d(TAG, "onError: Error Fetching Posts From Database : "+e.getMessage());

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @Override
    public void photoClicked(PostFavEntity postFavEntity, ImageView imageView, int position) {

        Intent intent = new Intent(FavouriteAfterClick.this, PsychPostAfterClick2.class);
        intent.putExtra("post_id", postFavEntity.getPost_id());
        intent.putExtra("category", postFavEntity.getCategory());
        intent.putExtra("sub_category", postFavEntity.getSubCategory());

        startActivity(intent);
    }

    @Override
    public void articleClicked(ArticleFavEntity articleFavEntity, CardView imageView, int position) {

        Intent intent = new Intent(FavouriteAfterClick.this, ArticlePostClickActivity.class);
        intent.putExtra("articleId",articleFavEntity.getArticleId());
        intent.putExtra("totalArticlesInCourse",1);
        intent.putExtra("fromFavourites", "True");

        startActivity(intent);
    }
}
