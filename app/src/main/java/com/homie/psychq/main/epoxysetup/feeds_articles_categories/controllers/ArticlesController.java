package com.homie.psychq.main.epoxysetup.feeds_articles_categories.controllers;

import android.util.Log;
import android.view.View;

import androidx.cardview.widget.CardView;

import com.airbnb.epoxy.EpoxyModel;
import com.airbnb.epoxy.OnModelClickListener;
import com.airbnb.epoxy.Typed3EpoxyController;
import com.homie.psychq.BaseApplication;
import com.homie.psychq.R;
import com.homie.psychq.main.epoxysetup.feeds_articles_categories.holders.crashcourses.ArticleHolder;
import com.homie.psychq.main.epoxysetup.feeds_articles_categories.models.crashcourses.ArticleModel_;
import com.homie.psychq.main.epoxysetup.feeds_articles_categories.models.crashcourses.ArticlesContinuationNoteModel_;
import com.homie.psychq.main.epoxysetup.feeds_articles_categories.models.crashcourses.ArticlesHeaderModel_;
import com.homie.psychq.main.epoxysetup.feeds_articles_categories.models.feeds.LoadingModel_;
import com.homie.psychq.main.epoxysetup.feeds_articles_categories.models.feeds.SubscriptionModel_;
import com.homie.psychq.main.models.crashcourses.Article;
import com.homie.psychq.utils.SharedPreferences;

import java.util.List;


/*
* Responsible for feeding data (articles) to EpoxyRv
* */

public class ArticlesController extends Typed3EpoxyController<List<Article>,Boolean,Boolean> {

    private static final String TAG = "ArticlesController";



    public SharedPreferences sharedPreferences;
    public String courseDescription,courseName,author,thumb;

    public ArticlesController(OnArticleClickedListener callbacks,
                              SharedPreferences sharedPreferences,
                              String courseDescription,
                              String courseName,
                              String author,
                              String thumb) {
        this.callbacks=callbacks;
        this.sharedPreferences= sharedPreferences;

        this.courseDescription= courseDescription;
        this.courseName = courseName;
        this.author= author;
        this.thumb = thumb;
    }



    /*Interface for getting clicks across to activities/fragments*/
    public OnArticleClickedListener callbacks;
    public interface OnArticleClickedListener {
        void articleClicked(ArticleModel_ articleModel_, int position, CardView clickedView);
        void onSubscribeClicked();
    }




    @Override
    protected void buildModels(List<Article> list, Boolean isLoadingMore, Boolean isCourseFinished) {

        //whatever @EpoxyAttribute we set in ModelClass (PostModel), we can send the data from here to that variable
        //through generated code after rebuild
        //this list will be submitted to gridCarouselModel

        /*Firstly add a header*/
        add(new ArticlesHeaderModel_()
                    .id("articlesHeader")
                    .courseDescription(courseDescription)
                    .courseThumb(thumb)
                    .title(courseName)
                    .author(author)
                    );



        /*For each entry in the list create article model*/
        for(Article photo : list){

            int id = photo.getUniversalCount();
            add(new ArticleModel_()
                    .id(id)
                    .article(photo)
                    .layoutClickListener(ArticleClickListener2)
                    .sharedPreferences(sharedPreferences)
            )
            ;

        }


        if(!isLoadingMore && !isCourseFinished && sharedPreferences.getBooleanPref(BaseApplication.get().getString(R.string.isSubscribed),false)){
            //If the user is subscribed, course is continued and he reaches the end of articles
            //this will execute and create a ContinuationNoteModel
            add(new ArticlesContinuationNoteModel_()
            .id("ContinuationNoteModel")

            );

        }else if(!isLoadingMore && !sharedPreferences.getBooleanPref(BaseApplication.get().getString(R.string.isSubscribed), false)){
            //If the user runs out of quota, and is not subscribed
            //we will show him subscription model, from which he can go to subscription activity
            add(new SubscriptionModel_()
                    .id("SubscriptionMessage")
                    .SubscribeClickLIstener(SubscribeClickListener)
                    .spanSizeOverride(OneSpanSIze)
            );

        }else if(isLoadingMore){
            //If none of the above, just show good old loading model
            add(new LoadingModel_()
                    .id("Loading")
                    .sharedPreferences(sharedPreferences)

            );
        }

    }





    /*
    * Click Listeners For Each Type Of Model
    * */
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

    OnModelClickListener<ArticleModel_, ArticleHolder> ArticleClickListener2=new OnModelClickListener<ArticleModel_, ArticleHolder>() {
        @Override
        public void onClick(ArticleModel_ model, ArticleHolder parentView, View clickedView, int position) {
            //customModelClickListener.onCategoryClick();
            Log.d("TempController", "onClick: PostClick");
            callbacks.articleClicked(model,position,parentView.layout);

        }
    };




}
