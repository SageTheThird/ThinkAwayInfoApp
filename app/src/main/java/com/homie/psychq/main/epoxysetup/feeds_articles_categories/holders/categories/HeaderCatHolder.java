package com.homie.psychq.main.epoxysetup.feeds_articles_categories.holders.categories;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.airbnb.epoxy.EpoxyHolder;
import com.homie.psychq.R;

public class HeaderCatHolder extends EpoxyHolder {

    public View view;
    public TextView cat_title,posts_count, cat_description,about;

    @Override
    protected void bindView(@NonNull View itemView) {
        view=itemView;
        cat_title=view.findViewById(R.id.onclick_category_title_tv);
        posts_count=view.findViewById(R.id.onclick_category_post_count);
        cat_description=view.findViewById(R.id.categoryDescription);
        about=view.findViewById(R.id.about);
//        postNote=view.findViewById(R.id.postNote);
    }
}
