package com.homie.psychq.main.epoxysetup.feeds_articles_categories.controllers;

import android.util.Log;
import android.view.View;

import androidx.cardview.widget.CardView;

import com.airbnb.epoxy.EpoxyModel;
import com.airbnb.epoxy.OnModelClickListener;
import com.airbnb.epoxy.Typed4EpoxyController;
import com.homie.psychq.BaseApplication;
import com.homie.psychq.R;
import com.homie.psychq.main.epoxysetup.feeds_articles_categories.holders.feeds.AdViewHolder;
import com.homie.psychq.main.epoxysetup.feeds_articles_categories.holders.crashcourses.ArticleHolder;
import com.homie.psychq.main.epoxysetup.feeds_articles_categories.holders.feeds.FeedsPostHolder;
import com.homie.psychq.main.epoxysetup.feeds_articles_categories.holders.feeds.TagsHolder;
import com.homie.psychq.main.epoxysetup.feeds_articles_categories.holders.categories.PsychCategoryHolder;
import com.homie.psychq.main.epoxysetup.feeds_articles_categories.models.categories.PsychCategoryModel_;
import com.homie.psychq.main.epoxysetup.feeds_articles_categories.models.crashcourses.ArticleModel_;
import com.homie.psychq.main.epoxysetup.feeds_articles_categories.models.feeds.AdViewModel_;
import com.homie.psychq.main.epoxysetup.feeds_articles_categories.models.feeds.FeedsHeaderModel_;
import com.homie.psychq.main.epoxysetup.feeds_articles_categories.models.feeds.FeedsPostModel_;
import com.homie.psychq.main.epoxysetup.feeds_articles_categories.models.feeds.GridCarouselModelModel_;
import com.homie.psychq.main.epoxysetup.feeds_articles_categories.models.feeds.HeaderModel_;
import com.homie.psychq.main.epoxysetup.feeds_articles_categories.models.feeds.LinearSingleTagModelModel_;
import com.homie.psychq.main.epoxysetup.feeds_articles_categories.models.feeds.LoadingModel_;
import com.homie.psychq.main.epoxysetup.feeds_articles_categories.models.feeds.PageNumModel_;
import com.homie.psychq.main.epoxysetup.feeds_articles_categories.models.feeds.SubscriptionModel_;
import com.homie.psychq.main.epoxysetup.feeds_articles_categories.models.feeds.TagsModel_;
import com.homie.psychq.main.models.feeds.Announcement;
import com.homie.psychq.main.models.categories.CategoryFeatured;
import com.homie.psychq.main.models.feeds.PsychPhoto;

import com.homie.psychq.room2.DatabaseTransactions;
import com.homie.psychq.utils.SharedPreferences;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;



/*
 * Responsible for feeding data (posts, categories, tags) to EpoxyRv
 * Handles Three types of data
 * -Post
 * -Categories
 * -Tags
 * */

public class FeedsController extends Typed4EpoxyController<HashMap<Integer,List<?>>,Boolean,HashMap<Integer,Integer>,List<Announcement>> {

    private static final String TAG = "FeedsController";


    /*Indexes of data in HashMap*/
    public static final int POSTS_INDEX=1;
    public static final int CATEGORIES_INDEX=2;
    public static final int TAGS_INDEX=3;
    public static final int TOTALPAGES_INDEX = 4;


    public int CATEGORIES_POSTIION = 17;//17
    public int TAGS_POSITION = 37;


    private SharedPreferences sharedPreferences;
    private DatabaseTransactions databaseTransactions;


    public FeedsController(AdapterCallbacks callbacks, SharedPreferences sharedPreferences, DatabaseTransactions databaseTransactions) {
        this.callbacks=callbacks;

        this.sharedPreferences = sharedPreferences;
        this.databaseTransactions = databaseTransactions;
    }


    /*Interface for getting clicks across to activities/fragments*/
    public AdapterCallbacks callbacks;
    public interface AdapterCallbacks {
        /*Add CallBacks for every viewtype/nested category/ads*/
        void onCategoryClick(PsychCategoryModel_ model, int position, View clickedView);
        void onPostClick(FeedsPostModel_ model, int position, FeedsPostHolder holder, View clickedView);
        void onVectorClick(FeedsPostModel_ model, int position, FeedsPostHolder holder, View clickedView);
        void onTagClick(TagsModel_ model, int position, View clickedView);
        void articleClicked(ArticleModel_ articleModel_, int position, CardView clickedView);
        void onSubscribeClicked();

    }






    @Override
    protected void buildModels(HashMap<Integer,List<?>> hashMapofLists, Boolean isLoadingMore, HashMap<Integer, Integer> pageNumberHash,List<Announcement> announcements) {

        //Main HashMap have three lists of posts, categories and tags with that index respectively

        //get posts from HashMap
        List<PsychPhoto> posts_list= (List<PsychPhoto>) hashMapofLists.get(POSTS_INDEX);
        //above list is used for getting the pageNumber on its first index for pageNum Model







        if(hashMapofLists.get(POSTS_INDEX) != null ){
            //if posts are not null populate it

            /*For each entry in posts_list, build models*/
            for(PsychPhoto photo : posts_list){

                int id = photo.getCustom_ordering();

                /*First Index goes to header*/
                if (getPostIndex(posts_list,photo) == 0) {
                    add(new FeedsHeaderModel_()
                            .id("FeedsHeader")
                            .announcements(announcements)
                            .sharedPreferences(sharedPreferences)
                            .spanSizeOverride(OneSpanSIze));

                }

                if (getPostIndex(posts_list,photo) == 5) {
                    /*At Position  specified above it will show the message banner*/
                    add(new HeaderModel_()
                            .id("carouselTag22").name(BaseApplication.get().getString(R.string.feedsNoteLocal))
                            .textSize(16)
                            .spanSizeOverride(OneSpanSIze));

                }

                if(pageNumberHash != null){

                    //Logic for showing pageNumbers
                    if(pageNumberHash.containsKey(id)){


                        List<Integer> totalPagesList= (List<Integer>) hashMapofLists.get(TOTALPAGES_INDEX);
                        
                        int id__ = getRandomNumber();
                        add(new PageNumModel_()
                                .id(id__)
                                .pageNumber(pageNumberHash.get(id))
                                .totalPages(totalPagesList != null ? totalPagesList.get(0) : 180)
                                .spanSizeOverride(OneSpanSIze));

                    }

                }












                /*CATEGORIES*/
                if(hashMapofLists.get(CATEGORIES_INDEX) != null ){
                    //if Categories are not null populate it

                    if(id % CATEGORIES_POSTIION == 0 && id % 2 !=0){


                        //LOGIC : Categories will be added to recyclerView at position divisible by 17
                        // while also eliminating even numbers

                        List<CategoryFeatured> cat_list= (List<CategoryFeatured>) hashMapofLists.get(CATEGORIES_INDEX);
                        List<PsychCategoryModel_> catModelsList=new ArrayList<>();

                        add(new HeaderModel_()
                                .id("carouselTag").name("Some categories you might want to explore")
                                .textSize(30)
                                .spanSizeOverride(OneSpanSIze));


                        for(int i=0;i<cat_list.size();i++){


                            String catId= "FeaturedCats"+cat_list.get(i).getId();

                            //nested Categories Recyclerview through carousel
                            //adding all posts to GridCarousel for nested horizantal recyclerview

                            catModelsList.add(new PsychCategoryModel_()
                                    .id(catId)
                                    .category(cat_list.get(i))
                                    .clickListener(CatOnModelClickListener));

                        }

                        String gridCarouselId="Carousel"+photo.getId();

                        add(new GridCarouselModelModel_()
                                .id(gridCarouselId)
                                .models(catModelsList)
                                .spanSizeOverride(OneSpanSIze));

                    }
                }









                /*TAGS*/
                if(hashMapofLists.get(TAGS_INDEX) != null ){

                    //Linear Tags will be populated in recyclerView at positions divisible by 37
                    //also eliminating even numbers

                    if(id % TAGS_POSITION == 0 && id % 2 !=0){

                        List<TagsModel_> tagsModelsList=new ArrayList<>();
                        List<CharSequence> tags_list= (List<CharSequence>) hashMapofLists.get(TAGS_INDEX);

                        for(int i=1;i<tags_list.size();i++){
                            tagsModelsList.add(
                                    new TagsModel_()
                                            .id("tag"+i)
                                            .tag(tags_list.get(i))
                                            .clickListener(SingleTagClickListener)
                            );
                        }

                        add(new HeaderModel_()
                                .id("tagHeader").name("Explore more interesting content with these tags")
                                .textSize(25)
                                .spanSizeOverride(OneSpanSIze));



                        add(new LinearSingleTagModelModel_()
                                .id("TagsInASingleHolder"+id)
                                .models(tagsModelsList));

                    }


                }


                /*POSTS*/
                add(new FeedsPostModel_()
                        .id(photo.getId())
                        .post(photo)
//                        .description(photo.getDescription())
                        .clickListener(PostOnModelClickListener)
                        .spanSizeOverride(OneSpanSIze)
                        .databaseTransactions(databaseTransactions)
                        .vectorCircularIvClickListener(VectorClickListener)
                );


            }
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

    private int getPostIndex(List<PsychPhoto> list,PsychPhoto post) {
        return list.indexOf(post);
    }

    public int getRandomNumber(){
        int min = 100;
        int max = 8000;

        Random r = new Random();
        int i1 = r.nextInt(max - min + 1) + min;

        return i1;
    }

    @Override
    public void setData(HashMap<Integer,List<?>> posts, Boolean isLoadingMore, HashMap<Integer, Integer> pageNumberHash,List<Announcement> announcements) {
        super.setData(posts, isLoadingMore, pageNumberHash,announcements);
    }
















    //-----------------------------------------
    /*Click Listeners for each type of model*/
    //-----------------------------------------

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
            callbacks.onSubscribeClicked();
        }
    };

    OnModelClickListener<PsychCategoryModel_, PsychCategoryHolder> CatOnModelClickListener=new OnModelClickListener<PsychCategoryModel_, PsychCategoryHolder>() {
        @Override
        public void onClick(PsychCategoryModel_ model, PsychCategoryHolder parentView, View clickedView, int position) {
            //customModelClickListener.onCategoryClick();
            Log.d("TempController", "onClick: CategoryClick");
            callbacks.onCategoryClick(model,position,clickedView);
        }
    };

    OnModelClickListener<FeedsPostModel_, FeedsPostHolder> PostOnModelClickListener=new OnModelClickListener<FeedsPostModel_, FeedsPostHolder>() {
        @Override
        public void onClick(FeedsPostModel_ model, FeedsPostHolder parentView, View clickedView, int position) {
            //customModelClickListener.onCategoryClick();
            Log.d("TempController", "onClick: PostClick");
            callbacks.onPostClick(model,position,parentView,clickedView);
        }
    };

    OnModelClickListener<FeedsPostModel_, FeedsPostHolder> VectorClickListener=new OnModelClickListener<FeedsPostModel_, FeedsPostHolder>() {
        @Override
        public void onClick(FeedsPostModel_ model, FeedsPostHolder parentView, View clickedView, int position) {
            //customModelClickListener.onCategoryClick();
            Log.d("TempController", "onClick: PostClick");
            callbacks.onVectorClick(model,position,parentView,clickedView);
        }
    };

    OnModelClickListener<TagsModel_, TagsHolder> SingleTagClickListener =new OnModelClickListener<TagsModel_, TagsHolder>() {
        @Override
        public void onClick(TagsModel_ model, TagsHolder parentView, View clickedView, int position) {
            //customModelClickListener.onCategoryClick();
            Log.d("TempController", "onClick: SIngleCatClicked : "+position);
            callbacks.onTagClick(model,position,clickedView);


        }
    };

    OnModelClickListener<AdViewModel_, AdViewHolder> AdViewTestClickListener =new OnModelClickListener<AdViewModel_, AdViewHolder>() {
        @Override
        public void onClick(AdViewModel_ model, AdViewHolder parentView, View clickedView, int position) {
            //customModelClickListener.onCategoryClick();
            Log.d("TempController", "onClick: AdView : "+position);


        }
    };

    OnModelClickListener<ArticleModel_, ArticleHolder> ArticleClickListener2=new OnModelClickListener<ArticleModel_, ArticleHolder>() {
        @Override
        public void onClick(ArticleModel_ model, ArticleHolder parentView, View clickedView, int position) {
            //customModelClickListener.onCategoryClick();
            Log.d("TempController", "onClick: PostClick");
            callbacks.articleClicked(model,position,parentView.layout);

        }
    };


}
