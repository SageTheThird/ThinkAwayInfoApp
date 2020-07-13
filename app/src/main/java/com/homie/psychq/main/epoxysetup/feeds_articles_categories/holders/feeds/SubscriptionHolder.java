package com.homie.psychq.main.epoxysetup.feeds_articles_categories.holders.feeds;


import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;

import com.airbnb.epoxy.EpoxyHolder;
import com.homie.psychq.R;

public class SubscriptionHolder extends EpoxyHolder {

    public View view;
    public Button subscribeBtn;

    @Override
    protected void bindView(@NonNull View itemView) {

        view=itemView;
        subscribeBtn=view.findViewById(R.id.subscribe_holder);

    }
}
