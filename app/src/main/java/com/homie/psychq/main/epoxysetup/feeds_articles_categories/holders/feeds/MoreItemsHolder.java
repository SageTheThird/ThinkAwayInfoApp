package com.homie.psychq.main.epoxysetup.feeds_articles_categories.holders.feeds;

import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.airbnb.epoxy.EpoxyHolder;
import com.homie.psychq.R;

public class MoreItemsHolder extends EpoxyHolder {

    public View view;
    public TextView title_tv,category_tv;
    public ImageView imageView;
    public ProgressBar progressBar;

    @Override
    protected void bindView(@NonNull View itemView) {
        view=itemView;
        title_tv =view.findViewById(R.id.psych_feautred_item_tv);
        imageView=view.findViewById(R.id.psych_featured_thumb);
        category_tv=view.findViewById(R.id.psych_feautred_item_category_tv);
        progressBar=view.findViewById(R.id.progress_bar_featured);
//        description_tv=view.findViewById(R.id.psych_feautred_item_descriptiontv);
    }
}
