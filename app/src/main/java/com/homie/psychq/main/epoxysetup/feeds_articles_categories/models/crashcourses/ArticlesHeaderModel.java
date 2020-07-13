package com.homie.psychq.main.epoxysetup.feeds_articles_categories.models.crashcourses;

import android.transition.TransitionManager;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.airbnb.epoxy.EpoxyAttribute;
import com.airbnb.epoxy.EpoxyModelClass;
import com.airbnb.epoxy.EpoxyModelWithHolder;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.homie.psychq.BaseApplication;
import com.homie.psychq.R;
import com.homie.psychq.main.epoxysetup.feeds_articles_categories.holders.crashcourses.ArticlesHeaderHolder;


@EpoxyModelClass(layout = R.layout.snippet_about_crashcourse_layout)
public class ArticlesHeaderModel extends EpoxyModelWithHolder<ArticlesHeaderHolder> {

    @EpoxyAttribute CharSequence courseDescription;
    @EpoxyAttribute CharSequence courseThumb;
    @EpoxyAttribute CharSequence title;
    @EpoxyAttribute CharSequence author;


//    @EpoxyAttribute String description;

    @Override
    public void unbind(@NonNull ArticlesHeaderHolder holder) {

    }

    @Override
    public void bind(@NonNull ArticlesHeaderHolder holder2) {

//        holder2.about.setTypeface(customTypeFaces.getTrebuchetNormalTF());
//        holder2.article.setTypeface(customTypeFaces.getTrebuchetNormalTF());

        holder2.description.setText(courseDescription);

//        holder2.author.setTypeface(customTypeFaces.getTrebuchetNormalTF());
        holder2.author.setText(author);

//        holder2.courseName.setTypeface(customTypeFaces.getTrebuchetNormalTF());
        holder2.courseName.setText(title);


        Glide.with(BaseApplication.get()).load(courseThumb)
//                .override(100,100)
                .transform(new CenterCrop(),new RoundedCorners(20))
                .into(holder2.courseThumb);

        holder2.seeMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(holder2.seeMore.getText().equals("See More")){
                    TransitionManager.beginDelayedTransition(holder2.linearLayout);
                    holder2.description.requestLayout();
                    holder2.description.getLayoutParams().height = ViewGroup.LayoutParams.WRAP_CONTENT;
                    holder2.seeMore.setText("See Less");
                }else {
                    TransitionManager.beginDelayedTransition(holder2.linearLayout);
                    holder2.description.requestLayout();
                    holder2.description.getLayoutParams().height = 1000;
                    holder2.seeMore.setText("See More");
                }
            }
        });

    }

    @Override
    protected ArticlesHeaderHolder createNewHolder() {
        return new ArticlesHeaderHolder();
    }

    @Override
    protected int getDefaultLayout() {
        return R.layout.snippet_about_crashcourse_layout;
    }

    @Override
    public boolean shouldSaveViewState() {
        return true;
    }



}
