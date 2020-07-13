package com.homie.psychq.main.epoxysetup.feeds_articles_categories.controllers;

import android.util.Log;
import android.view.View;

import com.airbnb.epoxy.EpoxyModel;
import com.airbnb.epoxy.OnModelClickListener;
import com.airbnb.epoxy.Typed4EpoxyController;
import com.homie.psychq.BaseApplication;
import com.homie.psychq.R;
import com.homie.psychq.main.epoxysetup.feeds_articles_categories.holders.feeds.AdViewHolder;
import com.homie.psychq.main.epoxysetup.feeds_articles_categories.holders.feeds.FeedsPostHolder;
import com.homie.psychq.main.epoxysetup.feeds_articles_categories.models.categories.HeaderCatModel_;
import com.homie.psychq.main.epoxysetup.feeds_articles_categories.models.feeds.AdViewModel_;
import com.homie.psychq.main.epoxysetup.feeds_articles_categories.models.feeds.FeedsPostModel_;
import com.homie.psychq.main.epoxysetup.feeds_articles_categories.models.feeds.LoadingModel_;
import com.homie.psychq.main.epoxysetup.feeds_articles_categories.models.feeds.SubscriptionModel_;
import com.homie.psychq.main.models.feeds.PsychPhoto;
import com.homie.psychq.room2.DatabaseTransactions;
import com.homie.psychq.utils.SharedPreferences;

import java.util.HashMap;
import java.util.List;



/*
 * Responsible for feeding data (categories) to EpoxyRv
 * Handles two scenarios
 * -When user taps on a category from categories section
 * -When user taps on the category from post or tags
 * */

public class CategoryController extends Typed4EpoxyController<List<PsychPhoto>,Boolean,HashMap<String,String>,HashMap<String,Integer>> {

    public static final int ABOUT_SHOW = 2;

    private SharedPreferences sharedPreferences;
    private DatabaseTransactions databaseTransactions;


    public CategoryController(AdapterCallbacks callbacks, SharedPreferences sharedPreferences, DatabaseTransactions databaseTransactions) {
        this.callbacks=callbacks;
        this.sharedPreferences=sharedPreferences;
        this.databaseTransactions = databaseTransactions;

    }



    /*Interface for getting clicks across to activities/fragments*/
    public AdapterCallbacks callbacks;
    public interface AdapterCallbacks {
        /*Add CallBacks for every viewtype/nested category/ads*/
        void onPostClick(FeedsPostModel_ model, int position, FeedsPostHolder holder, View clickedView);
        void onSubscribeClick();

    }




    @Override
    protected void buildModels(List<PsychPhoto> posts, Boolean isLoadingMore, HashMap<String,String> descriptionAndTitles, HashMap<String,Integer> firstItemIdPlusPostsCount) {


        /*For each entry in posts, create a model of post
        * Plus
        * -On first item we show header*/
        for(PsychPhoto post : posts){

            int id= post.getCustom_ordering();

            //this will display about the category section in epoxyRv
            if(firstItemIdPlusPostsCount != null){

                if( id == firstItemIdPlusPostsCount.get("firstItemId")){

                    if(firstItemIdPlusPostsCount.get("about") == ABOUT_SHOW){
                        //if about item in hashmap has value of 2, we show the about for category, otherwise for tags

                        add(new HeaderCatModel_()
                                .id("CategoryHeader")
                                .category_title(post.getCategory())
                                .cat_description(descriptionAndTitles.get("description"))
                                .about("About The Category")
                                .posts_count(firstItemIdPlusPostsCount.get("postsCount"))
                                .spanSizeOverride(OneSpanSIze));

                    }else {
                        //About tv for tags
                        add(new HeaderCatModel_()
                                .id("CategoryHeader")
                                .category_title(descriptionAndTitles.get("tagTitle"))
                                .cat_description(descriptionAndTitles.get("description"))
                                .about("Search Results For Tag")
                                .posts_count(firstItemIdPlusPostsCount.get("postsCount"))

                                .spanSizeOverride(OneSpanSIze));

                    }


                }

            }

            add(new FeedsPostModel_()
                            .id(post.getId())
                            .post(post)
//                        .description(photo.getDescription())
                            .clickListener(PostOnModelClickListener)
                            .spanSizeOverride(OneSpanSIze)
                    .databaseTransactions(databaseTransactions)
            );

        }

        if(!isLoadingMore && !sharedPreferences.getBooleanPref(BaseApplication.get().getString(R.string.isSubscribed), false)){
            //if we get loading Not more and isSubscribed false, we show subscription layout
            add(new SubscriptionModel_()
                    .id("SubscriptionMessage")
                    .SubscribeClickLIstener(SubscribeClickListener)
                    .spanSizeOverride(OneSpanSIze)
            );

        }else if(isLoadingMore){

            add(new LoadingModel_()
                    .id("Loading")
                    .sharedPreferences(sharedPreferences)
                    .spanSizeOverride(OneSpanSIze)
            );
        }

    }

    @Override
    public void setData(List<PsychPhoto> posts, Boolean isLoadingMore, HashMap<String,String> descriptionAndTitles,HashMap<String,Integer> firstItemIdPlusPostsCount) {
        super.setData(posts, isLoadingMore, descriptionAndTitles,firstItemIdPlusPostsCount);
    }






    /*
    * Click Listeners for each type of model
    * */
    OnModelClickListener<FeedsPostModel_, FeedsPostHolder> PostOnModelClickListener=new OnModelClickListener<FeedsPostModel_, FeedsPostHolder>() {
        @Override
        public void onClick(FeedsPostModel_ model, FeedsPostHolder parentView, View clickedView, int position) {
            //customModelClickListener.onCategoryClick();
            Log.d("TempController", "onClick: PostClick");
            callbacks.onPostClick(model,position,parentView,clickedView);
        }
    };

    OnModelClickListener<AdViewModel_, AdViewHolder> AdViewTestClickListener =new OnModelClickListener<AdViewModel_, AdViewHolder>() {
        @Override
        public void onClick(AdViewModel_ model, AdViewHolder parentView, View clickedView, int position) {
            //customModelClickListener.onCategoryClick();
            Log.d("TempController", "onClick: AdView : "+position);


        }
    };

    EpoxyModel.SpanSizeOverrideCallback OneSpanSIze=new EpoxyModel.SpanSizeOverrideCallback() {
        @Override
        public int getSpanSize(int totalSpanCount, int position, int itemCount) {
            //totalSpanCount will give us 1 span size
            return totalSpanCount;
        }
    };

    View.OnClickListener SubscribeClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            callbacks.onSubscribeClick();
        }
    };

}
