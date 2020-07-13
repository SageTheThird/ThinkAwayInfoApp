package com.homie.psychq.main.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Priority;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.github.chrisbanes.photoview.PhotoViewAttacher;
import com.homie.psychq.R;
import com.homie.psychq.main.models.feeds.PsychPhoto;
import com.homie.psychq.utils.ColorsHelper;
import com.homie.psychq.utils.QuotesLoadingHelper;
import com.homie.psychq.utils.SharedPreferences;
import com.homie.psychq.utils.TypeWriter;
import com.homie.psychq.utils.glide_progress_loading.GlideImageLoader;

import java.util.ArrayList;
import java.util.List;


public class PostAfterClickPagerAdapter extends PagerAdapter   {

    private static final String TAG = "JokesAdapter";
    public static final int NUMBER_OF_RELATED_POSTS = 11;

    private Context context;
    private List<PsychPhoto> postsList;
    private SharedPreferences sharedPreferences;


    private AfterClickPagerAdapterCallbacks callbacks;
//    private JokeAdapterCallbacks jokeAdapterCallbacks;
    private List<Integer> quotesIndexesForCurrent=new ArrayList<>();


    public PostAfterClickPagerAdapter(Context context, AfterClickPagerAdapterCallbacks callbacks,SharedPreferences sharedPreferences) {
    this.context = context;
    this.postsList = new ArrayList<>();
    this.callbacks = callbacks;
    this.sharedPreferences = sharedPreferences;
    }


    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        LayoutInflater mInflater = LayoutInflater.from(context);

        View item_view ;

        //user isPaid, so we show joke at every position
        item_view = mInflater.inflate(R.layout.post_after_click_viewpager_item_layout, container, false);

        ImageView mainIv = item_view.findViewById(R.id.main_iV_afterClick);
        Button retryBtn = item_view.findViewById(R.id.retryBtn);
        TypeWriter loadingTv = item_view.findViewById(R.id.loadingTv);
        ProgressBar progressBar = item_view.findViewById(R.id.progressBar2);


        progressBar.getProgressDrawable().setColorFilter(ColorsHelper.getCategoryColor(postsList.get(position).getCategory()),android.graphics.PorterDuff.Mode.MULTIPLY);

        if(quotesIndexesForCurrent.size() < 12){
            //when we enter 11 indexes into the list, then we don't want to add more
            quotesIndexesForCurrent.add(QuotesLoadingHelper.getSequentialIndexForQuote(sharedPreferences));
        }


        Log.d(TAG, "instantiateItem: Get Quote From Index : "+quotesIndexesForCurrent.get(position));

//        Log.d(TAG, "instantiateItem: Get Random Quote : "+QuotesLoadingHelper.getSequentialIndexForQuote(sharedPreferences));

        loadingTv.setCharacterDelay(25);
        String loadingQuote = QuotesLoadingHelper.getQuoteForInt(quotesIndexesForCurrent.get(position));
        loadingTv.animateText(loadingQuote);
        setupMainIV(postsList.get(position),mainIv,retryBtn,progressBar);


        container.addView(item_view);
        return item_view;
    }


    private void setupMainIV(PsychPhoto psychPhotoG,ImageView mainIv,Button retryBtn,ProgressBar progressBar) {



        PhotoViewAttacher photoViewAttacher = new PhotoViewAttacher(mainIv);
        photoViewAttacher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callbacks.onImageClicked();
            }
        });

        int widthTemp=context.getResources().getDisplayMetrics().widthPixels;
        int heightTemp=context.getResources().getDisplayMetrics().heightPixels;

//        RequestOptions requestOptions = new RequestOptions();
//        requestOptions.format(DecodeFormat.PREFER_RGB_565);
//        requestOptions.override(widthTemp,heightTemp);


        RequestOptions options = new RequestOptions()
//                .centerCrop()
//                                               .placeholder(R.drawable.ic_refresh)
                .priority(Priority.HIGH)
                .format(DecodeFormat.PREFER_RGB_565)
                .override(widthTemp,heightTemp)
                .transform(new RoundedCorners(20))
                ;

        new GlideImageLoader(mainIv,
                progressBar,
                null,retryBtn).load(psychPhotoG.getFullResImage(),options,psychPhotoG.getCategory().replace(" ",""));

    }



    public void slideUp(final TextView view){

        TranslateAnimation animate = new TranslateAnimation(
                0,                 // fromXDelta
                0,                 // toXDelta
                view.getHeight()/4,  // fromYDelta
                - view.getHeight());                // toYDelta
        animate.setDuration(250);

        animate.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {

                view.setVisibility(View.INVISIBLE);



            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        view.startAnimation(animate);

    }

    public void slideDown(View view){
        view.setVisibility(View.VISIBLE);
        TranslateAnimation animate = new TranslateAnimation(
                0,                 // fromXDelta
                0,                 // toXDelta
                - view.getHeight() ,                 // fromYDelta
                view.getHeight()/4); // toYDelta
        animate.setDuration(250);
        animate.setFillAfter(true);
        view.startAnimation(animate);
    }

    private void animationUsingConstraintSet() {
//        final boolean[] set = {false};
//        final ConstraintSet constraint1 = new ConstraintSet();
//        constraint1.clone(revealRootLayout);
//        final ConstraintSet constraint2 = new ConstraintSet();
//        constraint2.clone(context, R.layout.snippet_reveal_after_btn);
//
//
//        revealBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//                    TransitionManager.beginDelayedTransition(revealRootLayout);
//
//                    ConstraintSet constraint = set[0] ?  constraint1 : constraint2;
//                    constraint.applyTo(revealRootLayout);
//                    set[0] = !set[0];
//                }
//            }
//        });
    }


    @Override
    public int getCount() {
        return postsList.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {

        container.removeView((RelativeLayout) object);

    }

    public void addPosts(List<PsychPhoto> announcements){
        int lastCount = getCount();
        if(postsList.size() > 0){
            postsList = new ArrayList<>();
        }
        postsList.addAll(announcements);
        notifyDataSetChanged();
//        notifyItemRangeInserted(lastCount, jokes.size());
    }




    public interface AfterClickPagerAdapterCallbacks{

        void onImageClicked();
    }


}
