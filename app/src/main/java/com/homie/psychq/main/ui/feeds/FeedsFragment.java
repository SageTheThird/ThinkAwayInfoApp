package com.homie.psychq.main.ui.feeds;

import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Vibrator;
import android.transition.TransitionManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.airbnb.epoxy.EpoxyRecyclerView;
import com.airbnb.lottie.LottieAnimationView;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.getbase.floatingactionbutton.FloatingActionButton;
import com.homie.psychq.BaseApplication;
import com.homie.psychq.R;
import com.homie.psychq.main.epoxysetup.feeds_articles_categories.controllers.FeedsController;
import com.homie.psychq.main.epoxysetup.feeds_articles_categories.holders.feeds.FeedsPostHolder;
import com.homie.psychq.main.epoxysetup.feeds_articles_categories.models.categories.PsychCategoryModel_;
import com.homie.psychq.main.epoxysetup.feeds_articles_categories.models.crashcourses.ArticleModel_;
import com.homie.psychq.main.epoxysetup.feeds_articles_categories.models.feeds.FeedsPostModel_;
import com.homie.psychq.main.epoxysetup.feeds_articles_categories.models.feeds.TagsModel_;
import com.homie.psychq.main.ui.crashcourses.ArticlePostClickActivity;
import com.homie.psychq.main.models.feeds.Announcement;
import com.homie.psychq.main.models.crashcourses.Article;
import com.homie.psychq.main.models.feeds.ResultsAnnouncements;
import com.homie.psychq.main.models.crashcourses.ResultsArticles;
import com.homie.psychq.main.ui.PsychPostAfterClick2;
import com.homie.psychq.room2.DatabaseTransactions;
import com.homie.psychq.subscription.SubscriptionActivity;
import com.homie.psychq.utils.AdsHelper;
import com.homie.psychq.di.ViewModelProviderFactory;
import com.homie.psychq.di.psych.DaggerPsychComponent;
import com.homie.psychq.di.psych.PsychComponent;
import com.homie.psychq.di.psych.PsychModule;
import com.homie.psychq.main.api.main.PsychApi;
import com.homie.psychq.main.models.feeds.Results;
import com.homie.psychq.main.models.categories.CategoryFeatured;
import com.homie.psychq.main.models.feeds.PsychPhoto;
import com.homie.psychq.main.models.categories.ResultsCategories;
import com.homie.psychq.main.ui.categories.CategoryOnClick;
import com.homie.psychq.utils.ColorsHelper;
import com.homie.psychq.utils.CookiesHelper;
import com.homie.psychq.utils.FancyDialogHelper;
import com.homie.psychq.utils.PostReadMarker;
import com.homie.psychq.utils.SharedPreferences;
import com.shashank.sony.fancygifdialoglib.FancyGifDialogListener;
import com.stepstone.apprating.AppRatingDialog;
import com.warkiz.widget.IndicatorSeekBar;
import com.warkiz.widget.OnSeekChangeListener;
import com.warkiz.widget.SeekParams;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import javax.inject.Inject;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/*The Main Feeds Fragment*/

public class FeedsFragment extends Fragment implements FeedsController.AdapterCallbacks {
    private static final String TAG = "FeedsFragment";

    public static final int POSTS_INDEX_IN_HASHMAP =1;
    public static final int CATEGORIES_INDEX_IN_HASHMAP =2;
    public static final int TAGS_INDEX_IN_HASHMAP=3;
    public static final int TOTALPAGES_LIST_INDEX = 4;
    private static final int ITEMS_PER_PAGE = 10 ;
    private static final int ID_FOR_PAGENUM_MODEL = -10 ;
    public static final int TRIAL_PAGES_LIMIT = 23;
    public static final String NOT_SUBSCRIBED_QUERY = "NotSubscribedContent";
    public static final int REVIEW_POPUP_PAGENUMBER = 9;
    public static final int REVIEW_POPUP_PAGENUMBER2 = 35;
    public static final int REVIEW_POPUP_PAGENUMBER3 = 70;


    PsychApi psychApi;
    PsychComponent component;
    @Inject
    ViewModelProviderFactory providerFactory;
    private CompositeDisposable mDisposibles=new CompositeDisposable();
    private int pageNumber = 1;
    private boolean loading = false;
    private final int VISIBLE_THRESHOLD = 1;
    private int lastVisibleItem;
    private int totalItemCount;
    GridLayoutManager layoutManager;
    private EpoxyRecyclerView epoxyRecyclerView;
    private FeedsController controller;
    private List<PsychPhoto> posts_list=new ArrayList<>();
    private List<CategoryFeatured> categories_list=new ArrayList<>();
    private List<CharSequence> tags_list_linear=new ArrayList<>();
    private List<Integer> totalPagesIntList=new ArrayList<>();
    HashMap<Integer,List<?>> hashSuper=new HashMap<>();
    ShimmerFrameLayout shimmerFrameLayout;
    private AdsHelper adsHelper;
    int firstItemId;
    private SharedPreferences sharedPreferences;
    private RelativeLayout root_layout;
    private int totalPages = 134;
    private List<Article> articles_list;
    private String ordering_field = "-custom_ordering";
    private IndicatorSeekBar pages_seekBar;
    private CardView indicator_card;
    private FloatingActionButton refreshBtn, continueBtn, showIndicatorBtn;
    private boolean isSeekbarVisible = false;
    private CookiesHelper cookiesHelper;
    private int firstitemIdOf_EveryIteration = 0;
    HashMap<Integer,Integer> pageNumHash = new HashMap<>();
    private int totalPagesCategories;
    private LottieAnimationView lottieAnimationView;
    private List<Announcement> announcementList = new ArrayList<>();
    private RelativeLayout errorLayout;
    private ImageView errorIv;
    private ProgressBar errorProgress;
    private DatabaseTransactions databaseTransactions;
    private PostReadMarker postReadMarker;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_featured_updated,container,false);

        initUiComponents(view);
        initShimmer();
        initDataComponent();
        buildCategoriesList();
        loadAnnouncements();
        setupRv();



        refreshBtn.setOnClickListener(RefreshClickListener);




        return view;
    }

    private void initUiComponents(View view) {
        epoxyRecyclerView =view.findViewById(R.id.psych_featured_epoxy_rv);
        shimmerFrameLayout=view.findViewById(R.id.shimmer_view_container);
        root_layout=view.findViewById(R.id.root_layout);
        pages_seekBar=view.findViewById(R.id.seekbar);
        refreshBtn=view.findViewById(R.id.refresh_ab);
        continueBtn=view.findViewById(R.id.continue_ab);
        showIndicatorBtn=view.findViewById(R.id.page_indicatorAb);
        indicator_card=view.findViewById(R.id.indicatorCard);
        cookiesHelper=  new CookiesHelper(getActivity());
        lottieAnimationView = view.findViewById(R.id.lottieAnimationView);
        errorLayout = view.findViewById(R.id.errroLayoutRel);
        errorIv = view.findViewById(R.id.error_iv);
        errorProgress = view.findViewById(R.id.error_progress);
    }

    private void initShimmer() {
        epoxyRecyclerView.setVisibility(View.INVISIBLE);
        shimmerFrameLayout.startShimmer();
    }


    /*While Loading, Displays Error UI*/
    private void showErrorUI(){
        if(errorLayout.getVisibility() == View.GONE){
            TransitionManager.beginDelayedTransition(root_layout);
            errorLayout.setVisibility(View.VISIBLE);
        }

    }

    /*Hides loading UI, when loading is finished*/
    private void hideErrorUI(){
        if(errorLayout.getVisibility() == View.VISIBLE){
            TransitionManager.beginDelayedTransition(root_layout);
            errorLayout.setVisibility(View.GONE);
        }


    }

    private void showErrorProgressbar(){
        if(errorProgress.getVisibility() == View.GONE){
            TransitionManager.beginDelayedTransition(root_layout);
            errorProgress.setVisibility(View.VISIBLE);
        }

    }
    private void hideErrorProgressbar(){
        if(errorProgress.getVisibility() == View.VISIBLE){
            TransitionManager.beginDelayedTransition(root_layout);
            errorProgress.setVisibility(View.GONE);
        }

    }

    /*Displays a rating dialog which can direct users to google play listing of the app to leave a review*/
    private void showRatingDialog() {

        String app_url ="http://play.google.com/store/apps/details?id=" + getActivity().getPackageName();
        new AppRatingDialog.Builder()
                .setPositiveButtonText("Submit")
                .setNegativeButtonText("Never")
                .setNeutralButtonText("Maybe Later")
                .setNoteDescriptions(Arrays.asList("Awful", "heh...", "Gets A Pass!", "Quiet Good & Useful Actually!", "Exquisite. Helpful & Mindful"))
                .setDefaultRating(4)
                .setTitle("Your feedback is highly appreciated")
                .setDescription("Please select some stars and give your honest feedback. So we can work on shortcomings and potential improvements of the app")
                .setCommentInputEnabled(true)
                .setStarColor(R.color.SubtleBlack)
                .setNoteDescriptionTextColor(R.color.SubtleBlack)
                .setTitleTextColor(R.color.colorAccent)
                .setDescriptionTextColor(R.color.SubtleBlack)
                .setHint("Please write your precious review here ...")
                .setHintTextColor(R.color.white)
                .setCommentTextColor(R.color.white)
                .setCommentBackgroundColor(R.color.SubtleBlack)
                .setWindowAnimation(R.style.MyDialogFadeAnimation)
                .setCancelable(false)
                .setCanceledOnTouchOutside(false)
                .create(getActivity())
//                .setTargetFragment(this,0) // only if listener is implemented by fragment
                .show();
    }

    /*Fetches announcements from api and sets to header in feeds*/
    private void loadAnnouncements() {


        //New Approach
        //we place a viewpager layout above the epoxy recyclerView inside a nestedScrollView
        //and when data is fetched for announcements, the layout will be made visible and
        //announcements list will be set to viewpager

        psychApi.getAllAnnouncements(1)
                .toObservable()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ResultsAnnouncements>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        mDisposibles.add(d);
                    }

                    @Override
                    public void onNext(ResultsAnnouncements resultsAnnouncements) {

                        //FeedsAnnouncement, CategoryAnnouncement, CrashCourseAnnouncement

                        if(resultsAnnouncements != null){



                            for(Announcement announcement : resultsAnnouncements.getAnnouncements()){

                                if(announcement.getId().contains("CategoryAnnouncement")){
                                    //save in pref for categories

                                    sharedPreferences.saveStringPref(getString(R.string.CategoriesHeading),announcement.getHeading());
                                    sharedPreferences.saveStringPref(getString(R.string.CategoriesMessage),announcement.getMessage());

                                }else if(announcement.getId().contains("CrashCourseAnnouncement")){
                                    //save in pref for crashCourses

                                    sharedPreferences.saveStringPref(getString(R.string.CrashCourseHeading),announcement.getHeading());
                                    sharedPreferences.saveStringPref(getString(R.string.CrashCourseMessage),announcement.getMessage());

                                }else if(announcement.getId().contains("ProductsAnnouncement")){
                                    //save in pref for store setup


                                    //if status is online, store will be visible other not
//                                //url is fetched in Message field
                                sharedPreferences.saveStringPref(getString(R.string.StoreStatus),announcement.getHeading());
                                sharedPreferences.saveStringPref(getString(R.string.StoreUrl),announcement.getMessage());
//

                                } else {
                                    //we are left with feeds announcements
                                    //add the feeds announcements to the list and set to controller


                                    //here we setup the next line setup for announcements
                                    String messageModified = announcement.getMessage().replace("\\n", System.getProperty("line.separator"));
                                    Announcement announcement1 = announcement;
                                    announcement1.setMessage(messageModified);

                                    announcementList.add(announcement1);
                                }
                            }


                            loadData();



                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(getActivity(), "Oops! Announcements Are Not Loaded", Toast.LENGTH_LONG).show();


                        loadData();
//                        loadAnnouncements();

//                    handleNullAnnouncementsList();

                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }

    /*Refresh the whole feeds fragment's data*/
    private void refreshAllWithNewData(){
        startAnimation();
        refreshLists();
        setupRv();
        loadData();
    }


    /*Starts lottie animation while loading*/
    private void startAnimation() {

        if(shimmerFrameLayout.getVisibility() == View.GONE){
            lottieAnimationView.setVisibility(View.VISIBLE);
            String animationName="feeds_loading.json";
            lottieAnimationView.setAnimation(animationName);
            lottieAnimationView.loop(true);
            lottieAnimationView.playAnimation();
        }

    }

    /*Stops lottie animation when loading is done*/
    private void stopAnimation(){
        if(lottieAnimationView.getVisibility() == View.VISIBLE){

            lottieAnimationView.cancelAnimation();
            lottieAnimationView.setVisibility(View.GONE);

        }
    }

    /*refreshes list of posts, tags*/
    private void refreshLists() {
        posts_list = new ArrayList<>();
        tags_list_linear = new ArrayList<>();
    }

    /*Handles loading of data depending upon if a user is subscribed or not*/
    private void loadData() {

        if(!sharedPreferences.getBooleanPref(getString(R.string.isSubscribed),false)){
            //if user is not subscribed api will be queried for this special field onTrial
            loadDataForOnTrialUser();

        }else {
            //subscribed User Content
            loadDataForSubscribedUser();

        }

    }

    private void loadDataForSubscribedUser() {


        psychApi
                .getPostsByCustomOrdering(ordering_field,pageNumber)
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
                            hideErrorUI();
                            loadUiForResults(results);
//                            alterStorageLinks(results.getPosts());
                        }


                    }

                    @Override
                    public void onError(Throwable e) {


                        cookiesHelper.showCookie("Error While Loading Data : "+e.getMessage(),
                                "Please Check Your Internet Connection & Try Refreshing"
                                ,null
                                ,null);
                        showErrorUI();
                        hideErrorProgressbar();
                        errorIv.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                showErrorProgressbar();
                                loadDataForSubscribedUser();
                            }
                        });
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


                    loading = true;




                    if(pageNumber == TRIAL_PAGES_LIMIT && !sharedPreferences.getBooleanPref(getString(R.string.isSubscribed),false)){
                        //if user is not subscribed, he cant surf past 15 pages
//                        showSubscribeDialog();
                        controller.setData(hashSuper,false,pageNumHash,announcementList);
                        return;
                    }

                    if(pageNumber == REVIEW_POPUP_PAGENUMBER  && !sharedPreferences.getBooleanPref(getString(R.string.userAlreadySubmittedReview),false)){
                        //if user is not subscribed, he cant surf past 15 pages
                        showRatingDialog();
                    }

                    if(pageNumber == REVIEW_POPUP_PAGENUMBER2  && !sharedPreferences.getBooleanPref(getString(R.string.userAlreadySubmittedReview),false)){
                        //if user is not subscribed, he cant surf past 15 pages
                        showRatingDialog();
                    }

                    if(pageNumber == REVIEW_POPUP_PAGENUMBER3  && !sharedPreferences.getBooleanPref(getString(R.string.userAlreadySubmittedReview),false)){
                        //if user is not subscribed, he cant surf past 15 pages
                        showRatingDialog();
                    }


                    pageNumber++;

                    //loadingProgress.setVisibility(View.VISIBLE);

                    if(pageNumber <= totalPages){



                        psychApi.getPostsByCustomOrdering(ordering_field,pageNumber).toObservable().
                                subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(new Observer<Results>() {
                                    @Override
                                    public void onSubscribe(Disposable d) {

                                        mDisposibles.add(d);
                                    }

                                    @Override
                                    public void onNext(Results results) {



                                        if(results != null){

                                            loadUiForAdditionalData(results);
//                                            alterStorageLinks(results.getPosts());

                                        }


                                        //One Time usable code for changing entries in database
                                        //used for altering storage links of entries by using PATCH request
//                                    alterStorageLinks(results.getPosts());


                                    }

                                    @Override
                                    public void onError(Throwable e) {
                                        cookiesHelper.showCookie("Error While Loading Data : "+e.getMessage(),
                                                "Please Check Your Internet Connection & Try Refreshing",
                                                null,
                                                null);
                                    }

                                    @Override
                                    public void onComplete() {

                                    }
                                });
                    }else {
                        //end of the content
                    }



                }
            }
        });

    }

    private void loadDataForOnTrialUser() {

        psychApi.getPostsByCustomOrderingAndNotSubscribed(ordering_field,NOT_SUBSCRIBED_QUERY,pageNumber)
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
                            hideErrorUI();
                            loadUiForResults(results);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                        cookiesHelper.showCookie("Error While Loading Data : "+e.getMessage(),
                                "Please Check Your Internet Connection & Try Refreshing"
                                ,null
                                ,null);
                        showErrorUI();
                        hideErrorProgressbar();
                        errorIv.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                showErrorProgressbar();
                                loadDataForOnTrialUser();
                            }
                        });
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


                    loading = true;




                    if(pageNumber == TRIAL_PAGES_LIMIT && !sharedPreferences.getBooleanPref(getString(R.string.isSubscribed),false)){
                        //if user is not subscribed, he cant surf past 15 pages
//                        showSubscribeDialog();
                        controller.setData(hashSuper,false,pageNumHash,announcementList);
                        return;
                    }

                    if(pageNumber == REVIEW_POPUP_PAGENUMBER  && !sharedPreferences.getBooleanPref(getString(R.string.userAlreadySubmittedReview),false)){
                        //if user is not subscribed, he cant surf past 15 pages
                        showRatingDialog();
                    }


                    pageNumber++;

                    //loadingProgress.setVisibility(View.VISIBLE);

                    if(pageNumber <= totalPages){



                        psychApi.getPostsByCustomOrderingAndNotSubscribed(ordering_field,NOT_SUBSCRIBED_QUERY,pageNumber).toObservable().
                                subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(new Observer<Results>() {
                                    @Override
                                    public void onSubscribe(Disposable d) {

                                        mDisposibles.add(d);
                                    }

                                    @Override
                                    public void onNext(Results results) {



                                        if(results != null){

                                            loadUiForAdditionalData(results);

                                        }


                                        //One Time usable code for changing entries in database
                                        //used for altering storage links of entries by using PATCH request
//                                    alterStorageLinks(results.getPosts());


                                    }

                                    @Override
                                    public void onError(Throwable e) {
                                        cookiesHelper.showCookie("Error While Loading Data : "+e.getMessage(),
                                                "Please Check Your Internet Connection & Try Refreshing",
                                                null,
                                                null);
                                    }

                                    @Override
                                    public void onComplete() {

                                    }
                                });
                    }else {
                        //end of the content
                    }



                }
            }
        });
    }

    private void loadUiForAdditionalData(Results results) {


//                                        pageNumModelSetup(results.getPosts().get(0));
        firstitemIdOf_EveryIteration = results.getPosts().get(0).getCustom_ordering();
        pageNumHash.put(firstitemIdOf_EveryIteration , pageNumber);



        posts_list.addAll(results.getPosts());

        hashSuper.put(POSTS_INDEX_IN_HASHMAP,posts_list);
        requestAdditionalCategories();
        hashSuper.put(CATEGORIES_INDEX_IN_HASHMAP,categories_list);

        setupTagsList(results.getPosts().size(),results.getPosts());



//        handleNullAnnouncementsList();

        if(pageNumber < totalPages){
            //when pageNumber is less than last page isLoadingMore will be true
            controller.setData(hashSuper,true,pageNumHash,announcementList);
        }else {
            //when pageNumber equals last page, isLoadingMore will be false
            controller.setData(hashSuper,false,pageNumHash,announcementList);
        }


        //featuredPsychAdapter.addItems(results.getPosts());
        loading=false;

        pages_seekBar.setProgress(pageNumber);


        if(pageNumber > 4){
            savePgInPrefs();
        }

    }

    private void loadUiForResults(Results results) {

        if(results.getPosts() != null){

            //we refresh hashmap of pgNumbers so we can have header just on first page
            //for showing pageNumber we have to attach the pageNumber with the id {custom_ordering} firstitem of the list
            pageNumHash = new HashMap<>();

            firstitemIdOf_EveryIteration = results.getPosts().get(0).getCustom_ordering();

            pageNumHash.put(firstitemIdOf_EveryIteration , pageNumber);




            pages_seekBar.setOnSeekChangeListener(PageSeekChangeListener);
            continueBtn.setOnClickListener(ContinueClickListener);
            showIndicatorBtn.setOnClickListener(ShowIndicatorClickListener);

            //we add firstItemId for header so header will be shown on top
            firstItemId = results.getPosts().get(0).getCustom_ordering();

            hideShimmer();

            totalPages = results.getCount() / ITEMS_PER_PAGE;
            if(!sharedPreferences.getBooleanPref(getString(R.string.isSubscribed),false)){
                totalPages = 170;
            }

            if(results.getCount() % 10 > 0){
                //if we have an additional page of items less than 10
                totalPages++;
            }

            posts_list.addAll(results.getPosts());

            hashSuper.put(POSTS_INDEX_IN_HASHMAP,posts_list);
            hashSuper.put(CATEGORIES_INDEX_IN_HASHMAP,categories_list);


            //since short of arguments pass to controller, we create a list for totalPages and add just one
            //item i-e totalPages and pass to hashSuper and then get it in controller for Page Showing
            setupTotalPagesInList(totalPages);

            setupTagsList(results.getPosts().size(),results.getPosts());


//            handleNullAnnouncementsList();

            if(announcementList.size() < 1){
                Toast.makeText(getActivity(), "Oops! Announcements Are Not Loaded", Toast.LENGTH_LONG).show();
            }

            if(pageNumber < totalPages){
                //when pageNumber is less than last page isLoadingMore will be true
                controller.setData(hashSuper,true,pageNumHash,announcementList);
            }else {
                //when pageNumber equals last page, isLoadingMore will be false
                controller.setData(hashSuper,false,pageNumHash,announcementList);
            }





            pages_seekBar.setMax(totalPages);
            pages_seekBar.setProgress(pageNumber);


            if(pageNumber > 4){
                savePgInPrefs();
            }

            loading=false;
            stopAnimation();

        }
    }

    /*Sets the pagesList in main hashMap*/
    private void setupTotalPagesInList(int totalPages) {

        totalPagesIntList.add(0,totalPages);
        hashSuper.put(TOTALPAGES_LIST_INDEX,totalPagesIntList);

    }


    private void hideShimmer() {

        shimmerFrameLayout.stopShimmer();
        shimmerFrameLayout.setVisibility(View.GONE);
        epoxyRecyclerView.setVisibility(View.VISIBLE);
    }

    private void setupTagsList(int posts_size, List<PsychPhoto> list) {
        /*
         * For tags we get tags from fetched posts and filter out duplicates and display
         * */

        /*
         * For further tags we fetch the same posts list and separate tags from it and
         * shuffle the list
         * */
        List<String> tags_=null;
        List<String> non_duplicate2 = new ArrayList<>();
        for(int i=0;i<posts_size;i++){

            String full_tag = list.get(i).getTags();
            tags_= Arrays.asList(full_tag.split(","));
            non_duplicate2.addAll(tags_);
//                                Set<String> set = new HashSet<>(tags_);
        }
        //list of fresh tags non duplicate
        non_duplicate2 = new ArrayList<String>(new LinkedHashSet<String>(non_duplicate2));
        tags_list_linear.addAll(non_duplicate2);
        //here we filter the whole primary tags_list for duplication
        tags_list_linear = new ArrayList<>(new LinkedHashSet<>(tags_list_linear));
        //here we shuffle the whole tags_list
        Collections.shuffle(tags_list_linear);

        hashSuper.put(TAGS_INDEX_IN_HASHMAP,tags_list_linear);


//        List<String> tags_=null;
//        List<String> non_duplicate = new ArrayList<>();
//        for(int i=0;i<results.getPosts().size();i++){
//
//            String full_tag = results.getPosts().get(i).getTags();
//            tags_= Arrays.asList(full_tag.split(","));
//            non_duplicate.addAll(tags_);
////                                Set<String> set = new HashSet<>(tags_);
//        }
//        non_duplicate = new ArrayList<String>(new LinkedHashSet<String>(non_duplicate));
//        tags_list_linear.addAll(non_duplicate);

    }

    private void savePgInPrefs() {
        //when user reaches end of the pages it will be reset
//        if(pageNumber == totalPages - 4){
//           pageNumber = totalPages - 100;
//        }
        sharedPreferences.saveIntPref(getString(R.string.resume_pg),pageNumber);
        Log.d(TAG, "savePgInPrefs: Resume Saved In Prefs : "+pageNumber);
    }

    private void setupRv() {

        //pinterest layout
        //final StaggeredGridLayoutManager staggeredGridLayoutManager=new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);

        controller=new FeedsController(this,sharedPreferences,databaseTransactions);
        layoutManager=new GridLayoutManager(getActivity(),2);
//        LinearLayoutManager layoutManager=new LinearLayoutManager(getActivity());

        controller.setSpanCount(2);
        layoutManager.setSpanSizeLookup(controller.getSpanSizeLookup());
        epoxyRecyclerView.setLayoutManager(layoutManager);
        epoxyRecyclerView.setAdapter(controller.getAdapter());

    }

    private void requestAdditionalCategories() {


        if(pageNumber <= totalPagesCategories){
            //we have more pages, hit the api

            psychApi.getAllCategories(pageNumber)
                    .toObservable()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<ResultsCategories>() {
                        @Override
                        public void onSubscribe(Disposable d) {
                            mDisposibles.add(d);
                        }

                        @Override
                        public void onNext(ResultsCategories resultsCategories) {

                            if(resultsCategories != null){

                                categories_list.addAll(resultsCategories.getCategories());

                            }
                        }

                        @Override
                        public void onError(Throwable e) {

                        }

                        @Override
                        public void onComplete() {

                        }
                    });

        }


    }

    private void initDataComponent() {

        component= DaggerPsychComponent.builder()
                .psychModule(new PsychModule(FeedsFragment.this))
                .build();

        psychApi=component.providePsychApi();

        sharedPreferences = new SharedPreferences(getActivity());
        databaseTransactions = new DatabaseTransactions(getActivity());
        postReadMarker = new PostReadMarker(databaseTransactions);
    }

    private void buildCategoriesList() {


        psychApi.getAllCategories(pageNumber)
                .toObservable()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ResultsCategories>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        mDisposibles.add(d);
                    }

                    @Override
                    public void onNext(ResultsCategories resultsCategories) {

                        if(resultsCategories != null){

                            totalPagesCategories = resultsCategories.getCount() / 10 ;
                            if(resultsCategories.getCount() % 10 > 0){
                                totalPagesCategories++;
                            }

                            categories_list.addAll(resultsCategories.getCategories());
                            hashSuper.put(CATEGORIES_INDEX_IN_HASHMAP,categories_list);

                        }

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });



    }






    private void vibrate(){
        if(getActivity() != null){
            Vibrator vibrator = (Vibrator) getActivity().getSystemService(Context.VIBRATOR_SERVICE);
            vibrator.vibrate(50);
        }
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        mDisposibles.clear();
    }






    //---------------------------------------------
    /*Interface implemented methods*/
    //---------------------------------------------
    @Override
    public void onCategoryClick(PsychCategoryModel_ model, int position, View clickedView) {
        vibrate();
        startActivity(new Intent(getActivity(), CategoryOnClick.class)
                .putExtra("comingFrom","categories")
                .putExtra("title", model.category().getTitle())
                .putExtra("description", model.category().getDescription())
        );

    }

    @Override
    public void onPostClick(FeedsPostModel_ model, int position, FeedsPostHolder holder, View clickedView) {

        //first we check if the Id of the post is already present - if not then we add the id to read table

//        slideUp(holder.titleOnThumb);
        postReadMarker.checkIfPostIsReadAlready(model.post().getId(),model.post().getCustom_ordering());

        if(getActivity() != null){
            holder.titleOnThumb.setBackground(BaseApplication.get().getResources().getDrawable(R.drawable.title_read_background_feeds ));
            holder.titleOnThumb.getBackground().setColorFilter(ColorsHelper.getCategoryColor(model.post().getCategory().replace(" ","")), PorterDuff.Mode.SRC_ATOP);
        }

        Intent intent =new Intent(getActivity(), PsychPostAfterClick2.class);

        intent.putExtra("post_id",model.post().getId());
        intent.putExtra("category",model.post().getCategory());
        intent.putExtra("sub_category",model.post().getSubCategory());

        startActivity(intent);

    }

    @Override
    public void onVectorClick(FeedsPostModel_ model, int position, FeedsPostHolder holder, View clickedView) {

        Intent intent=new Intent(getActivity(),CategoryOnClick.class);

        intent.putExtra("comingFrom","categories");
        intent.putExtra("title",model.post().getCategory());
        intent.putExtra("postsCount",189);
        intent.putExtra("description","");
        startActivity(intent);
    }

    @Override
    public void onTagClick(TagsModel_ model, int position, View clickedView) {
        vibrate();
//        if(getActivity() != null){
//            clickedView.setBackgroundColor(getActivity().getResources().getColor(R.color.SubtleBlack));
//        }
        startActivity(new Intent(getActivity(), CategoryOnClick.class)
                .putExtra("comingFrom","tags")
                .putExtra("title", model.tag())
        );
    }

    @Override
    public void articleClicked(ArticleModel_ articleModel_, int position, CardView clickedView) {

        Intent intent = new Intent(getActivity(), ArticlePostClickActivity.class);
        intent.putExtra("articleId", articleModel_.article().getId());
        intent.putExtra("fromFavourites", "True");
        startActivity(intent);
    }

    @Override
    public void onSubscribeClicked() {

        vibrate();
        startActivity(new Intent(getActivity(), SubscriptionActivity.class));
    }


    //----------------------
    /*Listeners*/
    //----------------------
    OnSeekChangeListener PageSeekChangeListener = new OnSeekChangeListener() {
        @Override
        public void onSeeking(SeekParams seekParams) {

        }

        @Override
        public void onStartTrackingTouch(IndicatorSeekBar seekBar) {

            seekBar.setIndicatorTextFormat("Jump To Page : ${PROGRESS}");
        }

        @Override
        public void onStopTrackingTouch(IndicatorSeekBar seekBar) {
            //here we first show confirm then hit api
            pageNumber = seekBar.getProgress();
            if(!sharedPreferences.getBooleanPref(getString(R.string.isSubscribed),false)){
                //user is not subscribed, he cant go above TRIAL_PAGES_LIMIT
                if(pageNumber > TRIAL_PAGES_LIMIT){
                    cookiesHelper.showCookie("Please subscribe to surf through all the sections",
                            "",null,null);
                    seekBar.setProgress(TRIAL_PAGES_LIMIT);
                    pageNumber = TRIAL_PAGES_LIMIT;
                }
            }
            refreshAllWithNewData();

        }
    };
    View.OnClickListener RefreshClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            vibrate();
            pageNumber = 1;
            refreshAllWithNewData();


        }
    };
    View.OnClickListener ContinueClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            /*
             * For Resume Functionality : pageNumber of every loaded page is saved in shared Preferences
             * and later retrieved when user reopens the app, then user has the option of either jump to where
             * they left off or refresh which will load recent posts from page1
             * */

            vibrate();
            int resume_pg = sharedPreferences.getIntPref(getString(R.string.resume_pg), 1);
            if(resume_pg > 4){
                //execute resume code

                pageNumber  = sharedPreferences.getIntPref(getString(R.string.resume_pg),1);


                Toast.makeText(getActivity(), "Resuming From Where You Left off", Toast.LENGTH_SHORT).show();

                refreshAllWithNewData();



            }else {
                //User haven't scrolled far yet
                cookiesHelper.showCookie("No History of Feeds",
                        "No recent page visit found",null,null);
            }
        }
    };
    View.OnClickListener ShowIndicatorClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            //here we set seekbar visible


            if(isSeekbarVisible){
                TransitionManager.beginDelayedTransition(root_layout);
                indicator_card.setVisibility(View.GONE);
                pages_seekBar.setVisibility(View.GONE);
                isSeekbarVisible = false;

            }else {

                //if subscribed and posts_list is not equal to null (the connection is live)
                vibrate();
                TransitionManager.beginDelayedTransition(root_layout);
                pages_seekBar.setVisibility(View.VISIBLE);
                indicator_card.setVisibility(View.VISIBLE);
                isSeekbarVisible = true;
            }
        }
    };
    FancyGifDialogListener subscribeClickDialogListener = new FancyGifDialogListener() {
        @Override
        public void OnClick() {

            Toast.makeText(getActivity(), "Directing To Subscription", Toast.LENGTH_LONG).show();

        }
    };
    FancyGifDialogListener laterClickDialogListener = new FancyGifDialogListener() {
        @Override
        public void OnClick() {

            Toast.makeText(getActivity(), "See You Soon", Toast.LENGTH_LONG).show();

        }
    };



}

