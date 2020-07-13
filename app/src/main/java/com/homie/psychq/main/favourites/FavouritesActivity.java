package com.homie.psychq.main.favourites;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.homie.psychq.BaseApplication;
import com.homie.psychq.R;
import com.homie.psychq.room2.ArticleFavEntity;
import com.homie.psychq.utils.SharedPreferences;
import com.homie.psychq.room2.DatabaseTransactions;
import com.homie.psychq.room2.PostFavEntity;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import xyz.schwaab.avvylib.AvatarView;



public class FavouritesActivity extends AppCompatActivity {
    private static final String TAG = "FavouritesActivity";

        //post card
    private CardView post_card,articles_card;
    private ImageView v1,v2,v4,v3,v1Ar,v2Ar,v3Ar,v4Ar;


    private DatabaseTransactions databaseTransactions;


    AvatarView profile_pic;

    SharedPreferences sharedPreferences;

    private TextView username_tv;
    private RelativeLayout relativeLayout;
    private TextView favourites_count,no_saved,no_saved2;


    /*
    *
    * Here we create two card for post and articles fav and when it is tapped, items will be fetched
    * from room and populated in recycler view
    * */

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourites);


        sharedPreferences=new SharedPreferences(this);
        databaseTransactions=new DatabaseTransactions(this);

        initUiComponents();
        retrieveUserInfoFromPrefs();

        profile_pic.setAnimating(true);

        loadData();


        //Setting two cards width : One card represents articles - other posts
        setCardsWidth();



        post_card.setOnClickListener(PostCardCL);
        articles_card.setOnClickListener(ArticlesCardCL);

    }

    private void setCardsWidth() {
        post_card.requestLayout();
        articles_card.requestLayout();
        int screen_width=700;
        screen_width=getResources().getDisplayMetrics().widthPixels;

        post_card.getLayoutParams().width=screen_width/2;
        articles_card.getLayoutParams().width=screen_width/2;
    }

    private void initUiComponents() {
        post_card=findViewById(R.id.post_card);
        articles_card=findViewById(R.id.articles_card);
        v1=findViewById(R.id.v1);
        v1Ar=findViewById(R.id.v1Ar);
        v2=findViewById(R.id.v2);
        v2Ar=findViewById(R.id.v2Ar);
        v3=findViewById(R.id.v3);
        v3Ar=findViewById(R.id.v3Ar);
        v4=findViewById(R.id.v4);
        v4Ar=findViewById(R.id.v4Ar);
        relativeLayout=findViewById(R.id.card_tv_layout);
        favourites_count=findViewById(R.id.favourites_count);
        no_saved=findViewById(R.id.no_saved);
        no_saved2=findViewById(R.id.no_saved2);

        profile_pic=findViewById(R.id.profile_pic);
        username_tv=findViewById(R.id.username_profileTv);
    }


    /*Displays username,*/
    private void retrieveUserInfoFromPrefs() {

      String userInfoString =   sharedPreferences.getString(getString(R.string.user_info_map_pref),"No Info Found");
        Map<String,String> userInfomap= sharedPreferences.getMapFromString(userInfoString);

        if(userInfomap != null){
           String username = userInfomap.get("Username");
            String email = userInfomap.get("Email");
            String uid = userInfomap.get("Uid");
            String photoUrl = userInfomap.get("ProfilePic");



            if(username == null && email != null){
               List<String> modified = Arrays.asList(email.split("@"));
               username = modified.get(0);

           }
            if(photoUrl == null){
               photoUrl = "https://picsum.photos/id/237/200/300";
            }

            username_tv.setText(username);

            Glide.with(FavouritesActivity.this).load(photoUrl).into(profile_pic);

            Log.d(TAG, "retrieveUserInfoFromPrefs: Info "+username + " : "+email + " : "+uid + " : "+photoUrl);
        }

    }


    /*Checks the database for any users bookmarks of posts/articles and displays it two cards of 4 sectioned
    * images*/
    private void loadData() {

        databaseTransactions.getAllPostFav()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<PostFavEntity>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(List<PostFavEntity> postFavEntities) {

                        if(postFavEntities != null){

                            String count__ = "All the bookmarked Posts and Topics can be found here";
                            if(postFavEntities.size() > 0){

                                relativeLayout.setVisibility(View.VISIBLE);
                                no_saved.setVisibility(View.GONE);
                                no_saved2.setVisibility(View.GONE);


                                int list_size = postFavEntities.size();


                                if(list_size > 4){
                                    list_size = 4;
                                }
                                String count = String.valueOf(postFavEntities.size());

                                favourites_count.setText(count__);


                                switch(list_size){

                                    case 1:
                                        Glide.with(BaseApplication.get()).load(postFavEntities.get(0).getThumbnail()).into(v1);
                                        break;

                                    case 2:

                                        Glide.with(BaseApplication.get()).load(postFavEntities.get(0).getThumbnail()).into(v1);
                                        Glide.with(BaseApplication.get()).load(postFavEntities.get(1).getThumbnail()).into(v2);


                                        break;

                                    case 3:

                                        Glide.with(BaseApplication.get()).load(postFavEntities.get(0).getThumbnail()).into(v1);
                                        Glide.with(BaseApplication.get()).load(postFavEntities.get(1).getThumbnail()).into(v2);
                                        Glide.with(BaseApplication.get()).load(postFavEntities.get(2).getThumbnail()).into(v3);


                                        break;

                                    case 4:

                                        Glide.with(BaseApplication.get()).load(postFavEntities.get(0).getThumbnail()).into(v1);
                                        Glide.with(BaseApplication.get()).load(postFavEntities.get(1).getThumbnail()).into(v2);
                                        Glide.with(BaseApplication.get()).load(postFavEntities.get(2).getThumbnail()).into(v3);
                                        Glide.with(BaseApplication.get()).load(postFavEntities.get(3).getThumbnail()).into(v4);

                                        break;


                                }

                            }
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "onError: Error While Fetching Posts From Database : "+e.getMessage());

                    }

                    @Override
                    public void onComplete() {

                    }
                });

        /*
        * Check if any articles are saved in database
        *
        * */


        databaseTransactions.getAllArticlesFav()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<ArticleFavEntity>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(List<ArticleFavEntity> articleFavEntities) {

                        if(articleFavEntities != null){


                            if(articleFavEntities.size() > 0){

                                relativeLayout.setVisibility(View.VISIBLE);
                                no_saved.setVisibility(View.GONE);
                                no_saved2.setVisibility(View.GONE);


                                int list_size = articleFavEntities.size();


                                if(list_size > 4){
                                    list_size = 4;
                                }
                                String count = String.valueOf(articleFavEntities.size());
                                String count__ = count + " Favourites";
                                favourites_count.setText(count__);


                                switch(list_size){

                                    case 1:
                                        Glide.with(BaseApplication.get()).load(articleFavEntities.get(0).getThumbnail()).into(v1Ar);
                                        break;

                                    case 2:

                                        Glide.with(BaseApplication.get()).load(articleFavEntities.get(0).getThumbnail()).into(v1Ar);
                                        Glide.with(BaseApplication.get()).load(articleFavEntities.get(1).getThumbnail()).into(v2Ar);


                                        break;

                                    case 3:

                                        Glide.with(BaseApplication.get()).load(articleFavEntities.get(0).getThumbnail()).into(v1Ar);
                                        Glide.with(BaseApplication.get()).load(articleFavEntities.get(1).getThumbnail()).into(v2Ar);
                                        Glide.with(BaseApplication.get()).load(articleFavEntities.get(2).getThumbnail()).into(v3Ar);


                                        break;

                                    case 4:

                                        Glide.with(BaseApplication.get()).load(articleFavEntities.get(0).getThumbnail()).into(v1Ar);
                                        Glide.with(BaseApplication.get()).load(articleFavEntities.get(1).getThumbnail()).into(v2Ar);
                                        Glide.with(BaseApplication.get()).load(articleFavEntities.get(2).getThumbnail()).into(v3Ar);
                                        Glide.with(BaseApplication.get()).load(articleFavEntities.get(3).getThumbnail()).into(v4Ar);

                                        break;


                                }

                            }
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                        Log.d(TAG, "onError: Error While Fetching Articles From Database : "+e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }




    /*Listeners*/
    View.OnClickListener PostCardCL = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            startActivity(new Intent(FavouritesActivity.this,FavouriteAfterClick.class)
                    .putExtra("type","postfav")
            );
        }
    };
    View.OnClickListener ArticlesCardCL = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            startActivity(new Intent(FavouritesActivity.this,FavouriteAfterClick.class)
                    .putExtra("type","articlefav")
            );
        }
    };


}
