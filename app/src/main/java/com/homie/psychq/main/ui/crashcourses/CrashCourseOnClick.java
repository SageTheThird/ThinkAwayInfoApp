package com.homie.psychq.main.ui.crashcourses;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.airbnb.epoxy.EpoxyRecyclerView;
import com.airbnb.lottie.LottieAnimationView;
import com.homie.psychq.R;
import com.homie.psychq.di.psych.DaggerPsychComponent;
import com.homie.psychq.di.psych.PsychComponent;
import com.homie.psychq.main.api.main.PsychApi;
import com.homie.psychq.main.epoxysetup.feeds_articles_categories.controllers.ArticlesController;
import com.homie.psychq.main.epoxysetup.feeds_articles_categories.models.crashcourses.ArticleModel_;
import com.homie.psychq.main.models.crashcourses.Article;
import com.homie.psychq.main.models.crashcourses.ResultsArticles;
import com.homie.psychq.subscription.SubscriptionActivity;
import com.homie.psychq.utils.CookiesHelper;
import com.homie.psychq.utils.SharedPreferences;

import java.util.ArrayList;
import java.util.List;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;



/*
* Activity after a crash course is clicked
* */
public class CrashCourseOnClick extends AppCompatActivity implements ArticlesController.OnArticleClickedListener {

    private static final String TAG = "CrashCourseOnClick";

    PsychApi psychApi;
    PsychComponent component;
    private ArticlesController controller;
    private List<Article> articles_list;
    private GridLayoutManager layoutManager;
    private EpoxyRecyclerView recyclerView;
    private String courseName,courseDescription,author,thumb,title;
    private int pageNumber = 1;
    private boolean loading = false;
    private final int VISIBLE_THRESHOLD = 1;
    private int lastVisibleItem;
    private int totalItemCount;
    private int totalArticlesInCourse;
    private CompositeDisposable mDisposibles = new CompositeDisposable();
    private CookiesHelper cookiesHelper;
    private int totalpages;
    private boolean isCourseFinished = false;
    private LottieAnimationView lottieAnimationView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.crashcourse_onclick_activity);

        initUiComponents();
        handleIntent();
        startAnimation();
        initComponent();
        setupRv();
        loadData();


    }

    /*Gets articles for the crash course clicked and sets it to recyclerView*/
    private void loadData() {
        psychApi.getArticleByCrashCourseAndOrdering(courseName,"course_count",pageNumber)
                .toObservable()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ResultsArticles>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                        mDisposibles.add(d);

                    }

                    @Override
                    public void onNext(ResultsArticles resultsArticles) {
                        if(resultsArticles != null){

                            totalpages = resultsArticles.getCount() / 10;
                            if(resultsArticles.getCount() % 10 > 0){
                                totalpages++;
                            }

                            totalArticlesInCourse = resultsArticles.getCount();

                            articles_list.addAll(resultsArticles.getArticles());
                            controller.setData(articles_list,true,isCourseFinished);
                        }

                        stopAnimation();
                    }

                    @Override
                    public void onError(Throwable e) {

                        cookiesHelper.showCookie("Error Loading Topics : "+e.getMessage(),
                                "Please check your internet and try refreshing"
                                ,null,
                                null);

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    /*Extracts data for the header from intent*/
    private void handleIntent() {
        Intent intent = getIntent();
        courseName = intent.getStringExtra("courseName");
        author = intent.getStringExtra("author");
        thumb = intent.getStringExtra("thumb");
        title = intent.getStringExtra("title");
        isCourseFinished = intent.getBooleanExtra("is_finished", false);
        courseDescription = intent.getStringExtra("courseDescription");

    }


    private void initUiComponents() {
        recyclerView=findViewById(R.id.articles_recyclerView);
        lottieAnimationView = findViewById(R.id.lottieAnimationView);
        cookiesHelper = new CookiesHelper(this);
    }

    /*Starts Lottie Animation while loading*/
    private void startAnimation() {

        lottieAnimationView.setVisibility(View.VISIBLE);
        String animationName="feeds_loading.json";
        lottieAnimationView.setAnimation(animationName);
        lottieAnimationView.loop(true);
        lottieAnimationView.playAnimation();

    }

    /*Stops Lottie Animation when loading is finished*/
    private void stopAnimation(){
        if(lottieAnimationView.getVisibility() == View.VISIBLE){

            lottieAnimationView.cancelAnimation();
            lottieAnimationView.setVisibility(View.GONE);
        }

    }

    /*Inits API's*/
    private void initComponent() {

        component= DaggerPsychComponent.builder()
                .build();
        psychApi=component.providePsychApi();

    }


    /*Setups RecyclerView for articles and handles all the logic for scrolling
    * and loading further articles {paginated}*/
    private void setupRv() {

        articles_list=new ArrayList<>();

//        layoutManager=new LinearLayoutManager(CrashCourseOnClick.this);
        final StaggeredGridLayoutManager staggeredGridLayoutManager=new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);

        SharedPreferences sharedPreferences = new SharedPreferences(this);
        controller=new ArticlesController(this, sharedPreferences,courseDescription,title,author,thumb);
        layoutManager=new GridLayoutManager(this,1);
//        LinearLayoutManager layoutManager=new LinearLayoutManager(getActivity());

        controller.setSpanCount(1);
        layoutManager.setSpanSizeLookup(controller.getSpanSizeLookup());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(controller.getAdapter());

        recyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull final RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
//                int[] lastVisibleItemPositions = staggeredGridLayoutManager.findLastVisibleItemPositions(null);
//                totalItemCount= staggeredGridLayoutManager.getItemCount();
//                lastVisibleItem= getLastVisibleItem(lastVisibleItemPositions);

                totalItemCount=layoutManager.getItemCount();
                lastVisibleItem=layoutManager.findLastVisibleItemPosition();


                if (!loading && totalItemCount <= (lastVisibleItem + VISIBLE_THRESHOLD)) {

                    Log.d(TAG, "onScrolled: working");
                    loading = true;




                    if(pageNumber == 4 && !sharedPreferences.getBooleanPref(getString(R.string.isSubscribed),false)){
                        //uSer is not subscribed
                        controller.setData(articles_list,false, isCourseFinished);
                        return;
                    }

                    pageNumber++;

                    //loadingProgress.setVisibility(View.VISIBLE);

                    if(pageNumber <= totalpages){


                        psychApi.getArticleByCrashCourseAndOrdering(courseName,"course_count",pageNumber)
                                .toObservable()
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(new Observer<ResultsArticles>() {
                                    @Override
                                    public void onSubscribe(Disposable d) {

                                    }

                                    @Override
                                    public void onNext(ResultsArticles resultsArticles) {
                                        if(resultsArticles != null){
                                            Log.d(TAG, "onNext: "+resultsArticles.toString());
                                            articles_list.addAll(resultsArticles.getArticles());


                                            if(pageNumber < totalpages){
                                                //when pageNumber is less than last page isLoadingMore will be true
                                                controller.setData(articles_list,true, isCourseFinished);
                                            }else {
                                                //when pageNumber equals last page, isLoadingMore will be false
                                                controller.setData(articles_list,false,isCourseFinished);
                                            }

                                        }
                                        loading=false;
                                    }

                                    @Override
                                    public void onError(Throwable e) {
                                        cookiesHelper.showCookie("Error Loading Topics : "+e.getMessage(),
                                                "Please check your internet and try refreshing"
                                                ,null,
                                                null);
                                    }

                                    @Override
                                    public void onComplete() {

                                    }
                                });

                    }




                    //paginator.onNext(pageNumber);


                }
            }
        });


    }


    @Override
    protected void onResume() {
        super.onResume();

    }


    private void vibrate(){

            Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
            vibrator.vibrate(50);

    }

    @Override
    public void articleClicked(ArticleModel_ articleModel_, int position, CardView clickedView) {

        Intent intent = new Intent(CrashCourseOnClick.this,ArticlePostClickActivity.class);
        intent.putExtra("articleId", articleModel_.article().getId());
        intent.putExtra("totalArticlesInCourse", totalArticlesInCourse);
        intent.putExtra("isCourseFinished",isCourseFinished);
        startActivity(intent);
    }

    @Override
    public void onSubscribeClicked() {
        Intent intent = new Intent(CrashCourseOnClick.this, SubscriptionActivity.class);
        startActivity(intent);
    }
}
