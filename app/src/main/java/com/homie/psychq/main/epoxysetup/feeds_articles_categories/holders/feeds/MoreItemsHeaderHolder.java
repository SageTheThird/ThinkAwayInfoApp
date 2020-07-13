package com.homie.psychq.main.epoxysetup.feeds_articles_categories.holders.feeds;


import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.airbnb.epoxy.EpoxyHolder;
import com.homie.psychq.R;

public class MoreItemsHeaderHolder extends EpoxyHolder {

    public View view;
    public TextView textView;

    @Override
    protected void bindView(@NonNull View itemView) {

        view=itemView;
        textView=view.findViewById(R.id.moreitemstv);

    }
}
