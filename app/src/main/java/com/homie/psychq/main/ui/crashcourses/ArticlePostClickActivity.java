package com.homie.psychq.main.ui.crashcourses;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.transition.TransitionManager;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.widget.NestedScrollView;
import com.bumptech.glide.Priority;
import com.bumptech.glide.request.RequestOptions;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.homie.psychq.R;
import com.homie.psychq.di.psych.DaggerPsychComponent;
import com.homie.psychq.di.psych.PsychComponent;
import com.homie.psychq.main.api.main.PsychApi;
import com.homie.psychq.main.models.crashcourses.Article;
import com.homie.psychq.main.api.wordsapi.WordsAPi;
import com.homie.psychq.main.api.wordsapi.WordsApiHelper;
import com.homie.psychq.room2.ArticleFavEntity;
import com.homie.psychq.room2.DatabaseTransactions;
import com.homie.psychq.utils.AppExecutors;
import com.homie.psychq.utils.ImageOpsHelper;
import com.homie.psychq.utils.BottomSheetWordsMeanings;
import com.homie.psychq.utils.CookiesHelper;
import com.homie.psychq.utils.SharedPreferences;
import com.homie.psychq.utils.glide_progress_loading.GlideImageLoader;
import com.jaeger.library.StatusBarUtil;
import com.realpacific.clickshrinkeffect.ClickShrinkEffect;
import java.util.Arrays;
import java.util.List;
import io.reactivex.CompletableObserver;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import jp.wasabeef.glide.transformations.BlurTransformation;



/*Activity after an individual article is clicked
* Displays the whole content of the selected article
* Contains a built-in dictionary
* Handles updating views,downloads,shares
* Handles add to favourites
* */
public class ArticlePostClickActivity extends AppCompatActivity {

    private static final String TAG = "ArticlePostClickActivit";
    
    
    private String articleId;
    PsychApi psychApi;
    PsychComponent component;
    private Toolbar toolbar;
    private ImageView toolbar_iv;
    private TextView content_tv,content_tv2, description_tv;//
    private FloatingActionButton pin_article,saveTOFav_btn,share_btn;
    private CookiesHelper cookiesHelper;
    private TextView source_tv, published_tv, estTime_tv, reads_tv, saves_tv, shares_tv,article_num,dictionaryNote, aboutTv, contentTagTv;
    private ImageView nextTopicBtn,prevTopicBtn;
    private SharedPreferences sharedPreferences;
    private int totalArticlesInCourse;
    private CompositeDisposable mDisposibles=new CompositeDisposable();
    private NestedScrollView nestedScrollView;
    private CoordinatorLayout coordinatorRootLt;
    private boolean isFromFavourites= false;
    private Article articleG;
    private ImageOpsHelper imageOpsHelper;
    private RelativeLayout middleLayoutRel;
    private ShimmerFrameLayout shimmerFrameLayout;
    private boolean isViewFaded = false;
    private BottomSheetWordsMeanings bottomSheetWordsMeanings;

    

    //ClipBoard Config
    ClipboardManager clipboard;
    ClipData clip;
    WordsAPi wordsAPi;
    private WordsApiHelper wordsApiHelper;
    private boolean isCourseFinished;
    DatabaseTransactions mDatabaseTransactions;
    private String lastString = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtil.setTranslucent(this);
        setContentView(R.layout.article_post_click_activity2);

        initUIComponents();
        //init classes
        initDataComponents();

        //while loading
        shimmerFrameLayout.startShimmer();
        clickShrinkEffectForBTns();
        checkIntent();
        attachPrimaryClipListener();

        if (nestedScrollView != null) {
            nestedScrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
                @Override
                public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {

                    if (scrollY > oldScrollY) {
                        //Scroll DOWN
                    }
                    if (scrollY < oldScrollY) {
                        //Scroll UP
                    }

                    if (scrollY == 0) {
                        //TOP SCROLL

                    }

                    if (scrollY > oldScrollY) {
                        pin_article.hide();
                        share_btn.hide();
                        saveTOFav_btn.hide();
                    } else {
                        pin_article.show();
                        share_btn.show();
                        saveTOFav_btn.show();
                    }

                    if (nestedScrollView.getChildAt(nestedScrollView.getChildCount() - 1) != null) {
                        if ((scrollY >= (nestedScrollView.getChildAt(nestedScrollView.getChildCount() - 1).getMeasuredHeight() - nestedScrollView.getMeasuredHeight())) &&
                                scrollY > oldScrollY && !isFromFavourites) {
                            Log.d(TAG, "onScrollChange: Hit Bottom ");
                            TransitionManager.beginDelayedTransition(coordinatorRootLt);
                            prevTopicBtn.setVisibility(View.VISIBLE);
                            nextTopicBtn.setVisibility(View.VISIBLE);


                        }

                    }
                }
            });
        }
        getAndSetDataForArticle();
        setBtnsClickListeners();


    }

    /*Assigns ClickListeners to btns*/
    private void setBtnsClickListeners() {

        pin_article.setOnClickListener(PinArticleClickListener);
        saveTOFav_btn.setOnClickListener(SaveToFavClickListener);
        share_btn.setOnClickListener(ShareClickListener);
        nextTopicBtn.setOnClickListener(NextTopicClickListener);
        prevTopicBtn.setOnClickListener(PrevTopicClickListener);
        content_tv.setOnClickListener(NestedViewClickListener);

    }

    /*Gets the data for the article from API and sets it on UI components*/
    private void getAndSetDataForArticle() {
        psychApi.getArticleById(articleId)
                .toObservable()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Article>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        mDisposibles.add(d);
                    }

                    @Override
                    public void onNext(Article article) {

                        if (article != null) {

                            articleG = article;
                            hideShimmerLayout();
                            showAllViews();
                            setSupportActionBar(toolbar);
                            if (getSupportActionBar() != null) {
                                //Setting title of article in bar
                                getSupportActionBar().setTitle(article.getTitle());
                                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                            }

                            setupMainIV();

                            description_tv.setText(article.getDescription());


                            //we divide the layout into two textViews, when text length is greater than 5000
                            //the second textView will be displayed

                            String article_content = article.getArticleContent().replace("\\n", System.getProperty("line.separator"));

                            if(article_content.length() > 0 && article_content.length() > 5000){

                                //show the second textView
                                String contentFirstHalf = article_content.substring(0,article_content.length() / 2);
                                String content2ndHalf = article_content.substring(article_content.length() / 2);

                                content_tv2.setVisibility(View.VISIBLE);

                                content_tv2.setText(contentFirstHalf);
                                content_tv.setText(content2ndHalf);


                            }else {
                                //show just one textView
                                content_tv.setText(article_content);

                            }


                            String source = "Source : " + article.getSource();
                            source_tv.setText(source);


                            String timeStamp = article.getTimeCreated();
                            String published_temp = timeStamp.substring(0, Math.min(timeStamp.length(), 10));
                            String published = "Published : " + published_temp;

                            published_tv.setText(published);

                            String course_count  = String.valueOf(article.getCourseCount());
                            article_num.setText(course_count);

                            String esttime = "Est.Time : " + article.getEst_time();
                            estTime_tv.setText(esttime);

                            String reads = numberCalculation(article.getReads());
                            reads_tv.setText(reads);

                            String saves = numberCalculation(article.getDownloads());
                            saves_tv.setText(saves);

                            String shares = numberCalculation(article.getShares());
                            shares_tv.setText(shares);


                            incrementReads();


                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        cookiesHelper.showCookie("Error While Loading Topic : "+e.getMessage(),
                                "Check your internet connection and try again"
                                ,null,null);

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    /*Checks and extracts data from intent*/
    private void checkIntent() {
        articleId = getIntent().getStringExtra("articleId");
        totalArticlesInCourse = getIntent().getIntExtra("totalArticlesInCourse", 60);
        isCourseFinished = getIntent().getBooleanExtra("isCourseFinished", false);

        if (getIntent().hasExtra("fromFavourites")) {
            //if the user is from favourites we will not show next and previous button because articles c
            //can be fom different courses and are added randomly to favourites
            isFromFavourites = true;
        }
    }

    /*Inits Shrink Effect for some Buttons*/
    private void clickShrinkEffectForBTns() {
        new ClickShrinkEffect(pin_article);
        new ClickShrinkEffect(saveTOFav_btn);
        new ClickShrinkEffect(share_btn);
    }

    /*Inits all the important classes {data classes, helper classes, Api helper classes}*/
    private void initDataComponents() {
        initComponent();
        mDatabaseTransactions = new DatabaseTransactions(this);
        sharedPreferences = new SharedPreferences(this);
        imageOpsHelper = new ImageOpsHelper(this);
        wordsApiHelper=new WordsApiHelper(this,wordsAPi);
        cookiesHelper = new CookiesHelper(this);

        clipboard = (ClipboardManager) this.getSystemService(Context.CLIPBOARD_SERVICE);
    }

    private void initUIComponents() {

        toolbar = findViewById(R.id.toolbar);
        toolbar_iv = findViewById(R.id.toolarBar_iv);
        description_tv = findViewById(R.id.description_tv_articles);
        content_tv = findViewById(R.id.content_tv_article);
        pin_article = findViewById(R.id.pin_article);
        saveTOFav_btn = findViewById(R.id.add_to_fav);
        source_tv = findViewById(R.id.source_tv);
        published_tv = findViewById(R.id.published_tv);
        estTime_tv = findViewById(R.id.esttime_tv);
        reads_tv = findViewById(R.id.reads_tv);
        saves_tv = findViewById(R.id.saves_tv);
        shares_tv = findViewById(R.id.share_tv);
        nextTopicBtn = findViewById(R.id.next_page_article);
        prevTopicBtn = findViewById(R.id.prev_page_article);
        nestedScrollView = findViewById(R.id.nestedScroll_articlePost);
        coordinatorRootLt = findViewById(R.id.coordinator_postAfter);
        share_btn = findViewById(R.id.share_article);
        middleLayoutRel = findViewById(R.id.middle_layout_post_article);
        shimmerFrameLayout = findViewById(R.id.shimmer_articlespost_frameLayout);
        article_num = findViewById(R.id.article_number_);
        dictionaryNote = findViewById(R.id.note_articles);
        contentTagTv = findViewById(R.id.content);
        aboutTv = findViewById(R.id.about);
//        content_tv2 = findViewById(R.id.content_tv_article2);
    }

    /*Attaches Primary Click Listener to clipboard, i-e whenever something is added to clipboard,
    * we will get notified*/
    private void attachPrimaryClipListener() {
        if(clipboard != null){
            clipboard.addPrimaryClipChangedListener(PrimaryClipChangeListener);
        }

    }

    /*Removes listener from clipboard*/
    private void removePrimaryClipListener() {
        if(clipboard != null){
            clipboard.removePrimaryClipChangedListener(PrimaryClipChangeListener);
        }

    }

    /*Shows all the UI components in the activity*/
    private void showAllViews() {

        TransitionManager.beginDelayedTransition(coordinatorRootLt);
        description_tv.setVisibility(View.VISIBLE);
        middleLayoutRel.setVisibility(View.VISIBLE);
        content_tv.setVisibility(View.VISIBLE);
        article_num.setVisibility(View.VISIBLE);
        dictionaryNote.setVisibility(View.VISIBLE);
        contentTagTv.setVisibility(View.VISIBLE);
        aboutTv.setVisibility(View.VISIBLE);

    }


    private void hideShimmerLayout() {

        shimmerFrameLayout.stopShimmer();
        shimmerFrameLayout.setVisibility(View.GONE);
    }

    /*Increments Shares and send updates to api*/
    private void incrementShares() {
        if(articleG != null){
            int shares=articleG.getShares();
            shares +=1;
            articleG.setShares(shares);
            updatePost(articleG);
        }
    }

    /*Increments Saves and send updates to api*/
    private void incrementSaves() {
        if(articleG != null){
            int downloads=articleG.getDownloads();
            downloads +=1;
            articleG.setDownloads(downloads);
            updatePost(articleG);
        }
    }

    /*Increments Reads and send updates to api*/
    private void incrementReads() {
        if(articleG != null){
            int reads=articleG.getReads();
            reads +=1;
            articleG.setReads(reads);
            updatePost(articleG);
        }
    }


    /**
     * Updates the post with new/updated article object
     * @param articleG
     */
    private void updatePost(Article articleG) {

        psychApi.updateArticle(articleG,articleG.getId())
                .toObservable()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Article>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                        mDisposibles.add(d);

                    }

                    @Override
                    public void onNext(Article article) {

                    }

                    @Override
                    public void onError(Throwable e) {

                        Toast.makeText(ArticlePostClickActivity.this, "Error : "+e.getMessage(), Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }


    /*
    * Handles setting images
    * */
    private void setupMainIV() {

//        new PhotoViewAttacher(toolbar_iv);

        RequestOptions options = new RequestOptions()
//                .override(300,300)
//              .placeholder(R.drawable.ic_refresh)

                .transform(new BlurTransformation(25,3))
                .error(R.drawable.ic_pitcher)
                .priority(Priority.HIGH);

        new GlideImageLoader(toolbar_iv,
                null,
                null,null).load(articleG.getThumbnail(),options,null);

    }


    /*Triggers phone vibration for 50 Milliseconds*/
    private void vibrate(){
        Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        vibrator.vibrate(50);

    }


    /**
     * Responsible for adding entry to favourites
     * @param articleFavEntity
     */
    private void addArticleToDb(ArticleFavEntity articleFavEntity) {

        mDatabaseTransactions.addArticleFav(articleFavEntity)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        mDisposibles.add(d);
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "onComplete: Article Added To Database : ");

                        cookiesHelper.showCookie("Topic Added to Favourites",
                                "Topic is available in your favourites",
                                null
                                ,null);

                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                });

    }


    private void initComponent() {

        component= DaggerPsychComponent.builder()
                .build();

        psychApi=component.providePsychApi();
        wordsAPi = component.provideWordsAPI();
    }

    /*Helper method for calculating 1000's in k's*/
    private String numberCalculation(long number) {
        if (number < 1000)
            return "" + number;
        int exp = (int) (Math.log(number) / Math.log(1000));
        return String.format("%.1f %c", number / Math.pow(1000, exp), "kMGTPE".charAt(exp-1));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mDisposibles.clear();
        removePrimaryClipListener();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if(mDisposibles != null){
            mDisposibles.clear();
        }
        removePrimaryClipListener();
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                if(mDisposibles != null){
                    mDisposibles.clear();
                }
                finish();
                break;
        }
        return true;
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.setHeaderTitle("Choose your option");
        getMenuInflater().inflate(R.menu.test_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.option_1:
                Toast.makeText(this, "Option 1 selected", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.option_2:
                Toast.makeText(this, "Option 2 selected", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onContextItemSelected(item);
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
    protected void onResume() {
        super.onResume();
        attachPrimaryClipListener();
    }



    /*Buttons Click Listeners Bodies*/
    View.OnClickListener NextTopicClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (articleId != null) {
                vibrate();
                String id_num = articleId.replaceAll("\\D+", ""); // this string has only digits from id
                int increm_id = Integer.parseInt(id_num) + 1; //incrementing id
                String without_digits = articleId.replaceAll("\\d", "");//string without digits
                String next_full_id = without_digits + String.valueOf(increm_id);

                //here we check if the course HAS next article or not
                if (increm_id != 0 && increm_id <= totalArticlesInCourse) {

                    Intent intent = new Intent(ArticlePostClickActivity.this, ArticlePostClickActivity.class);
                    intent.putExtra("articleId", next_full_id);
                    intent.putExtra("totalArticlesInCourse", totalArticlesInCourse);
                    intent.putExtra("articleTitle", "Some Title Other Than PsychQ");
                    finish();
                    startActivity(intent);

                }

                else {
                    //If no next element, then check for if course is continued or not
                    if(isCourseFinished){

                        cookiesHelper.showCookie("Bummer! Course Ends Here",
                                "Try out our other crash courses",null,null);

                    }else {

                        cookiesHelper.showCookie("Course isn't over yet",
                                "Stay tuned for updates to this course, will be worth it."
                                ,null,null);

                    }

                }
            }
        }
    };
    View.OnClickListener PrevTopicClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (articleId != null) {
                vibrate();

                String id_num = articleId.replaceAll("\\D+", ""); // this string has only digits from id
                int decrem_id = Integer.parseInt(id_num) - 1; //incrementing id
                String without_digits = articleId.replaceAll("\\d", "");//string without digits
                String prev_full_id = without_digits + String.valueOf(decrem_id);


                if (decrem_id == 0) {
                    //if we hit 0 by jumping backwards, just show the last article next
                    cookiesHelper.showCookie("Jumping To Last Topic Of Course",
                            "",null,null);

                    decrem_id = totalArticlesInCourse; //incrementing id
                    prev_full_id = without_digits + String.valueOf(decrem_id);
                }


                if (decrem_id != 0 && decrem_id <= totalArticlesInCourse) {

                    Intent intent = new Intent(ArticlePostClickActivity.this, ArticlePostClickActivity.class);
                    intent.putExtra("articleId", prev_full_id);
                    intent.putExtra("totalArticlesInCourse", totalArticlesInCourse);

                    finish();
                    startActivity(intent);
//                    overridePendingTransition(R.anim.slide_out_right);

                }
            }
        }
    };
    View.OnClickListener SaveToFavClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            if(articleG != null){

                //first we will check if the item is already present in db
                AppExecutors.getInstance().diskIO().execute(new Runnable() {
                    @Override
                    public void run() {

                        List<ArticleFavEntity> bookmarkEntities = mDatabaseTransactions.getArticleFavIfPresent(articleG.getTitle());

                        if(bookmarkEntities.size() > 0){
                            //article is already bookmarked
                            AppExecutors.getInstance().mainThread().execute(new Runnable() {
                                @Override
                                public void run() {
                                    cookiesHelper.showCookie("You cannot bookmark article twice!",
                                            "Article is already bookmarked",
                                            null,
                                            null);
                                }
                            });

                        }else {

                            //enter the article to db

                            AppExecutors.getInstance().mainThread().execute(new Runnable() {
                                @Override
                                public void run() {


                                    vibrate();
                                    incrementSaves();

                                    cookiesHelper.showCookie("Adding Topic To Favourites",
                                            "You can easily read your favourite topic in future",
                                            null,
                                            null);

//            sharedPreferences.saveStringPref("pinnedArticleId",articleId);

                                    ArticleFavEntity articleFavEntity = new ArticleFavEntity();
                                    articleFavEntity.setArticleId(articleG.getId());
                                    articleFavEntity.setParentCourse(articleG.getParentCourse());
                                    articleFavEntity.setTitle(articleG.getTitle());
                                    articleFavEntity.setThumbnail(articleG.getThumbnail());
                                    articleFavEntity.setCourse_count(articleG.getCourseCount());
                                    articleFavEntity.setArticle_content(articleG.getArticleContent());


                                    String incrementedSaves = String.valueOf(articleG.getDownloads());
                                    saves_tv.setText(incrementedSaves);

                                    addArticleToDb(articleFavEntity);


                                }
                            });

                        }

                    }
                });





            }



        }
    };
    View.OnClickListener ShareClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            vibrate();

            if(sharedPreferences.getBooleanPref(getString(R.string.isSubscribed),false)){

                if(articleG != null){


                    incrementShares();

                    String incrementedShares = String.valueOf(articleG.getShares());
                    shares_tv.setText(incrementedShares);

                    cookiesHelper.showCookie("Preparing Article To Be Shared",
                            "This will only take a few moments",
                            null,
                            null);

                    final String appPackageName = getPackageName(); // getPackageName() from Context or Activity object
//            String app_link = String.valueOf(Uri.parse("market://details?id=" + appPackageName));

                    String app_link = "https://play.google.com/store/apps/details?id="+appPackageName;

                    String description = articleG.getTitle()
                            + " \n\n"+articleG.getDescription()
                            + "\n\nFollow the link to explore more interesting articles like this"
                            + " \n"+app_link
                            ;

                    imageOpsHelper.shareIntent(articleG.getThumbnail(),
                            ArticlePostClickActivity.this,
                            description,
                            null);

                }

            }else {
                //User is not subscribed
                cookiesHelper.showCookie("Please Subscribe To Unlock This Feature",
                        "You can enjoy unlimited shares upon subscription"
                        ,null,null);

            }





        }
    };
    View.OnClickListener PinArticleClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {


            if(articleId != null && articleG != null){
                vibrate();

                incrementSaves();

                cookiesHelper.showCookie("Article Pinned",
                        "You can continue reading from this topic later",
                        null,
                        null);

                sharedPreferences.saveStringPref("pinnedArticleId",articleId);

            }


        }
    };



    /*Other Listeners*/
    ClipboardManager.OnPrimaryClipChangedListener PrimaryClipChangeListener = new ClipboardManager.OnPrimaryClipChangedListener() {
        @Override
        public void onPrimaryClipChanged() {

            //First we confirm the subscription
            //whenever text is selected in the app this method will get triggered
            //then we can get primaryClip from clipBoard
            //then we have to separate a single word from text and
            //request api for the word and show the fetched word in bottomSHeet


            if(sharedPreferences.getBooleanPref(getString(R.string.isSubscribed),false)){
                //User is Subscribed

                clip = clipboard.getPrimaryClip();

                if (clip != null) {


                    String copiedText = String.valueOf(clip.getItemAt(0).getText());
                    //here we will make request to the api

                    List<String> wordsList = Arrays.asList(copiedText.split("\\s+"));

                    if(wordsList != null && wordsList.size() == 1 ){
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


                }

            }else {
                //User is not Subscribed
                cookiesHelper.showCookie("Please Subscribe To Unlock This Feature",
                        "You can search unlimited words upon subscription"
                        ,null,null);

            }


        }
    };
    View.OnClickListener NestedViewClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            //code for view visibility goes here

            Log.d(TAG, "onClick: Nested Click");
            if(isViewFaded){
                //change it to visible


                TransitionManager.beginDelayedTransition(coordinatorRootLt);
                nextTopicBtn.setVisibility(View.VISIBLE);
                prevTopicBtn.setVisibility(View.VISIBLE);
                isViewFaded = false;

            }else {
                //hide views

                TransitionManager.beginDelayedTransition(coordinatorRootLt);
                nextTopicBtn.setVisibility(View.INVISIBLE);
                prevTopicBtn.setVisibility(View.INVISIBLE);
                isViewFaded = true;
            }


        }
    };

}
