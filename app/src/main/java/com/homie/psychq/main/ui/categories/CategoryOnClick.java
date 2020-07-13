package com.homie.psychq.main.ui.categories;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.epoxy.EpoxyRecyclerView;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.homie.psychq.BaseApplication;
import com.homie.psychq.R;
import com.homie.psychq.di.psych.DaggerPsychComponent;
import com.homie.psychq.di.psych.PsychComponent;
import com.homie.psychq.main.api.main.PsychApi;
import com.homie.psychq.main.epoxysetup.feeds_articles_categories.holders.feeds.FeedsPostHolder;
import com.homie.psychq.main.epoxysetup.feeds_articles_categories.models.feeds.FeedsPostModel_;
import com.homie.psychq.main.models.feeds.Results;

import com.homie.psychq.main.models.feeds.PsychPhoto;

import com.homie.psychq.main.ui.PsychPostAfterClick2;
import com.homie.psychq.main.epoxysetup.feeds_articles_categories.controllers.CategoryController;
import com.homie.psychq.room2.DatabaseTransactions;
import com.homie.psychq.subscription.SubscriptionActivity;
import com.homie.psychq.utils.ColorsHelper;
import com.homie.psychq.utils.CookiesHelper;

import com.homie.psychq.utils.PostReadMarker;
import com.homie.psychq.utils.SharedPreferences;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class CategoryOnClick extends AppCompatActivity implements CategoryController.AdapterCallbacks {


    private static final String TAG = "CategoryOnClick";
    public static final int ABOUT_SHOW = 2;

//    private TextView categoryTitle, postCountTv, cat_desc_tv;
    private EpoxyRecyclerView epoxyRecyclerView;
    private CategoryController controller;

    PsychApi psychApi;
    PsychComponent component;

    ShimmerFrameLayout shimmerFrameLayout;

    String postCount;
    String title,description;

    //Pagination

    private int pageNumber = 1;
    private boolean loading = false;
    private final int VISIBLE_THRESHOLD = 1;
    private int lastVisibleItem;
    private int totalItemCount;
    GridLayoutManager layoutManager;


    private List<PsychPhoto> posts_list;
    private int firstItemId;
    private SharedPreferences sharedPreferences;
    private CookiesHelper cookiesHelper;
    private int totalPages = 1;

    //this hashmap will have firstItemId & postsCount needed for layout in controller
    private HashMap<String, Integer> firstItemAndPostsCountHashMap;
    //this map will have description and title of the category or tag
    private HashMap<String, String> descriptionAndTitlesMap;


    CompositeDisposable mDisposibles = new CompositeDisposable();

    private PostReadMarker postReadMarker;
    private DatabaseTransactions databaseTransactions;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.category_onclick_psych);


        /*
         * Here the intent will give us title of the category
         * and will make request to the api with that title and populate the recyclerview
         * when it returns the list of categories
         * */

//        categoryTitle = findViewById(R.id.onclick_category_title_tv);
//        postCountTv = findViewById(R.id.onclick_category_post_count);
        epoxyRecyclerView = findViewById(R.id.onClick_category_epoxyrv);
        shimmerFrameLayout=findViewById(R.id.shimmer_view_container);
        sharedPreferences = new SharedPreferences(this);
        cookiesHelper = new CookiesHelper(this);
        databaseTransactions = new DatabaseTransactions(this);
        postReadMarker = new PostReadMarker(databaseTransactions);

//        cat_desc_tv=findViewById(R.id.categoryDescription);
        posts_list=new ArrayList<>();

        epoxyRecyclerView.setVisibility(View.INVISIBLE);
        shimmerFrameLayout.startShimmer();

        controller = new CategoryController(this,sharedPreferences,databaseTransactions);

        component = DaggerPsychComponent.builder()
                .build();

        psychApi = component.providePsychApi();


        setupEpoxyRV();

        Intent intent = getIntent();

        title = intent.getStringExtra("title");
        description = intent.getStringExtra("description");


//        cat_desc_tv.setText(description);


        if (intent.getStringExtra("comingFrom").equals("tags")){
            //post count of tags will be set when a request is made
            String title_= "#"+title;
//            categoryTitle.setText(title_);
            setRvTagsData();
        }

        if(intent.getStringExtra("comingFrom").equals("categories")){


            postCount = intent.getStringExtra("postsCount");



//            categoryTitle.setText(title);


            setRvCategoryData();
        }




    }

    private void setRvCategoryData() {
        psychApi
                .getPostsByCategory(title, pageNumber)
                .toObservable()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Results>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        mDisposibles.add(d);
                    }

                    @Override
                    public void onNext(Results results) {


                        if(results != null){


                            totalPages = results.getCount() / 10 ;
                            if(results.getCount() % 10 > 0){
                                //if we have an additional page of items less than 10
                                totalPages++;
                            }

                            firstItemAndPostsCountHashMap = new HashMap<>();
                            descriptionAndTitlesMap = new HashMap<>();
                            descriptionAndTitlesMap.put("description",description);//just set description
                            firstItemId = results.getPosts().get(0).getCustom_ordering();


                            firstItemAndPostsCountHashMap.put("firstItemId", firstItemId);
                            firstItemAndPostsCountHashMap.put("postsCount", results.getCount());
                            firstItemAndPostsCountHashMap.put("about", ABOUT_SHOW); // if there is ABOUT_SHOW in map, controller will show info for category

                            shimmerFrameLayout.stopShimmer();
                            shimmerFrameLayout.setVisibility(View.GONE);
                            epoxyRecyclerView.setVisibility(View.VISIBLE);




                            String postsCountSt = String.valueOf(results.getCount());
//                        postCountTv.setText("Posts Count : " + postsCountSt);

                            Log.d(TAG, "onNext: " + results.toString());
                            Log.d(TAG, "onNext: " + results.getPosts().toString());
                            Log.d(TAG, "onNext: " + results.getPosts().size());

                            posts_list.addAll(results.getPosts());

                            if(results.getPosts().size() <= 10){
                                //if we have posts less than 10 we set isLoadingMore false
                                controller.setData(posts_list, false, descriptionAndTitlesMap,firstItemAndPostsCountHashMap);

                            }else {
                                controller.setData(posts_list, true, descriptionAndTitlesMap,firstItemAndPostsCountHashMap);

                            }

                        }


                    }

                    @Override
                    public void onError(Throwable e) {

                        Log.d(TAG, "onError: " + e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });



        epoxyRecyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
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



                    if(pageNumber == 3 && !sharedPreferences.getBooleanPref(getString(R.string.isSubscribed),false)){
                        //if user is not subscribed, he cant surf past 15 pages
                        controller.setData(posts_list,false,descriptionAndTitlesMap, firstItemAndPostsCountHashMap);
                        return;
                    }

                    pageNumber++;


                    //loadingProgress.setVisibility(View.VISIBLE);

                    if(pageNumber <= totalPages){
                        //we have another page, request the api


                        psychApi.getPostsByCategory(title,pageNumber).toObservable().
                                subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(new Observer<Results>() {
                                    @Override
                                    public void onSubscribe(Disposable d) {
                                        mDisposibles.add(d);
                                    }

                                    @Override
                                    public void onNext(Results results) {


                                        posts_list.addAll(results.getPosts());

                                        if(pageNumber < totalPages){
                                            //when pageNumber is less than last page isLoadingMore will be true
                                            controller.setData(posts_list,true,descriptionAndTitlesMap, firstItemAndPostsCountHashMap);
                                        }else {
                                            //when pageNumber equals last page, isLoadingMore will be false
                                            controller.setData(posts_list,false,descriptionAndTitlesMap, firstItemAndPostsCountHashMap);
                                        }

                                        //featuredPsychAdapter.addItems(results.getPosts());
                                        loading=false;

                                    }

                                    @Override
                                    public void onError(Throwable e) {
                                        cookiesHelper.showCookie("Problem While Loading Posts : "+e.getMessage(),
                                                "Please Check Your Internet Connection and Try Again"
                                                , null
                                                ,null);
                                    }

                                    @Override
                                    public void onComplete() {

                                    }
                                });


                    }else {
                        //end of the pages

                    }





                    //paginator.onNext(pageNumber);

                }
            }
        });




    }


    private void setRvTagsData() {

        String queryTitle= "_" + title;
        String hashTitle = "#"+title;

        psychApi
                .getPostsByTags(queryTitle,pageNumber)
                .toObservable()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Results>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        mDisposibles.add(d);
                    }

                    @Override
                    public void onNext(Results results) {


                        if(results != null){

                            totalPages = results.getCount() / 10;
                            if(results.getCount() % 10 != 0){
                                //if we have an additional page of items less than 10
                                totalPages++;
                            }
                            description = "Search results for "+queryTitle;

                            //for tags separate description and title will be passed to controller through map
                            descriptionAndTitlesMap = new HashMap<>();
                            descriptionAndTitlesMap.put("tagTitle",hashTitle);
                            descriptionAndTitlesMap.put("description",description);


                            firstItemAndPostsCountHashMap = new HashMap<>();
                            firstItemId = results.getPosts().get(0).getCustom_ordering();

                            firstItemAndPostsCountHashMap.put("firstItemId", firstItemId);
                            firstItemAndPostsCountHashMap.put("postsCount", results.getCount());
                            firstItemAndPostsCountHashMap.put("about", 3);

                            shimmerFrameLayout.stopShimmer();
                            shimmerFrameLayout.setVisibility(View.GONE);
                            epoxyRecyclerView.setVisibility(View.VISIBLE);

                            posts_list.addAll(results.getPosts());

                            if(results.getPosts().size() <= 10){
                                controller.setData(posts_list, false, descriptionAndTitlesMap, firstItemAndPostsCountHashMap);
                            }else {
                                controller.setData(posts_list, true, descriptionAndTitlesMap, firstItemAndPostsCountHashMap);
                            }


                        }


                    }

                    @Override
                    public void onError(Throwable e) {

                        Log.d(TAG, "onError: Problem while loading data : "+e.getMessage());
                        cookiesHelper.showCookie("Problem While Loading Posts : "+e.getMessage(),
                                "Please Check Your Internet Connection and Try Again"
                                , null
                                ,null);
                    }

                    @Override
                    public void onComplete() {

                    }
                });



        epoxyRecyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
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



                    pageNumber++;


                    if(pageNumber == 3 && !sharedPreferences.getBooleanPref(getString(R.string.isSubscribed),false)){
                        //if user is not subscribed, he cant surf past 15 pages
                        controller.setData(posts_list,false,descriptionAndTitlesMap, firstItemAndPostsCountHashMap);
                        return;
                    }

                    //loadingProgress.setVisibility(View.VISIBLE);


                    if(pageNumber <= totalPages){
                        //request the api


                        psychApi.getPostsByTags(queryTitle,pageNumber).toObservable().
                                subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(new Observer<Results>() {
                                    @Override
                                    public void onSubscribe(Disposable d) {
                                        mDisposibles.add(d);
                                    }

                                    @Override
                                    public void onNext(Results results) {



                                        posts_list.addAll(results.getPosts());


                                        if(pageNumber < totalPages){
                                            controller.setData(posts_list,true,descriptionAndTitlesMap, firstItemAndPostsCountHashMap);
                                        }else {
                                            controller.setData(posts_list,false,descriptionAndTitlesMap, firstItemAndPostsCountHashMap);
                                        }

                                        //featuredPsychAdapter.addItems(results.getPosts());
                                        loading=false;

                                    }

                                    @Override
                                    public void onError(Throwable e) {
                                        cookiesHelper.showCookie("Problem While Loading Posts : "+e.getMessage(),
                                                "Please Check Your Internet Connection and Try Again"
                                                , null
                                                ,null);
                                    }

                                    @Override
                                    public void onComplete() {

                                    }
                                });




                    }else {
                        //end of content

                    }





                }
            }
        });




    }

    private void setupEpoxyRV() {

        //final StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        layoutManager=new GridLayoutManager(this,2);


        controller.setSpanCount(2);
        layoutManager.setSpanSizeLookup(controller.getSpanSizeLookup());
        epoxyRecyclerView.setNestedScrollingEnabled(false);
        epoxyRecyclerView.setLayoutManager(layoutManager);
        epoxyRecyclerView.setAdapter(controller.getAdapter());


    }

    @Override
    public void onPostClick(FeedsPostModel_ model, int position, FeedsPostHolder holder, View clickedView) {

        postReadMarker.checkIfPostIsReadAlready(model.post().getId(),model.post().getCustom_ordering());

        holder.titleOnThumb.setBackground(BaseApplication.get().getResources().getDrawable(R.drawable.title_read_background_feeds ));
        holder.titleOnThumb.getBackground().setColorFilter(ColorsHelper.getCategoryColor(model.post().getCategory().replace(" ","")), PorterDuff.Mode.SRC_ATOP);



        Intent intent = new Intent(CategoryOnClick.this, PsychPostAfterClick2.class);
        intent.putExtra("comingFrom","categories");
        intent.putExtra("post_id", model.post().getId());
        intent.putExtra("category", model.post().getCategory());
        intent.putExtra("sub_category", model.post().getSubCategory());
        startActivity(intent);
    }

    @Override
    public void onSubscribeClick() {
        startActivity(new Intent(CategoryOnClick.this, SubscriptionActivity.class));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mDisposibles.clear();
    }
}
