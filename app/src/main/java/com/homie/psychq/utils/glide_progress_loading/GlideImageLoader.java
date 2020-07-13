package com.homie.psychq.utils.glide_progress_loading;

import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;

import androidx.annotation.Nullable;

import com.airbnb.lottie.LottieAnimationView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.homie.psychq.BaseApplication;
import com.homie.psychq.utils.ColorsHelper;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

public class GlideImageLoader {

    private static final String TAG = "GlideImageLoader";

    private ImageView mImageView;
    private ProgressBar mProgressBar;
    private LottieAnimationView lottieAnimationView;
    private Button retryBtn;


    public GlideImageLoader(ImageView imageView, ProgressBar progressBar, LottieAnimationView lottieAnimationView, Button retryBtn) {
        mImageView = imageView;
        mProgressBar = progressBar;
        this.lottieAnimationView = lottieAnimationView;
        this.retryBtn = retryBtn;

    }

    public void load(final String url, RequestOptions options,String categoryForColor) {
        if (url == null || options == null) return;

//        onConnecting();

        //set Listener & start
        ProgressAppGlideModule.expect(url, new ProgressAppGlideModule.UIonProgressListener() {
            @Override
            public void onProgress(long bytesRead, long expectedLength) {
                if (mProgressBar != null) {
                    mProgressBar.setProgress((int) (100 * bytesRead / expectedLength));
                    Log.d(TAG, "onProgress: " + (int) (100 * bytesRead / expectedLength));
                }
            }

            @Override
            public float getGranualityPercentage() {
                return 1.0f;
            }
        });
        //Get Image



        Glide.with(BaseApplication.get())
                .load(url)
                .transition(withCrossFade())
                .apply(options) //this needs to be commented
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        ProgressAppGlideModule.forget(url);
                        hideProgress();


                        if(retryBtn != null){
                            retryBtn.setVisibility(View.VISIBLE);
                            retryBtn.getBackground().setColorFilter(ColorsHelper.getCategoryColor(categoryForColor), PorterDuff.Mode.SRC_ATOP);

                            retryBtn.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    //retry coDE
                                    load(url,options,categoryForColor);
                                    showProgress();
                                }
                            });
                        }


                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        ProgressAppGlideModule.forget(url);
                        hideProgress();
                        if(retryBtn != null && retryBtn.getVisibility() == View.VISIBLE){
                            retryBtn.setVisibility(View.GONE);

                        }
                        return false;
                    }
                })
                .into(mImageView);
    }


    private void onConnecting() {
        if (mProgressBar != null) mProgressBar.setVisibility(View.VISIBLE);
    }

    private void showProgress(){
        if (mProgressBar != null) {
            mProgressBar.setVisibility(View.VISIBLE);

        }
    }
    private void hideProgress() {

        if(lottieAnimationView != null){
            lottieAnimationView.cancelAnimation();
            lottieAnimationView.setVisibility(View.GONE);
        }
        if (mProgressBar != null && mImageView != null) {
            mProgressBar.setVisibility(View.GONE);
            mImageView.setVisibility(View.VISIBLE);
        }
    }
}