package com.homie.psychq.main.ui;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.os.Bundle;
import android.os.Vibrator;
import android.transition.TransitionManager;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.homie.psychq.R;
import com.homie.psychq.di.psych.DaggerPsychComponent;
import com.homie.psychq.di.psych.PsychComponent;
import com.homie.psychq.main.adapters.RelatedTagsAdapter;
import com.homie.psychq.main.api.main.PsychApi;
import com.homie.psychq.main.models.feeds.Results;
import com.homie.psychq.main.favourites.FavouriteAfterClick;
import com.homie.psychq.main.models.feeds.PsychPhoto;
import com.homie.psychq.main.ui.categories.CategoryOnClick;
import com.homie.psychq.main.api.wordsapi.WordsAPi;
import com.homie.psychq.main.api.wordsapi.WordsApiHelper;
import com.homie.psychq.room2.DatabaseTransactions;
import com.homie.psychq.room2.PostFavEntity;
import com.homie.psychq.utils.AppExecutors;
import com.homie.psychq.utils.ImageOpsHelper;
import com.homie.psychq.utils.ColorsHelper;
import com.homie.psychq.utils.CookiesHelper;
import com.homie.psychq.utils.viewpager_transformations.PopTransformation;
import com.homie.psychq.main.adapters.PostAfterClickPagerAdapter;
import com.homie.psychq.utils.PostReadMarker;
import com.homie.psychq.utils.SharedPreferences;
import com.jaeger.library.StatusBarUtil;

import org.aviran.cookiebar2.OnActionClickListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import io.reactivex.CompletableObserver;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class PsychPostAfterClick2 extends AppCompatActivity implements RelatedTagsAdapter.OnTagClickedListener, PostAfterClickPagerAdapter.AfterClickPagerAdapterCallbacks {

    private static final String TAG = "PsychPostAfterClick2";


    private List<PsychPhoto> posts_list;
    private ImageView mainIv;
    private Button download_btn,add_to_fav_btn, set_as_wall_btn, go_to_cat_btn, share_btn;
    private TextView description_tv,title_tv;
    PsychApi psychApi;
    PsychComponent component;
    private String post_id;
    private String category;
    private CoordinatorLayout rootLayout;
    private Intent starterIntent;
    private CompositeDisposable mDisposibles=new CompositeDisposable();
    private ImageOpsHelper imageOpsHelper;
    private DatabaseTransactions databaseTransactions;
    private CookiesHelper cookiesHelper;
    private ImageView back_;
    private SharedPreferences sharedPreferences;
    private ImageView cancelBtn;
    private ProgressBar downloading_progress;
    private TextView progress_tv;
    private RelativeLayout download_card;
    private ProgressBar progress_share;
    private ShimmerFrameLayout shimmerFrameLayout;
    private RelativeLayout shimnmerRelativeLayout;
    ClipboardManager clipboard;
    ClipData clip;
    WordsAPi wordsAPi;
    private WordsApiHelper wordsApiHelper;
    private List<String> tags_list = new ArrayList<>();
    private PostReadMarker postReadMarker;
    private BottomSheetBehavior mBottomSheetBehavior;
    private View bottomSheet;
    private boolean isBottomSheetHidden = false;
    private ViewPager postsViewPager;
    private PostAfterClickPagerAdapter pagerAdapter;
    private RecyclerView recyclerView;
    private RelatedTagsAdapter relatedTagsAdapter;
    private String lastString = ""; //with this we can determine when to hit Words APi
    private ImageView nextPostIv;
    private View swipeUpView1,swipeUpView2,swipeUpView3,pagerCoverView,tagsWrap1,tagsWrap2;
    private boolean isNextPostIvVisisble = false;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        StatusBarUtil.setTranslucent(this);
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        initImageOpsHelper();
        setContentView(R.layout.after_post_click_layout2);


        initUiComponents();

        initPostsViewPager();

        startShimmer();

        initApis();

        handleIntent();

        setupRelatedTagsRv();

        loadData();

        setupWordsDefinitions();

        initBottomSheet();



    }

    /*Handles scenarios for when user is coming from different places*/
    private void handleIntent() {
        starterIntent=getIntent();
        //if user is coming from categories or featured fragment
        post_id=starterIntent.getStringExtra("post_id");
        category=starterIntent.getStringExtra("category");

        if(sharedPreferences.getBooleanPref("categoryInit",true)){

            //this will save category for first time and then we can compare future incoming categories with it
            sharedPreferences.saveStringPref(getString(R.string.category_moreitems),category);
            sharedPreferences.saveBooleanPref("categoryInit",false);
        }
    }


    private void initUiComponents() {
        recyclerView=findViewById(R.id.after_click_more_items_rv);
        posts_list=new ArrayList<>();
        mainIv=findViewById(R.id.main_iV_afterClick);
        download_btn=findViewById(R.id.download_btn_af);
        add_to_fav_btn=findViewById(R.id.saveToFavouriteBtn_af);
        set_as_wall_btn=findViewById(R.id.set_as_wallpaper_btn_af);
        go_to_cat_btn=findViewById(R.id.goToCategory_btn_af);
        share_btn=findViewById(R.id.share_tv_id);
        title_tv=findViewById(R.id.title_tv_af);
        description_tv=findViewById(R.id.description_tv_af);
        rootLayout =findViewById(R.id.rootLayout);
        sharedPreferences = new SharedPreferences(this);
        cancelBtn=findViewById(R.id.cancel_download);
        downloading_progress=findViewById(R.id.progressBar);
        progress_tv=findViewById(R.id.textView2);
        download_card=findViewById(R.id.down_cardView);
        progress_share=findViewById(R.id.rotateloading);
        shimmerFrameLayout=findViewById(R.id.shimmer_post_frameLayout);
        shimnmerRelativeLayout=findViewById(R.id.shimmerPostClickRelativeLayout);
        databaseTransactions=new DatabaseTransactions(this);
        postReadMarker = new PostReadMarker(databaseTransactions);
        postsViewPager = findViewById(R.id.postAfterClickViewPager);
        nextPostIv = findViewById(R.id.forwardBtn);
        swipeUpView1 = findViewById(R.id.swipeUpView1);
        swipeUpView2 = findViewById(R.id.swipeUpView2);
        swipeUpView3 = findViewById(R.id.swipeUpView3);
        tagsWrap1 = findViewById(R.id.tagsWrap1);
        tagsWrap2 = findViewById(R.id.tagsWrap2);
        pagerCoverView = findViewById(R.id.pagerCoverView);
        cookiesHelper=new CookiesHelper(this);
    }


    /*Enables Users to explore 10 related items to current opened post*/
    private void initPostsViewPager() {

        pagerAdapter = new PostAfterClickPagerAdapter(this,this,sharedPreferences);
        postsViewPager.setAdapter(pagerAdapter);
        postsViewPager.setPageTransformer(true,new PopTransformation());

        postsViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {


                Log.d(TAG, "onPageSelected: Posts List Size : "+posts_list.size());
                if(position > -1  && posts_list.size() > 1 ){

                    if(position <= posts_list.size() - 1){

                        Log.d(TAG, "onPageSelected: ViewPager Position : "+position);
                        /*We need to trigger this when we have greater than 1 posts and exactly 11 posts overall*/
                        postReadMarker.checkIfPostIsReadAlready(posts_list.get(position).getId(),posts_list.get(position).getCustom_ordering());
                        setDataForPost(posts_list.get(position));
                        splitAndSetTags(posts_list.get(position).getTags());

                    }

                }


            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    /*BottomSheet for displaying all the textual data*/
    private void initBottomSheet() {

        bottomSheet = findViewById(R.id.postNestedScrool);
        mBottomSheetBehavior = BottomSheetBehavior.from(bottomSheet);
        mBottomSheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View view, int i) {
                switch (i) {
                    case BottomSheetBehavior.STATE_COLLAPSED:
                        Log.d(TAG, "onStateChanged: STATE_COLLAPSED");
                        hideBackgroundCoverView();
                        if(!isNextPostIvVisisble){
                            slideIn(nextPostIv);
                            isNextPostIvVisisble = true;
                        }

                        break;
                    case BottomSheetBehavior.STATE_DRAGGING:
                        Log.d(TAG, "onStateChanged: STATE_DRAGGING");
                        break;
                    case BottomSheetBehavior.STATE_EXPANDED:
                        Log.d(TAG, "onStateChanged: STATE_EXPANDED");
                        isBottomSheetHidden = false;
                        showBackgroundCoverView();

                        break;
                    case BottomSheetBehavior.STATE_HIDDEN:
                        Log.d(TAG, "onStateChanged: STATE_HIDDEN");
                        isBottomSheetHidden = true;
                        hideBackgroundCoverView();
                        slideOut(nextPostIv);
                        isNextPostIvVisisble = false;
                        break;
                    case BottomSheetBehavior.STATE_SETTLING:
                        Log.d(TAG, "onStateChanged: STATE_SETTLING");
                        break;
                }
            }

            @Override
            public void onSlide(@NonNull View view, float v) {
                Log.d(TAG, "onSlide: Sliding ");
            }
        });
    }

    private void initApis() {
        component= DaggerPsychComponent.builder()
                .build();

        psychApi=component.providePsychApi();
        wordsAPi = component.provideWordsAPI();
        wordsApiHelper=new WordsApiHelper(this,wordsAPi);


    }

    private void startShimmer() {

        if(shimmerFrameLayout != null){
            shimmerFrameLayout.startShimmer();
        }

    }

    /*A black transparent background for when we swipe the bottomSheet up*/
    private void showBackgroundCoverView() {
        if(pagerCoverView.getVisibility() == View.GONE){
            TransitionManager.beginDelayedTransition(rootLayout);
            pagerCoverView.setVisibility(View.VISIBLE);
        }

    }

    /*Hides the black transparent background*/
    private void hideBackgroundCoverView() {
        if(pagerCoverView.getVisibility() == View.VISIBLE){
            TransitionManager.beginDelayedTransition(rootLayout);
            pagerCoverView.setVisibility(View.GONE);

        }

    }

    /*Converts a string to all possible tags by separating from comma*/
    private void splitAndSetTags(String tags) {

        tags_list= Arrays.asList(tags.split(","));

        if(tags_list == null){
            //add default tags to list and set
            setupDefaultTags();
            setTagsToRv();
        }else {

            if(tags_list.size() > 0){
                //here
                setTagsToRv();

            }else {
                //tags_list is not null, but size is 0, we set default tags
                setupDefaultTags();
                setTagsToRv();
            }


        }

    }
    private void setTagsToRv(){
        relatedTagsAdapter.addTags(tags_list);
    }

    /*Prep WOrds API*/
    private void setupWordsDefinitions() {

        clipboard = (ClipboardManager) this.getSystemService(Context.CLIPBOARD_SERVICE);
        attachPrimaryClipListener();

    }




    /*ImageOps helper will help in download image, setting image, or setting as background*/
    private void initImageOpsHelper() {

        imageOpsHelper =new ImageOpsHelper(PsychPostAfterClick2.this);
        imageOpsHelper.initBigImageViewer();

    }


    /*Vibrates the phone for 50 Milliseconds*/
    private void vibrate(){
            Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
            vibrator.vibrate(50);

    }


    private void setupRelatedTagsRv() {
        relatedTagsAdapter =new RelatedTagsAdapter(this,this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        recyclerView.setAdapter(relatedTagsAdapter);
    }



    @Override
    public void onBackPressed() {
        super.onBackPressed();
//        Glide.with(mainIv).clear(mainIv);
        removePrimaryClipListener();
    }

    private void loadRelatedItems() {

        //moreItemsController.setData(posts_list, false, false);

        //here we need a work around - if category is the same , keep the total_pages in sharedPreferences
        //if category changes reset total_pages in sharedPrefs
        //category will stored in prefs just for first time then
        //whenever the category != category_prefs, update category_prefs + reset count of total pages to 1


        String category_pref = sharedPreferences.getString(getString(R.string.category_moreitems),category);

        if(!category.equals(category_pref)){
            //reset the total pages in shared
            sharedPreferences.saveStringPref(getString(R.string.category_moreitems),category);
            sharedPreferences.saveIntPref(getString(R.string.total_pages),1);
            Log.d(TAG, "loadRelatedItems: Category Changed "+sharedPreferences.getString(getString(R.string.category_moreitems),null));

        }


        psychApi.getPostsByCategory(category,getRandomPage())
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


                        if(results != null ){

                            nextPostIv.setVisibility(View.VISIBLE);
                            slideIn(nextPostIv);
                            isNextPostIvVisisble = true;

                            int total_pages = results.getCount() / 10;
                            sharedPreferences.saveIntPref(getString(R.string.total_pages),total_pages);

                            posts_list.addAll(results.getPosts());
                            pagerAdapter.addPosts(posts_list);
                            Log.d(TAG, "onNext: Post List Size : "+posts_list.size());



                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                        cookiesHelper.showCookie("Error While Loading Recommended Posts","Re-open the post to load the recommended posts. Check your internet connection and try again",null,null);

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private void setupDefaultTags() {
        tags_list.add("interesting");
        tags_list.add("emotions");
        tags_list.add("suicide");
        tags_list.add("thinking");
        tags_list.add("lifePointers");
        tags_list.add("nietzsche");
        tags_list.add("life");
        tags_list.add("hell");
        tags_list.add("quotes");
        tags_list.add("movies");
        tags_list.add("pain");
        tags_list.add("criticalThinking");
        tags_list.add("skepticism");
        tags_list.add("idealization");
        tags_list.add("devaluing");
        tags_list.add("morality");
        tags_list.add("mind");
        tags_list.add("psychosis");
        tags_list.add("dreams");
        tags_list.add("relationships");
        tags_list.add("people");
        tags_list.add("anxiety");
        tags_list.add("mentalHealth");
        tags_list.add("psychologicalTricks");
    }

    private int getRandomPage(){
        /*
        * it will fetch pages according to category ranging from 1 to total-pages
        * */
        int minimum = 1;
        int maximum = sharedPreferences.getIntPref(getString(R.string.total_pages),1);
        Random rn = new Random();
        Log.d(TAG, "getRandomPage: Maximum (TotalPages) : "+maximum);
        int range = maximum - minimum + 1;
        int randomNum =  rn.nextInt(range) + minimum;
        Log.d(TAG, "getRandomPage: Range (maximum - 1 + 1) : "+range);
        Log.d(TAG, "getRandomPage: RandomNumber  : "+randomNum);
        return randomNum;
    }

    /*Gets post from api and sets it to UI*/
    private void loadData() {

        psychApi.getPostById(post_id)
                .toObservable()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<PsychPhoto>() {
                               @Override
                               public void onSubscribe(Disposable d) {

                                   mDisposibles.add(d);
                               }

                               @Override
                               public void onNext(PsychPhoto psychPhoto) {

                                   //we make psychPhoto global


                                   Log.d(TAG, "onNext: "+psychPhoto.toString());

                                   if(psychPhoto != null){



                                       posts_list.add(psychPhoto);

                                       pagerAdapter.addPosts(posts_list);

                                       Log.d(TAG, "onNext: Post List Size : "+posts_list.size());
                                       hideShimmer();
                                       showAllViews();
                                       setDataForPost(posts_list.get(postsViewPager.getCurrentItem()));






                                       /*Here we will make the related items request and the results will
                                       * be added to the list and updated for ViewPager*/
                                       loadRelatedItems();


                                   }
                               }

                               @Override
                               public void onError(Throwable e) {

                                   cookiesHelper.showCookie("Error While Loading Post","Seems like a problem occurred while loading. Please check the internet connection and try again",null,null);
                                   Log.d(TAG, "onError: Error while Fetching Post : "+e.getMessage());
                               }

                               @Override
                               public void onComplete() {

                               }
                           });

    }
    private void setDataForPost(PsychPhoto psychPhoto) {

        String categoryWithoutSpaces = psychPhoto.getCategory().replace(" ","");

        bottomSheet.getBackground().setTint(ColorsHelper.getCategoryColor(categoryWithoutSpaces));
        download_btn.getBackground().setColorFilter(ColorsHelper.getCategoryColor(categoryWithoutSpaces), PorterDuff.Mode.SRC_ATOP);
        add_to_fav_btn.getBackground().setColorFilter(ColorsHelper.getCategoryColor(categoryWithoutSpaces), PorterDuff.Mode.SRC_ATOP);
        set_as_wall_btn.getBackground().setColorFilter(ColorsHelper.getCategoryColor(categoryWithoutSpaces), PorterDuff.Mode.SRC_ATOP);
        go_to_cat_btn.getBackground().setColorFilter(ColorsHelper.getCategoryColor(categoryWithoutSpaces), PorterDuff.Mode.SRC_ATOP);
        share_btn.getBackground().setColorFilter(ColorsHelper.getCategoryColor(categoryWithoutSpaces), PorterDuff.Mode.SRC_ATOP);
        swipeUpView1.setBackgroundColor(ColorsHelper.getCategoryColor(categoryWithoutSpaces));
        swipeUpView2.setBackgroundColor(ColorsHelper.getCategoryColor(categoryWithoutSpaces));
        swipeUpView3.setBackgroundColor(ColorsHelper.getCategoryColor(categoryWithoutSpaces));
        tagsWrap1.setBackgroundColor(ColorsHelper.getCategoryColor(categoryWithoutSpaces));
        tagsWrap2.setBackgroundColor(ColorsHelper.getCategoryColor(categoryWithoutSpaces));
        nextPostIv.getBackground().setTint(ColorsHelper.getCategoryColor(categoryWithoutSpaces));


        title_tv.setText(psychPhoto.getTitle());
        description_tv.setText(psychPhoto.getDescription());

        //shows print_on_shirt btn when variable is true


        download_btn.setOnClickListener(DownloadClickListener);
        add_to_fav_btn.setOnClickListener(AddToFavClickListener);
        set_as_wall_btn.setOnClickListener(SetAsWallClickListener);
        go_to_cat_btn.setOnClickListener(GotoCategoryClickListener);
        share_btn.setOnClickListener(ShareClickListener);

        incrementViews(psychPhoto);

        //will split the tags from string and set it to Rv
        splitAndSetTags(psychPhoto.getTags());

    }

    private void hideShimmer() {

        if(shimnmerRelativeLayout != null){
            TransitionManager.beginDelayedTransition(rootLayout);
            shimnmerRelativeLayout.setVisibility(View.GONE);
        }
        if(shimmerFrameLayout != null && shimmerFrameLayout.getVisibility() == View.VISIBLE){
            shimmerFrameLayout.stopShimmer();
            shimmerFrameLayout.setVisibility(View.GONE);
        }
    }

    private void showAllViews() {
        TransitionManager.beginDelayedTransition(rootLayout);
//        views_tv.setVisibility(View.VISIBLE);
//        tags_tv.setVisibility(View.VISIBLE);
        title_tv.setVisibility(View.VISIBLE);
        description_tv.setVisibility(View.VISIBLE);
        download_btn.setVisibility(View.VISIBLE);
        set_as_wall_btn.setVisibility(View.VISIBLE);
        add_to_fav_btn.setVisibility(View.VISIBLE);
        go_to_cat_btn.setVisibility(View.VISIBLE);
        share_btn.setVisibility(View.VISIBLE);
//        note_.setVisibility(View.VISIBLE);
    }

    private void directUserToStore() {


        Uri uri = Uri.parse("https://gismos-merch.myshopify.com");
        Intent insta = new Intent(Intent.ACTION_VIEW, uri);

        if (isIntentAvailable(PsychPostAfterClick2.this, insta)){
            startActivity(insta);
        } else{
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://gismos-merch.myshopify.com")));
        }


        Toast.makeText(PsychPostAfterClick2.this, "Directing to our products site", Toast.LENGTH_LONG).show();

    }

    private boolean isIntentAvailable(Context ctx, Intent intent) {
        final PackageManager packageManager = ctx.getPackageManager();
        List<ResolveInfo> list = packageManager.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
        return list.size() > 0;
    }

    private void savePostInDatabase(PsychPhoto psychPhoto) {
        PostFavEntity postFavEntity =new PostFavEntity();
        postFavEntity.setTitle(psychPhoto.getTitle());
        postFavEntity.setPost_id(psychPhoto.getId());
        postFavEntity.setCategory(psychPhoto.getCategory());
        postFavEntity.setSubCategory(psychPhoto.getSubCategory());
        postFavEntity.setThumbnail(psychPhoto.getThumbnail());


        databaseTransactions
                .addPostFav(postFavEntity)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onComplete() {

                        cookiesHelper.showCookie("Post Added to Favourites Successfully",
                                "You can now read this article offline.",
                                "Go to Favourites",
                                GoToFavouritesActionListener);


                    }

                    @Override
                    public void onError(Throwable e) {

                        Log.d(TAG, "onError: Error Adding Post To Favourites : "+e.getMessage());
                    }
                });
    }

    private void incrementDownloads(PsychPhoto psychPhoto){


        int downloads_=psychPhoto.getDownloads();
        downloads_ +=1;

        psychPhoto.setDownloads(downloads_);

        psychApi.updatePost(psychPhoto,psychPhoto.getId())
                .toObservable()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<PsychPhoto>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                        mDisposibles.add(d);

                    }

                    @Override
                    public void onNext(PsychPhoto psychPhoto) {
                        Log.d(TAG, "onNext: Views : "+psychPhoto.getViews());
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private void incrementViews(PsychPhoto psychPhoto){
        int views_=psychPhoto.getViews();
        views_ +=1;

        psychPhoto.setViews(views_);

        updatePost(psychPhoto);


        // old : http://appscontent.zapto.org/content/apps/psychq/categories_thumbs/PSYT_categories_thumb_3.jpg
        // new : http://sneezy3d.com:25255/content/apps/psychq/categories_thumbs/PSYT_categories_thumb_3.jpg
//        String thumbnail = psychPhoto.getThumbnail();
//        String full_res_image = psychPhoto.getFullResImage();
//
//        String[] thumbnail_arr = thumbnail.split("zapto.org",2);
//        String[] full_res_arr = full_res_image.split("zapto.org",2);
//
//        String modified_thumbnail = "http://sneezy3d.com:25255" + thumbnail_arr[1];
//        String modified_full_res = "http://sneezy3d.com:25255" + full_res_arr[1];
//
//        Log.d(TAG, "incrementViews: Modified Thumbnail : "+modified_thumbnail);
//        Log.d(TAG, "incrementViews: Modified Full Res  : "+modified_full_res);

    }

    private void updatePost(PsychPhoto psychPhoto){

        psychApi.updatePost(psychPhoto,psychPhoto.getId())
                .toObservable()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<PsychPhoto>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                        mDisposibles.add(d);

                    }

                    @Override
                    public void onNext(PsychPhoto psychPhoto) {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private void attachPrimaryClipListener() {
        if(clipboard != null){
            clipboard.addPrimaryClipChangedListener(PrimaryClipChangeListener);
        }

    }

    private void removePrimaryClipListener() {
        if(clipboard != null){
            clipboard.removePrimaryClipChangedListener(PrimaryClipChangeListener);
        }

    }

    @Override
    protected void onPause() {
        super.onPause();
        removePrimaryClipListener();
    }

    @Override
    protected void onStop() {
        super.onStop();
        removePrimaryClipListener();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        removePrimaryClipListener();
    }

    @Override
    protected void onResume() {
        super.onResume();
        attachPrimaryClipListener();
    }

    @Override
    public void onTagClicked(String tag, TextView tagTv, int position) {
        startActivity(new Intent(this, CategoryOnClick.class)
                .putExtra("comingFrom","tags")
                .putExtra("title", tag)
        );
    }

    @Override
    public void onImageClicked() {
        /*When tapped on viewPager the bottomSheet would expand or collapse accordingly*/
        if(isBottomSheetHidden){
            expandBottomSheet();
        }else {
            collapseBottomSheet();
        }
    }

    private void collapseBottomSheet() {
        //here we will also make the nextPostIv gone by animation
        mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
        mBottomSheetBehavior.setPeekHeight(0);
        hideBackgroundCoverView();
        isBottomSheetHidden = true;
//        slideOut(nextPostIv);

    }
    private void expandBottomSheet() {

        mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        mBottomSheetBehavior.setPeekHeight(350);
        showBackgroundCoverView();
        isBottomSheetHidden = false;
//        slideIn(nextPostIv);
    }


    public void slideOut(final ImageView view){

        TranslateAnimation animate = new TranslateAnimation(
                0,                 // fromXDelta
                view.getHeight(),                 // toXDelta
                0,  // fromYDelta
                0);                // toYDelta
        animate.setDuration(250);
        animate.setFillAfter(true);//It enables the view to stay in that transited position

        animate.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {

//                view.setVisibility(View.INVISIBLE);



            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        view.startAnimation(animate);

    }

    public void slideIn(final ImageView view){

        TranslateAnimation animate = new TranslateAnimation(
                view.getHeight(),                 // fromXDelta
                0,                 // toXDelta
                0,  // fromYDelta
                0);                // toYDelta
        animate.setDuration(250);
        animate.setFillAfter(true);

        animate.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {

//                view.setVisibility(View.INVISIBLE);



            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        view.startAnimation(animate);

    }








    //-------------------------------------
    /*Listeners*/
    //-------------------------------------

    ClipboardManager.OnPrimaryClipChangedListener PrimaryClipChangeListener = new ClipboardManager.OnPrimaryClipChangedListener() {
        @Override
        public void onPrimaryClipChanged() {

            //First we confirm the subscription
            //whenever text is selected in the app this method will get triggered
            //then we can get primaryClip from clipBoard
            //then we have to separate a single word from text and
            //request api for the word and show the fetched word in bottomSHeet

//                if(sharedPreferences.getBooleanPref(getString(R.string.isSubscribed),false)){
            //User is Subscribed

            clip = clipboard.getPrimaryClip();

            if (clip != null) {


                String copiedText = String.valueOf(clip.getItemAt(0).getText());




                //here we will make request to the api

                List<String> wordsList = Arrays.asList(copiedText.split("\\s+"));
                if(wordsList != null && wordsList.size() == 1){
                    //hit api with one word

                    if(!lastString.equals(copiedText)){

                        lastString = copiedText;
                        wordsApiHelper.showDefinitionsForWord(copiedText);
                    }


                }else {
                    cookiesHelper.showCookie("Please Select a Single Word",
                            "Definitions can only be shown for a single word",
                            null,
                            null);
                }




            }else {
                //User is not Subscribed
                cookiesHelper.showCookie("Please Subscribe To Unlock This Feature",
                        "You can search unlimited words upon subscription"
                        ,null,null);

            }
        }
    };
    View.OnClickListener DownloadClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

//            showAd();
            if(sharedPreferences.getBooleanPref(getString(R.string.isSubscribed),false)){
                vibrate();
                imageOpsHelper.shareIntent(posts_list.get(postsViewPager.getCurrentItem()).getFullResImage(), PsychPostAfterClick2.this,null,null);
                imageOpsHelper.downloadImage(posts_list.get(postsViewPager.getCurrentItem()).getFullResImage(),"High", rootLayout,null,download_card,null,cancelBtn
                        ,progress_tv,downloading_progress, PsychPostAfterClick2.this);
                incrementDownloads(posts_list.get(postsViewPager.getCurrentItem()));
            }else {
                //user is not Subscribed
                cookiesHelper.showCookie("Please subscribe to unlock the feature"
                        ,"Plus a lot of other stuff",null,null);
            }


        }
    };
    View.OnClickListener SetAsWallClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            //code snippet same as unsplash set as wallpaper
//            showAd();
            if(sharedPreferences.getBooleanPref(getString(R.string.isSubscribed),false)){
                vibrate();
                imageOpsHelper.setImageAsBackground(posts_list.get(postsViewPager.getCurrentItem()).getFullResImage(),"High");
                incrementDownloads(posts_list.get(postsViewPager.getCurrentItem()));
            }else {
                //user is not Subscribed
                Toast.makeText(PsychPostAfterClick2.this, "Please Subscribe To Unlock This Feature", Toast.LENGTH_LONG).show();
            }

        }
    };
    View.OnClickListener IsPrintableClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            //user will be directed to website or store fragment
            directUserToStore();
        }
    };
    View.OnClickListener GotoCategoryClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            vibrate();
            startActivity(new Intent(PsychPostAfterClick2.this, CategoryOnClick.class)
                    .putExtra("comingFrom","categories")
                    .putExtra("title", posts_list.get(postsViewPager.getCurrentItem()).getCategory())
                    .putExtra("description", posts_list.get(postsViewPager.getCurrentItem()).getDescription())

            );

        }
    };
    View.OnClickListener ShareClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
//            shareTextUrl(psychPhotoG.getTitle(),psychPhotoG.getThumbnail());

            if(sharedPreferences.getBooleanPref(getString(R.string.isSubscribed),false)){

                vibrate();
                final String appPackageName = getPackageName(); // getPackageName() from Context or Activity object
//            String app_link = String.valueOf(Uri.parse("market://details?id=" + appPackageName));

                String app_link = "https://play.google.com/store/apps/details?id="+appPackageName;

                cookiesHelper.showCookie("Preparing Post To Share",
                        "This will only take a few moments",null,null);

                String description = posts_list.get(postsViewPager.getCurrentItem()).getTitle()
                        + " \n\n"+posts_list.get(postsViewPager.getCurrentItem()).getDescription()
                        + "\n\nFollow the link to explore more interesting articles like this"
                        + " \n"+app_link
                        ;

                imageOpsHelper.shareIntent(posts_list.get(postsViewPager.getCurrentItem()).getFullResImage(),
                        PsychPostAfterClick2.this,
                        description,
                        progress_share);
            }
            else {
                //user is not subscribed
                Toast.makeText(PsychPostAfterClick2.this, "Please Subscribe To Unlock This Feature", Toast.LENGTH_LONG).show();
            }

        }

    };
    View.OnClickListener AddToFavClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if(posts_list.get(postsViewPager.getCurrentItem()) != null){

                AppExecutors.getInstance().diskIO().execute(new Runnable() {
                    @Override
                    public void run() {
                        List<PostFavEntity> bookmarkEntities = databaseTransactions.getPostFavIfPresent(posts_list.get(postsViewPager.getCurrentItem()).getTitle());

                        if(bookmarkEntities.size() > 0){
                            //item already present
                            AppExecutors.getInstance().mainThread().execute(new Runnable() {
                                @Override
                                public void run() {
                                    cookiesHelper.showCookie("Item Already Bookmarked","You cannot bookmark an item twice!",null,null);
                                }
                            });
                        }else {
                            //enter to db
                            vibrate();
                            savePostInDatabase(posts_list.get(postsViewPager.getCurrentItem()));
                        }
                    }
                });


            }

        }
    };
    OnActionClickListener GoToFavouritesActionListener = new OnActionClickListener() {
        @Override
        public void onClick() {

            startActivity(new Intent(PsychPostAfterClick2.this, FavouriteAfterClick.class)
                    .putExtra("type","postfav")
            );
            Toast.makeText(PsychPostAfterClick2.this, "Directing to Favourites", Toast.LENGTH_LONG).show();

        }
    };
    View.OnClickListener BackClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Glide.with(mainIv).clear(mainIv);
            finish();
        }
    };
}
