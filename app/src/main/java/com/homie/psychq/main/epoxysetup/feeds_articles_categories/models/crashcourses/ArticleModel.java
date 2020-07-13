package com.homie.psychq.main.epoxysetup.feeds_articles_categories.models.crashcourses;

import android.view.View;

import androidx.annotation.NonNull;

import com.airbnb.epoxy.EpoxyAttribute;
import com.airbnb.epoxy.EpoxyModelClass;
import com.airbnb.epoxy.EpoxyModelWithHolder;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.homie.psychq.BaseApplication;
import com.homie.psychq.R;
import com.homie.psychq.main.epoxysetup.feeds_articles_categories.holders.crashcourses.ArticleHolder;
import com.homie.psychq.main.models.crashcourses.Article;
import com.homie.psychq.utils.SharedPreferences;
import com.homie.psychq.utils.TimeStampHelper;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

/*Binds the data passed from controller to UI*/

@EpoxyModelClass(layout = R.layout.articles_item_layout_test2)
public class ArticleModel extends EpoxyModelWithHolder<ArticleHolder> {


    @EpoxyAttribute View.OnClickListener layoutClickListener;
    @EpoxyAttribute Article article;
    @EpoxyAttribute SharedPreferences sharedPreferences;

    @Override
    public void unbind(@NonNull ArticleHolder holder) {

    }

    @Override
    public void bind(@NonNull ArticleHolder holder1) {


        holder1.titles_tv.setText(article.getTitle());
        String number__ = String.valueOf(article.getCourseCount());
        holder1.number_tv.setText(number__);

        if(article.getId().equals(sharedPreferences.getString("pinnedArticleId",null))){
            holder1.pin_iv.setVisibility(View.VISIBLE);
        }else {
            holder1.pin_iv.setVisibility(View.GONE);
        }

        if(TimeStampHelper.getDaysAgoInt(article.getTimeCreated()) < 4){
            //content is 3 days old, show new stamp otherwise hide stamp
            holder1.fresh_stamp_iv.setVisibility(View.VISIBLE);
        }else {
            holder1.fresh_stamp_iv.setVisibility(View.GONE);
        }

        /*
         * From testing override doesnt work with transform for round corners
         * - leave just transform(new CenterCrop, new ROundCorner()) when the images are of optimal sizes i-e
         * just barely fitting in its imageView
         * */

        Glide.with(BaseApplication.get())
                .load(article.getThumbnail())
//                .override(100,100)
                .error(R.drawable.ic_pitcher)
                .transform(new CenterCrop(),new RoundedCorners(20))
                .transition(withCrossFade())
                .into(holder1.thumb);


        holder1.layout.setOnClickListener(layoutClickListener);


    }

    @Override
    protected ArticleHolder createNewHolder() {
        return new ArticleHolder();
    }

    @Override
    protected int getDefaultLayout() {
        return R.layout.articles_item_layout_test2;
    }

    @Override
    public boolean shouldSaveViewState() {
        return true;
    }



}
