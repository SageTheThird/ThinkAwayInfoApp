package com.homie.psychq.main.epoxysetup.feeds_articles_categories.holders.feeds;

import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.airbnb.epoxy.EpoxyHolder;
import com.homie.psychq.R;
import com.mikhaellopez.circularimageview.CircularImageView;

public class FeedsPostHolder extends EpoxyHolder {

    public View view;
    public TextView category_tv, timestamp,titleOnThumb;
    public ImageView imageView, freshStampIv;
    public ProgressBar progressBar;
    public CircularImageView vectorCircularIv;
    public ConstraintLayout rootConstraintLayout;


    @Override
    protected void bindView(@NonNull View itemView) {
        view=itemView;
//        title_tv =view.findViewById(R.id.psych_feautred_item_tv);
        titleOnThumb =view.findViewById(R.id.psych_feautred_item_tv2);
//        descriptionTv =view.findViewById(R.id.psych_feautred_description);
        imageView=view.findViewById(R.id.psych_featured_thumb);
        category_tv=view.findViewById(R.id.psych_feautred_category);
        progressBar=view.findViewById(R.id.progress_bar_featured);
        timestamp=view.findViewById(R.id.psych_feautred_timestamp);
        freshStampIv=view.findViewById(R.id.fresh_stamp_iv);
        vectorCircularIv=view.findViewById(R.id.circularIv);
        rootConstraintLayout=view.findViewById(R.id.rootConstraintLayout);
//        description_tv=view.findViewById(R.id.psych_feautred_item_descriptiontv);
    }
}
