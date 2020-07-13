package com.homie.psychq.main.epoxysetup.feeds_articles_categories.holders.categories;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.airbnb.epoxy.EpoxyHolder;
import com.homie.psychq.R;

public class PsychCategoryHolder extends EpoxyHolder {

    public View view;
    public ImageView largeImage;
    public ImageView smallImage;
    public ImageView openBtn;
    public TextView titleTv;
    public TextView descriptionTv;


    @Override
    protected void bindView(@NonNull View itemView) {
        view=itemView;
        largeImage=view.findViewById(R.id.psych_categories_item_iv);
        smallImage=view.findViewById(R.id.psych_categories_item_iv2);
        titleTv=view.findViewById(R.id.psych_categories_item_tv);
        descriptionTv=view.findViewById(R.id.category_description_nested);
        openBtn=view.findViewById(R.id.openBtn);

    }
}
