package com.homie.psychq.main.epoxysetup.feeds_articles_categories.holders.feeds;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.airbnb.epoxy.EpoxyHolder;
import com.homie.psychq.R;

public class TagsHolder extends EpoxyHolder {

    public TextView tag;



    @Override
    protected void bindView(@NonNull View itemView) {
        View view =itemView;
        tag=view.findViewById(R.id.single_categories_holder_tv);


    }
}
